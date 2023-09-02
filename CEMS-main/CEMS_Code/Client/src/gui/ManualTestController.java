package gui;

import java.util.ArrayList;
import java.util.Collections;

import client.ChatClient;
import communication.Msg;
import communication.MsgType;
import controllers.CemsFileController;
import controllers.CountDown;
import controllers.TimeController;
import enteties.StudentTest;
import enteties.TestToExecute;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Controller class for the ManualTest screen.
 * 
 * @author Lior Zucker 
 */
public class ManualTestController extends AbstractController implements CountDown,Testing {
	
	
    @FXML
    private TextField downloadField,uploadField;
	/**
	 * button: complete the test,download test,upload test.
	 */
	@FXML
	private Button donebtn,downbtn,upbtn;
	
	 /**
     * Labels for displaying test information.
     */
    @FXML
    private Label info1, info2, info3, info4, info5, timeLabel,warnningLabel;

    /**
     * Container for displaying instructions for students.
     */
    @FXML
    private TextFlow instructionsForStudentTextFlow;
	
    /**
     * Test data.
     */
	private Integer code;
	@SuppressWarnings("unused")
	private String lock;
	private TestToExecute numbersOfStudent; 
	private int timeOfStudent;
	private Msg msg;
	private Integer grade=75;
	private String flagEndOrMiddle;
	private boolean lastMin = false;
	
	/**
     * Controllers
     */
	private TestToExecute testToExecute;
	public TimeController timeController;
	private CemsFileController cemsFileController = new CemsFileController();	
	
	/**
	 * Constructs a new ManualTestController.
	 * retrieves the test to be executed and initializes the code field.
	 * Initializes the time controller with the test duration.
	 */
    public ManualTestController() {
    	if (ChatClient.lastCurrentScreen instanceof StartTestController) {
            testToExecute = ((StartTestController) ChatClient.lastCurrentScreen).getTestToExecuteToShow();
            code = testToExecute.getTestCode();
        }
    	 timeController = new TimeController(testToExecute.getTest().getDuration(), this);
    }
    
    /**
     * Sets the information of the test and starts the timer.
     */
    @FXML
    protected void initialize() { 
    	setInfo();
    }
    
