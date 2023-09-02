package controllers;

import java.util.ArrayList;
import java.util.Collection;

import communication.Msg;
import communication.MsgType;
import enteties.Test;
import enteties.TestToExecute;
import enteties.User;
import notifications.NotificationAlertsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Controller class for managing TestToExecute.
 */
public class TestToExecuteController {

	/**
	 * create a messge for select query.
	 * @param code
	 * @return Msg
	 */
	public Msg checkIfTheTestExict(String code) {
		Msg msg = new Msg(MsgType.select);
		msg.setSelect("testtoexecute.lecturerId");
		msg.setSelect("testtoexecute.testCode");
		msg.setFrom("testtoexecute");
		msg.setWhere("testCode", code);
		return msg;
	}

	/**
	 * Creates a message to update the median and average grades for a specific test
	 * in test to execute.
	 *
	 * @param code    The test code of the test to update.
	 * @param average The new average grade to set.
	 * @param median  The new median grade to set.
	 * @return A Msg object configured for the update operation.
	 */

	public Msg updateMedianAndAverage(String code, double average, double median) {
		Msg msg = new Msg(MsgType.update);
		msg.setTableToUpdate("testtoexecute");
		msg.setSet("average", average);
		msg.setSet("median", median);
		msg.setWhere("testCode", code);
		return msg;
	}

	/**
	 * @param upOrDown
	 *                 -1 to get doun the distribution with old grade.
	 *                 1 to ger up the distribution with new grade.
	 * @param code     key of the test in data.
	 * @param grade    grade of one sudent when he finish the test.
	 * @return Msg
	 */
	public Msg insertDistributionByCode(String code, Integer grade, Integer upOrDown) {
		Msg msg = new Msg(MsgType.updatePlusOne);
		msg.setTableToUpdate("testtoexecute");
		if (grade >= 0 && grade < 10) {
			msg.setSet("distrubition1", upOrDown);
		} else if (grade >= 10 && grade < 20) {
			msg.setSet("distrubition2", upOrDown);
		} else if (grade >= 20 && grade < 30) {
			msg.setSet("distrubition3", upOrDown);
		} else if (grade >= 30 && grade < 40) {
			msg.setSet("distrubition4", upOrDown);
		} else if (grade >= 40 && grade < 50) {
			msg.setSet("distrubition5", upOrDown);
		} else if (grade >= 50 && grade < 60) {
			msg.setSet("distrubition6", upOrDown);
		} else if (grade >= 60 && grade < 70) {
			msg.setSet("distrubition7", upOrDown);
		} else if (grade >= 70 && grade < 80) {
			msg.setSet("distrubition8", upOrDown);
		} else if (grade >= 80 && grade < 90) {
			msg.setSet("distrubition9", upOrDown);
		} else if (grade >= 90 && grade <= 100) {
			msg.setSet("distrubition10", upOrDown);
		}
		msg.setWhere("testCode", code);
		return msg;
	}

	/**
	 * make query to get if the test is lock or not.
	 * 
	 * @param code key of the test in data.
	 * @return Msg contain select query.
	 */
	public Msg checkIfTheTestIsLock(String code) {
		Msg msg = new Msg(MsgType.select);
		msg.setSelect("testtoexecute.`lock`");
		msg.setFrom("testtoexecute");
		msg.setWhere("testCode", code);
		return msg;
	}

	/**
	 * Checks if the student is the last one to finish the test.
	 *
	 * @param code The test code.
	 * @return A message containing the selected information.
	 */
	public Msg checkIfTheStudentIsLast(Integer code) {
		Msg msg = new Msg(MsgType.select);
		msg.setSelect("testtoexecute.numberOfStudentStarted");
		msg.setSelect("testtoexecute.numberOfStudentFinished");
		msg.setSelect("testtoexecute.numberOfStudent");
		msg.setFrom("testtoexecute");
		msg.setWhere("testCode", code);
		return msg;

	}

	/**
	 * @param numberToPlus How much would you like to add to the field.
	 * @param whichField
	 *                     start-for numberOfStudentStarted
	 *                     finish - for numberOfStudentFinished
	 *                     cantSubmit -for student that cant submit ,
	 *                     numberOfStudentי
	 * @param code         key of the test in data.
	 * @return Msg contain update query.
	 */
	public Msg updateNumberOfStudenByOne(Integer numberToPlus, String code, String whichField) {
		Msg msg = new Msg(MsgType.updatePlusOne);
		msg.setTableToUpdate("testtoexecute");
		if (whichField.equals("start")) {
			msg.setSet("numberOfStudentStarted", numberToPlus);

		}
		if (whichField.equals("cantSubmit")) {
			msg.setSet("numberOfStudent", numberToPlus);
		}
		if (whichField.equals("finish")) {
			msg.setSet("numberOfStudentFinished", numberToPlus);
		}
		msg.setWhere("testCode", code);
		return msg;
	}

