package gui;

import java.util.ArrayList;

import client.ChatClient;
import communication.Msg;
import enteties.StudentTest;
import enteties.TestToExecute;
import enteties.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class StartTestController extends AbstractController {

	/**
	 * button: complete the test,download test,upload test.
	 */
	@FXML
	private Button backbtn, startbtn, connectbtn;

	/**
	 * test fileds to get data from user.
	 */
	@FXML
	private TextField codeTextField, idTextField;

	/**
	 * The controller class for a specific view or UI component. Handles user
	 * interactions and contains the necessary fields and methods.
	 */
	private static TestToExecute testToExecute = new TestToExecute();
	private static StudentTest studentTest = new StudentTest();
	private String lock = null;
	private Msg msg;
	private String lecturerId;
	@SuppressWarnings("unused")
	private String lecturerName;

	private String code;

	/**
	 * after enter code of test user push connect , this fun check if code is valid
	 * and get the appropriate testtoexeucte. If everything was successful,
	 * permission is opened for the student to insert an ID.
	 * 
	 * @param event press on button connect after enter code.
	 */
	@FXML
	void connectBtn(ActionEvent event) {
		String code = codeTextField.getText();
		// Check if the code is empty
		if (code.isEmpty() == true) {
			notification.showWarningAlert("You must enter code for a test, 4 digits!");
			return;
		}
		// Check if the code is valid
		if (!testToExecuteController.checkValidCode(code)) {
			return;
		}
		// Check if the test exists
		msg = testToExecuteController.checkIfTheTestExict(code);
		sendMsg(msg);
		// Show a warning if the test is not found
		if (msgReceived == null) {
			notification.showWarningAlert(
					"A test with the code you provided is not found in the system. Please check that the code is correct.");
			return;
		}
		// Get the lecturer ID for the test
		lecturerId = msgReceived.convertData(TestToExecute.class).get(0).getLecturerId();
		// Check if the test is locked
		msg = testToExecuteController.checkIfTheTestIsLock(code);
		sendMsg(msg);
		if (msgReceived == null) return;
		lock = msgReceived.convertData(TestToExecute.class).get(0).getLock();
		// Get the lock status
		if (lock.equals("true")) {
			notification.showErrorAlert("Sorry, this test is lock!");
			return;
		}
		// Check if the student has already accessed the test
		msg = studentTestController.studentAlreadyAccessed(ChatClient.user, code);
		sendMsg(msg);
		// If the student has not accessed the test, proceed
		if (msgReceived == null) {
			msg = userController.getLecturerNameById(lecturerId);
			sendMsg(msg);
			if (msgReceived == null) return;
			lecturerName = msgReceived.convertData(User.class).get(0).getName();
			Msg msgGetTesttoexeute = testToExecuteController.selectTestToExecuteByTestCode(Integer.parseInt(code));

			sendMsg(msgGetTesttoexeute);
			if (msgReceived == null) return;
			ArrayList<TestToExecute> arr = msgReceived.convertData(TestToExecute.class);
			setTestToExecute(arr.get(0));
			idTextField.setDisable(false);
			idTextField.setEditable(true);
			msg = testToExecuteController.updateNumberOfStudenByOne(1,Integer.toString(StartTestController.getTestToExecute().getTestCode()),"start");
			sendMsg(msg);
		} else {
			notification.showErrorAlert("Sorry, you have already accessed this test and submitted it");
		}
	}

	/**
	 * If the id inserted matches the user, it is checked whether the test is manual
	 * or online and a screen is displayed accordingly.
	 * 
	 * @param event press on button connect start after enter id.
	 * @throws Exception
	 */
	@FXML
	void startBtn(ActionEvent event) throws Exception {
		code = codeTextField.getText();
		User user = ChatClient.user;
		String id = idTextField.getText();
		if (testToExecuteController.checkValidId(id, user)) {
			if (getTestToExecute() != null) {
				Msg msgInsert = studentTestController.insertStudentTest(getTestToExecute(), user);
				sendMsg(msgInsert);
				studentTest.setTestCode(Integer.valueOf(codeTextField.getText()));
				studentTest.setStudentId(ChatClient.user.getId());
				if (getTestToExecute().getTestingType().equals("manual")) {
					start("ManualTest", "StartTest");
				} else {
					start("OnlineTest", "StartTest");
				}
			}
		}
	}

	/**
	 * Constructor Makes the id uneditable.
	 */
	public StartTestController() {
		idTextField = new TextField();
		idTextField.setDisable(true);
	}

	/**
	 * return TestToExecute to be shown.
	 * 
	 * @return TestToExecute to be shown.
	 */
	public static TestToExecute getTestToExecute() {
		return testToExecute;
	}

	/**
	 * set TestToExecute to be shown.
	 * 
	 * @param testToExecute to be shown.
	 */
	public static void setTestToExecute(TestToExecute testToExecute) {
		StartTestController.testToExecute = testToExecute;
	}

	/**
	 * Retrieves the code.
	 *
	 * @return The code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Retrieves the test to execute.
	 *
	 * @return The TestToExecute object.
	 */
	public TestToExecute getTestToExecuteToShow() {
		return testToExecute;
	}
}