    /**
     * Sets the information of the test and updates the display.
     * This method populates various labels and text flows with the relevant information
     * about the test and its instructions for students and lecturers, based on the current screen state.
     * It also adjusts the formatting and styling of the displayed elements.
     */
    public void setInfo() {
        // Set the info of the test
        Text textForTextFlow = null;
        info1.setText("Test Code: " + testToExecute.getTestCode());
        info2.setText("Course: " + testToExecute.getTest().getCourse().getName());
        info3.setText("Duration:" + testToExecute.getTest().getDuration() + " minutes");
        info4.setText("Date: " + testToExecute.getDate());
        info5.setText("Student Name:" + ChatClient.user.getName());
        // Set instructions for students
        if (testToExecute.getTest().getInstructionsForStudent() != null) {
            textForTextFlow = new Text("Instructions For Student: " + testToExecute.getTest().getInstructionsForStudent());
            textForTextFlow.wrappingWidthProperty().bind(instructionsForStudentTextFlow.widthProperty());
            textForTextFlow.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 14));
            instructionsForStudentTextFlow.getChildren().addAll(textForTextFlow);
        }
    }
    
	/**
	 * Handles the action event for the submit button in a JavaFX application.
	 *
	 * @param event The ActionEvent object representing the button click event.
	 * @throws Exception If an exception occurs during the execution of the method.
	 */
	@FXML
	public void submmitBtn(ActionEvent event) throws Exception {
		 // Stop the timer
        timeController.stopTimer();
        
        // Calculate the time taken by the student
        timeOfStudent = testToExecute.getTest().getDuration() - timeController.timeLeft();
        
        notification.setOnCancelAction(new Runnable() {	
			@Override public void run() {
			 timeController.startTimer();
		}});
        notification.setOnOkAction(new Runnable() {	
			@Override public void run() {

			if(testToExecute.getLock().equals("true")){
				flagEndOrMiddle="End";
				testIsLockCantSubmmit();
				updateAverageAndMedian();	
			}
			else {
				testIsSubmit(timeOfStudent,grade);
				checkIfStudentIsTheLastOne();
				updateAverageAndMedian();	
			}				
			try {start("StudentMenu", "Login");
			((Menu)ChatClient.getScreen(ChatClient.user.getPremission()+"Menu")).setWelcome("Welcome " + ChatClient.user.getName());
			} catch (Exception e) {}}});
        notification.showConfirmationAlert(ChatClient.user.getName()+" Are you sure ?","After clicking the OK button, the submission is final and there is no option to change it");

	}
	
	 /**
     * Checks if the current student is the last one to submit the test and locks the test if necessary.
     * It retrieves the number of students who have started and finished the test and calculates
     * the difference to determine if the current student is the last one.
     * If the current student is the last one, it sends a message to lock the test.
     */
	public void checkIfStudentIsTheLastOne() {
		msg=testToExecuteController.checkIfTheStudentIsLast(StartTestController.getTestToExecute().getTestCode());
		sendMsg(msg);
		if(msgReceived==null) {return;}
		numbersOfStudent=msgReceived.convertData(TestToExecute.class).get(0);
		Integer needToLock=numbersOfStudent.getNumberOfStudentsStarted()-numbersOfStudent.getNumberOfStudentsFinished()-numbersOfStudent.getNumberOfStudents();
		if (needToLock==0) {
			msg=testToExecuteController.getMsgToLockTest(StartTestController.getTestToExecute());
			sendMsg(msg);
		}
    }
   
	 /**
     * Updates the average and median grades for a specific test code.
     */
	public void updateAverageAndMedian(){
		
		double average=0 , median=0;
		msg=studentTestController.selectAllstudentBySpecificCodeTest(code.toString());
		sendMsg(msg);
		 if(msgReceived==null) {return;}
		ArrayList<StudentTest> listOfStudent =msgReceived.convertData(StudentTest.class);
		System.out.println("the grade"+listOfStudent.get(0).getGrade());///////
		ArrayList<Integer> listOfGrades=new ArrayList<Integer>();
		for(StudentTest student : listOfStudent) {
			average+=student.getGrade();
			listOfGrades.add(student.getGrade());
		}
		average=average/listOfGrades.size();
		Collections.sort(listOfGrades);
		median=listOfGrades.get(listOfGrades.size()/2);
		msg=testToExecuteController.updateMedianAndAverage(code.toString(),average,median);
		sendMsg(msg);		
	}
	
	/**
	 * Handles the submission of a test by a student.
	 *
	 * @param timeOfStudent The time taken by the student to complete the test (in minutes).
	 * @param grade The grade obtained by the student.
	 */
	public void testIsSubmit(Integer timeOfStudent,Integer grade) {
		Msg msg;
		notification.showInformationAlert("The test was successfully submitted!");
		try {start("StudentMenu", "Login");
		((Menu)ChatClient.getScreen(ChatClient.user.getPremission()+"Menu")).setWelcome("Welcome " + ChatClient.user.getName());} catch (Exception e) {e.printStackTrace();}
		////////update data
		Msg msgUpdate = testToExecuteController.updateNumberOfStudenByOne(1,code.toString(),"finish");
		sendMsg(msgUpdate);
		msg=studentTestController.InsertAnswersAndGradeManual("false",timeOfStudent,"23244",grade,ChatClient.user.getId() ,code.toString());
		sendMsg(msg);
		msg=testToExecuteController.insertDistributionByCode(code.toString(),grade,1);
		sendMsg(msg);
	}

	/**
	 * Handles the case when the student has exceeded the allowed time for the test.
	 */
	public void timeOfStudentIsOverLoad() {
		Msg msg;
		notification.showErrorAlert("You have exceeded the allowed time!");
		Msg msgUpdate = testToExecuteController.updateNumberOfStudenByOne(1,Integer.toString(StartTestController.getTestToExecute().getTestCode()),"cantSubmit");
		sendMsg(msgUpdate);
		msg=studentTestController.InsertAnswersAndGradeManual("false",timeOfStudent,"00000",0,ChatClient.user.getId() ,code.toString());
		sendMsg(msg);
		msg=testToExecuteController.insertDistributionByCode(code.toString(),0,1);
		sendMsg(msg);
		try {start("StudentMenu", "Login");
		((Menu)ChatClient.getScreen(ChatClient.user.getPremission()+"Menu")).setWelcome("Welcome " + ChatClient.user.getName());
		} catch (Exception e) {e.printStackTrace();}
	}
	
	/**
	 * down load test from server to client.
	 * 
	 * @param event
	 */
	@FXML
	void download(ActionEvent event) {
		Msg msg = new Msg(MsgType.fileToSend);
		try {msg.setPathFile(downloadField.getText());
			sendMsg(msg);
		}catch(Exception e){notification.showErrorAlert("Enter folder path to download.");}
	}

	/**
	 * upload test to submit it , go to server folder.
	 * 
	 * @param event
	 */
	@FXML
	void upload(ActionEvent event) {
		try {
			String path = uploadField.getText();
			Msg msg = cemsFileController.createMsgWithFile(path);
			sendMsg(msg);
		}catch(Exception e) {notification.showErrorAlert("Error uploading file to server.");}
	}

	/**
	 * Handles the case when the test is locked in the middle and the student cannot submit the test.
	 */
	public void testIsLockCantSubmmit() {
		msg = testToExecuteController.updateNumberOfStudenByOne(1,Integer.toString(StartTestController.getTestToExecute().getTestCode()),"cantSubmit");
		sendMsg(msg);
		notification.showErrorAlert("The test is locked!\n You will not be able to submit the test!");
		if(flagEndOrMiddle.equals("End")) {
			msg=studentTestController.InsertAnswersAndGradeManual("false",timeOfStudent,"00000",0,ChatClient.user.getId() ,code.toString());
		}
		else {
			msg=studentTestController.InsertAnswersAndGradeManual("false",timeOfStudent,"12000",50,ChatClient.user.getId() ,code.toString());
		}
		sendMsg(msg);
		msg=testToExecuteController.insertDistributionByCode(code.toString(),0,1);
		sendMsg(msg);
		try {start("StudentMenu", "Login");
		((Menu)ChatClient.getScreen(ChatClient.user.getPremission()+"Menu")).setWelcome("Welcome " + ChatClient.user.getName());
		} catch (Exception e) {e.printStackTrace();}
	}
	/**
	 * Overrides the method to handle the case when a test is manually locked by a lecturer.
	 *
	 * @param testCode The code of the test being checked for manual locking
	 */
	
	@Override
	public void testGotManualyLockedByLecturer(String testCode) {
		if(!testCode.equals(Integer.toString(StartTestController.getTestToExecute().getTestCode()))) return;
		notification.setOnOkAction(new Runnable() {	
			@Override public void run() {
				flagEndOrMiddle="Middle";
				testIsLockCantSubmmit();
				updateAverageAndMedian();
		}});
		notification.showConfirmationAlertWithOnlyOk("Please click OK to continue the process","Sorry, but the test got locked by your lecturer..");
	}
	/**
	 * Sets the text of the countdown label to the specified string.
	 *
	 * @param s countdownText The string to be set as the text of the countdown label.
	 */
	@Override
	public void setTextCountdown(String s) {
		timeLabel.setText(s);
	}
	/**
	 * Callback method called when the time limit for the student has ended.
	 * Performs necessary actions such as notifying the end of time, checking if the student is the last one,
	 * and updating the average and median.
	 */
	@Override
	public void endOfTime() {
		if (lastMin == false) {
			lastMin = true;
			timeController.updateClock(1);
			timeController.startTimer();
			warnningLabel.setText("This is you last chance to submit!");
			warnningLabel.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 14px; -fx-text-fill: red;");
			timeLabel.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 18px; -fx-text-fill: red;");
		}
		else {
			timeOfStudentIsOverLoad();
			checkIfStudentIsTheLastOne();
			updateAverageAndMedian();	
		}
	}

	/**
	 * gets a pop message about a change in duration and act properly.
	 * 
	 * @param testCode the test code.
	 * @param duration the new duration.
	 */
	@Override
	public void testdurationGotChanged(String testCode, Integer duration) {
		if(!testCode.equals(Integer.toString(StartTestController.getTestToExecute().getTestCode()))) return;
		notification.setOnOkAction(new Runnable() {	
			@Override public void run() {
				flagEndOrMiddle="Middle";
				timeController.updateClock(duration-(testToExecute.getTest().getDuration()));
		}});
		notification.showConfirmationAlertWithOnlyOk("Please click OK to continue the process","The duration of the test you are taking got changed.");
	}
}