	/**
	 * Constructs a database select message to retrieve TestToExecute associated
	 * with a code.
	 *
	 * @param code The code object for the right TestToExecute.
	 * @return A Msg object representing the database select message.
	 */
	public Msg selectTestToExecuteByCode(String code) {
		Msg msg = new Msg(MsgType.select);
		msg.setSelect("*");
		msg.setFrom("testtoexecute");
		msg.setWhere("testCode", code);
		return msg;
	}

	/**
	 * check if the id is valid.
	 *
	 * @param id   the id that enter the user to enter a test.
	 * @param user the user to get the id of the user.
	 * @return boolean false if the id is not valid. true if the id is valid and corresponding to the user.
	 */
	public boolean checkValidId(String id, User user) {
		NotificationAlertsController alert = new NotificationAlertsController();
		if (id.isEmpty() == true) {
			alert.showWarningAlert("ID is empty!");
			return false;
		}
		if (!user.getId().equals(id)) {
			alert.showWarningAlert("ID is worng!");
			return false;
		}
		return true;
	}

	/**
	 * check if the code is valid to enter a test.
	 *
	 * @param code the code for the write test.
	 * @return boolean false if the code is not valid. true if the code is valid.
	 */
	public boolean checkValidCode(String code) {
		NotificationAlertsController alert = new NotificationAlertsController();
		if (code.length() != 4) {
			alert.showWarningAlert("The code must be 4 in length!");
			return false;
		}
		if (!code.matches("\\d+")) {
			alert.showWarningAlert("The code must consist of only digits!");
			return false;
		}
		return true;
	}

	/**
	 * Constructs a database select message to retrieve TestToExecute associated
	 * with a user.
	 * it includes the testToExecute object, the Test object inside it, and the
	 * Course object inside it.
	 *
	 * @param user The User object for whom to retrieve the TestToExecute.
	 * @return A Msg object representing the database select message.
	 */
	public Msg selectTestToExecuteByUser(User user) {
		Msg msg = new Msg(MsgType.select);
		msg.setSelect("testtoexecute.*, test.*, course.*");
		msg.setFrom("testtoexecute");
		msg.setFrom("test");
		msg.setFrom("course");
		msg.setWhereCol("testtoexecute.testId", "test.id");
		msg.setWhereCol("test.courseNumber", "course.number");
		msg.setWhere("testtoexecute.lecturerId", user.getId());
		return msg;
	}

	/**
	 * Constructs a database select message to retrieve TestToExecute associated
	 * with hod user.
	 * it includes the testToExecute object, the Test object inside it, and the
	 * Course object inside it.
	 *
	 * @param user The User object for whom to retrieve the TestToExecute.
	 * @return A Msg object representing the database select message.
	 */
	public Msg selectTestToExecuteByHod(User user) {
		Msg msg = new Msg(MsgType.select);
		msg.setSelect("testtoexecute.*, test.*, course.*");
		msg.setFrom("testtoexecute");
		msg.setFrom("test");
		msg.setFrom("course");
		msg.setFrom("hod_subject");
		msg.setWhereCol("testtoexecute.testId", "test.id");
		msg.setWhereCol("test.courseNumber", "course.number");
		msg.setWhereCol("hod_subject.subjectNumber", "course.subjectNum");
		msg.setWhere("hod_subject.hodId", user.getId());
		return msg;
	}

	/**
	 * Constructs a database select message to retrieve TestToExecute associated
	 * with a user according to the courses the user teaches.
	 * it includes the testToExecute object, the Test object inside it, and the
	 * Course object inside it.
	 * it returns only the tests which is still running. (not locked yet).
	 * 
	 * @param user The User object for whom to retrieve the TestToExecute.
	 * @return A Msg object representing the database select message.
	 */
	public Msg selectRunningTestToExecuteByUser(User user) {
		Msg msg = new Msg(MsgType.select);
		msg.setSelect("testtoexecute.*, test.*, course.*");
		msg.setFrom("testtoexecute");
		msg.setFrom("test");
		msg.setFrom("course");
		msg.setWhereCol("testtoexecute.testId", "test.id");
		msg.setWhereCol("test.courseNumber", "course.number");
		msg.setWhere("testtoexecute.lock", "false");
		msg.setWhere("testtoexecute.lecturerId", user.getId());
		return msg;
	}

	/**
	 * Constructs a database select message to retrieve TestToExecute associated
	 * with a Lecturer user.
	 * it includes the testToExecute object and the User object inside it.
	 * 
	 * @param id user The User object for whom to retrieve the TestToExecute.
	 * @return Msg object representing the database select message.
	 */
	public Msg selectTestToExecuteByLecturer(String id) {
		Msg msg = new Msg(MsgType.select);
		msg.setSelect("testtoexecute.*");
		msg.setFrom("testtoexecute");
		msg.setFrom("user");
		msg.setWhereCol("testtoexecute.lecturerId", "user.id");
		msg.setWhere("user.id", id);
		return msg;
	}

	/**
	 * Constructs a database select message to retrieve TestToExecute associated
	 * with Course.
	 * it includes the testToExecute object, the Test object and the Course object
	 * inside it.
	 * 
	 * @param name The Course name for whom to retrieve the TestToExecute.
	 * @return A Msg object representing the database select message.
	 */
	public Msg selectTestToExecuteByCourseName(String name) {
		Msg msg = new Msg(MsgType.select);
		msg.setSelect("testtoexecute.*");
		msg.setFrom("testtoexecute");
		msg.setFrom("course");
		msg.setFrom("test");
		msg.setWhereCol("course.number", "test.courseNumber");
		msg.setWhereCol("testtoexecute.testId", "test.id");
		msg.setWhere("course.name", name);
		return msg;
	}

	/**
	 * Retrieves the names of the tests as an ObservableList.
	 * 
	 * @param testToExecuteLst subjectsLst The list of the testToExecuteLst.
	 * @return ArrayList The ObservableList of subject names.
	 */
	public ArrayList<String> getTestToExecuteNames(ArrayList<TestToExecute> testToExecuteLst) {
		// ObservableList<String> testToExecuteNames =
		// FXCollections.observableArrayList();
		ArrayList<String> testToExecuteNames = new ArrayList<String>();
		for (TestToExecute t : testToExecuteLst) {
			testToExecuteNames
					.add("(code-" + t.getTestCode() + ")  " + t.getTest().getCourse().getName() + "  " + t.getDate());
		}
		return testToExecuteNames;
	}

	/**
	 * Finds a TestToExecute object from the given list based on the provided name.
	 *
	 * @param newValue         The name of the TestToExecute to find.
	 * @param testToExecuteLst The list of TestToExecute objects to search in.
	 * @return The TestToExecute object with the matching name, or null if not
	 *         found.
	 */
	public TestToExecute findTestToExecuteByName(String newValue, ArrayList<TestToExecute> testToExecuteLst) {
		for (TestToExecute t : testToExecuteLst)
			if (newValue.equals(
					"(code-" + t.getTestCode() + ")  " + t.getTest().getCourse().getName() + "  " + t.getDate()))
				return t;
		return null;
	}

	/**
	 * Executes a list of tests and generates a list of TestToExecute objects based
	 * on the provided test list and user.
	 * including the FX fields needed for the table.
	 *
	 * @param testLst The list of tests to execute.
	 * @param user    The user executing the tests.
	 * @return The list of generated TestToExecute objects.
	 */
	@SuppressWarnings({ "unchecked" })
	public ArrayList<TestToExecute> executeListOfTests(ArrayList<Test> testLst, User user) {
		ArrayList<TestToExecute> lst = new ArrayList<>();
		for (Test t : testLst) {
			TestToExecute tmp = new TestToExecute();
			tmp.setTestId(t.getId());
			tmp.setAverage(-1.0);
			tmp.setMedian(-1.0);
			tmp.setLock("false");
			tmp.setLecturerId(user.getId());
			tmp.setCourse(t.getCourse());
			tmp.setNewButton();
			tmp.setButtonText("Show");
			tmp.setNewRadioButton();
			tmp.setNewComboBox();
			tmp.getComboBox().getItems().addAll("online", "manual");
			tmp.getComboBox().setValue("online");
			tmp.setNewTextField(); // date
			tmp.getTextField().setPromptText("22/06/2023");
			tmp.setNewTextField1(); // code
			tmp.getTextField1().setPromptText("4 digits");
			tmp.getComboBox().setDisable(true);
			tmp.getTextField().setDisable(true);
			tmp.getTextField1().setDisable(true);
			tmp.setTest(t);
			lst.add(tmp);
		}
		return lst;
	}

	/**
	 * Checks the inputs for creating a new question.
	 *
	 * @param selectedTest The TestToExecute to check the inputs for.
	 * @param user
	 * 
	 * @return Object representing the new TestToExecute if the inputs are valid,
	 *         or a String with an error message if the inputs are not invalid.
	 */
	public Object checkInputs(TestToExecute selectedTest, String userId, String date, String testCode, String testingType) {
		String error = new String("");
		if (date.length() == 0)
			error += "You must enter test date.\n";
		selectedTest.setDate(date);
		try {
			if (Integer.valueOf(testCode) > 9999
					|| Integer.valueOf(testCode) < 1000)
				error += "Test code must be a number between 1000 and 9999.\n";
			selectedTest.setTestCode(Integer.valueOf(testCode));
		} catch (Exception e) {
			error += "Test code must be an integer.\n";
		}
		selectedTest.setTestingType(testingType);
		if (error.length() != 0)
			return error;
		selectedTest.setLecturerId(userId);
		return selectedTest;
	}

	/**
	 * creates and returns a Msg for inserting a TestToExecute to DB.
	 *
	 * @param t newTestToExecute The newTestToExecute to insert.
	 * @return Msg object representing the database insert message.
	 */
	public Msg insertTestToExecute(TestToExecute t) {
		Msg msg = new Msg(MsgType.insert);
		msg.setTableToUpdate("testtoexecute");
		msg.setColNames("testCode, testId, testingType, date, lecturerId");
		ArrayList<Object> tmp = new ArrayList<>();
		tmp.add(t.getTestCode());
		tmp.add(t.getTestId());
		tmp.add(t.getTestingType());
		tmp.add(t.getDate());
		tmp.add(t.getLecturerId());
		msg.setValues(tmp);
		return msg;
	}

	/**
	 * Returns an ObservableList of TestToExecute objects with FX values.
	 *
	 * @param runningTestLst The list of TestToExecute objects.
	 * @return The ObservableList of TestToExecute objects with FX values.
	 */
	public ObservableList<TestToExecute> getObservLstWithFXValues(ArrayList<TestToExecute> runningTestLst) {
		ObservableList<TestToExecute> runningTestTable = FXCollections.observableArrayList(runningTestLst);
		for (TestToExecute runningTest : runningTestLst) {
			runningTest.setNewTextField(); // duration
			runningTest.setNewTextField1(); // explanation for changing the duration.
			runningTest.setNewRadioButton(); // select
			runningTest.getTextField().setPromptText(runningTest.getTest().getDuration().toString()); // explanation
			runningTest.getTextField1().setPromptText("Explanation for change");
			runningTest.getTextField().setDisable(true); // duration
			runningTest.getTextField1().setDisable(true); // explanation
			runningTest.getRadioButton().selectedProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue == true) {
					runningTest.getTextField().setDisable(false);
					runningTest.getTextField1().setDisable(false);
				} else {
					runningTest.getTextField().clear();
					runningTest.getTextField1().clear();
					runningTest.getTextField().setDisable(true);
					runningTest.getTextField1().setDisable(true);
				}
			});
		}
		return runningTestTable;
	}

	/**
	 * Retrieves the selected TestToExecute object from a collection based on the
	 * selected radio button.
	 *
	 * @param lst The collection of TestToExecute objects.
	 * @return The selected TestToExecute object, or null if none is selected or an
	 *         exception occurs.
	 */
	public TestToExecute getSelectedTest(Collection<TestToExecute> lst) {
		for (TestToExecute t : lst) {
			try {
				if (t.getRadioButton().isSelected())
					return t;
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * Generates a message to lock a test.
	 *
	 * @param test The TestToExecute object representing the test to be locked.
	 * @return The message containing the lock instructions.
	 */
	public Msg getMsgToLockTest(TestToExecute test) {
		Msg msgM = new Msg(MsgType.manyMessages);
		Msg msgUpdate = new Msg(MsgType.update);
		msgUpdate.setTableToUpdate("testtoexecute");
		msgUpdate.setSet("`lock`", "true");
		msgUpdate.setWhere("testCode", test.getTestCode());
		Msg msgLock = new Msg(MsgType.lockTest);
		msgLock.setTestCode(test.getTestCode());
		msgM.setMsgLst(msgUpdate);
		msgM.setMsgLst(msgLock);
		return msgM;
	}
	/**
	 * Constructs and returns a Msg object to select test to execute by test code.
	 *
	 * @param code The test code to search for.
	 * @return The Msg object with the select query.
	 */
	public Msg selectTestToExecuteByTestCode(Integer code) {
		Msg msg = new Msg(MsgType.select);
		msg.setSelect("testtoexecute.*, test.*, course.*");
		msg.setFrom("testtoexecute");
		msg.setFrom("test");
		msg.setFrom("course");
		msg.setWhereCol("testtoexecute.testId", "test.id");
		msg.setWhereCol("test.courseNumber", "course.number");
		msg.setWhere("testCode", code);
		return msg;
	}
}