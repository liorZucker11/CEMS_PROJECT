package gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import client.ChatClient;
import communication.Msg;
import communication.MsgType;
import controllers.CountDown;
import controllers.TimeController;
import enteties.Question;
import enteties.StudentTest;
import enteties.TestToExecute;
import enteties.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Controller class for the OnlineTest screen.
 * 
 * @author Lior Zucker 
 */
public class OnlineTestController extends AbstractController implements CountDown, Testing{
	 /**
     * The container for displaying the test data.
     */
    @FXML
    private VBox dataVbox;

    /**
     * Labels for displaying test information.
     */
    @FXML
    private Label info1, info2, info3, info4, info5, timeLabel;

    /**
     * Container for displaying instructions for students.
     */
    @FXML
    private TextFlow instructionsForStudentTextFlow;

    /**
     * Labels for displaying points per question.
     */
    
    /**
     * Fields for managing the test questions and answers
     */
    private ArrayList<Question> questions = new ArrayList<Question>();
    private ArrayList<ToggleGroup> toggleGroups = new ArrayList<ToggleGroup>();

    /**
     * Controllers
     */
    private TimeController timeController;
    
    /**
     * GridPane for displaying the questions
     */ 
    GridPane gridPane;

    /**
     * Test data
     */
    private Integer code;
    private TestToExecute numbersOfStudent;
    private Integer grade = 0;
    private String answers = "";
    private Msg msg;
    private int timeOfStudent;
    private String flagEndOrMiddle;
    private TestToExecute testToExecute;
   
    /**
     * Creates an instance of the OnlineTestController class.
     * This constructor initializes the controller and sets up the display of the test information and questions.
     * It populates various labels and text flows with the relevant information about the test and its instructions for students and lecturers.
     * The formatting and styling of the displayed elements are also adjusted.
     */
    public OnlineTestController() {

        // Retrieve the test data and questions from the server
        if (ChatClient.lastCurrentScreen instanceof StartTestController) {
            testToExecute = ((StartTestController) ChatClient.lastCurrentScreen).getTestToExecuteToShow();
            code = testToExecute.getTestCode();
        }
        Msg msg = questionController.getQuestionAndPointsByTestCode(code);
        sendMsg(msg);
        if(msgReceived!=null) {
        	questions = msgReceived.convertData(Question.class); // ArrayList
        }
        // Create a time controller for managing the test time
        timeController = new TimeController(testToExecute.getTest().getDuration(), this);
    }

    /**
     * Initializes the controller and sets up the display of the test information and questions.
     * This method is automatically called after the FXML file has been loaded.
     */
    @FXML
    protected void initialize() {
    	if(questions==null) {notification.showErrorAlert("there are no test in this code."); return;}
        setInfo();

        int questionCounter = 1;
        gridPane = new GridPane();
        gridPane.setHgap(50);
        gridPane.setVgap(10);
        int row = 0;

        // Iterate over the list of questions and create the necessary UI elements
        for (int i = 0; i < questions.size(); i++) {
	    	gridPane.add(new Label(), 0, i + row);
	    	row++;
            Question question = questions.get(i); 
            // Create and configure the question label
            Label questionLabel = new Label(questionCounter + ". " + question.getQuestion());
            Label instuctionsQuestionLabel;
            if (question.getInstructions() != null)
                instuctionsQuestionLabel = new Label("Instruction for question: " + question.getInstructions());
            else
                instuctionsQuestionLabel = new Label("");
            questionCounter++;
            questionLabel.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-weight: bold; -fx-font-size: 14px;");

            // Create and configure the points label
            Label pointsLabel = new Label("Points: " + question.getPoints());
            pointsLabel.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 14px;");

            // Add the question label, instruction label, and points label to the grid pane
            gridPane.add(instuctionsQuestionLabel, 0, i + row);
            row++;
            gridPane.add(questionLabel, 0, i + row);
            gridPane.add(pointsLabel, 1, i + row);

            // Set up the 4 radio buttons in a toggle group and set each answer
            ToggleGroup answerGroup = new ToggleGroup();
            for (int j = 0; j < 4; j++) {
                row++;
                NumberRadioButton answerRadioButton = new NumberRadioButton(question.getAnswers()[j],(j+1));
                answerRadioButton.setToggleGroup(answerGroup);
                gridPane.add(answerRadioButton, 0, i + row);
            }
           
            toggleGroups.add(answerGroup);
        }
        // Add the grid pane to the data VBox
        dataVbox.getChildren().add(gridPane);
        timeController.startTimer();
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
     * Handles the action when the Submit button is clicked.
     * This method is triggered when the Submit button is clicked by the user to submit their test.
     * It stops the timer, calculates the time taken by the student, and prompts for confirmation.
     * If confirmed, it updates the test status, calculates the grade, and saves the student's answers and grade.
     * It also checks if the student is the last one to submit and locks the test if necessary.
     * Finally, it updates the average and median grades for the test.
     *
     * @param event The action event triggered by clicking the Submit button.
     * @throws Exception If an exception occurs during the process.
     */
    @FXML
    void submitBtn(ActionEvent event) throws Exception {
        // Stop the timer
        timeController.stopTimer();

        // Calculate the time taken by the student
        timeOfStudent = testToExecute.getTest().getDuration() - timeController.timeLeft();

        // Prompt for confirmation before submitting the test
        notification.setOnCancelAction(new Runnable() {
            @Override
            public void run() {
                // If the confirmation is canceled, resume the timer
                timeController.startTimer();
            }
        });
        notification.setOnOkAction(new Runnable() {
            @Override
            public void run() {
                // If confirmed, proceed with submitting the test
                if (testToExecute.getLock().equals("true")) {
                    // Test is locked, cannot submit
                    flagEndOrMiddle = "End";
                    testIsLockCantSubmmit();
                    updateAverageAndMedian();
                } else {
                    // Test can be submitted
                    testIsSubmit(timeOfStudent);
                    checkIfStudentIsTheLastOne();
                    updateAverageAndMedian();
                }
                try {start("StudentMenu", "Login");
                ((Menu)ChatClient.getScreen(ChatClient.user.getPremission()+"Menu")).setWelcome("Welcome " + ChatClient.user.getName());} catch (Exception e) {e.printStackTrace();}
            }
        });
        notification.showConfirmationAlert(ChatClient.user.getName() + " Are you sure?","After clicking the OK button, the submission is final and there is no option to change it");
    }
    /**
     * yuval rozner
     * i call to this method in checkIfStudentIsTheLastOne and testGotManualyLockedByLecturer.
     * in hash map - two student that copy each from each other.
     * get code for test - code - type Integer
     * get lecturerId - testToExecute.getLecturerId() 
     * get name student - studentWhoCopy.get().getUser().getName()
     * get id student - studentWhoCopy.get().getStudentId()
     */
    
    
    /**
     * Checks for copied answers among students who took the test.
     * Compares the answers of each student to identify potential copies.
     * pop massage at the right lecturer with information on the students who copied answers.
     */
    public void checkCopy() {
    	HashMap<StudentTest, StudentTest> studentWhoCopy = new HashMap<>();
    	
    	//get the correct answers of a test
    	StringBuilder sb = new StringBuilder();
    	for(Question q:questions) {
    		sb.append(Integer.toString(q.getCorrectAnswer()));
    	}
    	
    	//get answer id and name of all student that did this test.
    	msg=studentTestController.selectAllAnswersOfstudentCodeTest(code);
        sendMsg(msg);
        if(msgReceived==null) {return;}
        ArrayList<StudentTest> listOfStudent = msgReceived.convertData(StudentTest.class);
        
        //get in the hush map the student who copy
        for(StudentTest student : listOfStudent) {
        	for(StudentTest copyStudent : listOfStudent) {
        		if(!student.equals(copyStudent)) {//give up on the cases is the same student.
        			if(student.getAnswers().equals(copyStudent.getAnswers()) && (!student.getAnswers().equals(sb))) {
        				if(!(studentWhoCopy.containsKey(copyStudent) && studentWhoCopy.containsValue(student))) {//check if they do not in the hash already.
        					studentWhoCopy.put(student, copyStudent);
        					break;
        				}	
        			}
        		}
        	}
        }
        String poptext = "The following students might cheat and copy in test code "+code+":\n";
        for (Entry<StudentTest, StudentTest> entry : studentWhoCopy.entrySet()) {
        	StudentTest key = entry.getKey();
        	StudentTest value = entry.getValue();
        	poptext += "\t " + key.getUser().getName()+ " <---> " + value.getUser().getName()+"\n";
        }
        if(!studentWhoCopy.isEmpty()) {
        	Msg popMsg = new Msg(MsgType.pop);
        	User lecturer = new User();
        	lecturer.setId(testToExecute.getLecturerId());
        	lecturer.setUsername("lecturer12");
        	popMsg.setUser(lecturer);
        	popMsg.setPopText(poptext);
        	Msg many = new Msg(MsgType.manyMessages);
        	many.setMsgLst(popMsg);
        	sendMsg(many);
        }
    }

    /**
     * Checks if the current student is the last one to submit the test and locks the test if necessary.
     * It retrieves the number of students who have started and finished the test and calculates
     * the difference to determine if the current student is the last one.
     * If the current student is the last one, it sends a message to lock the test.
     */
    public void checkIfStudentIsTheLastOne() {
        // Retrieve the number of students who have started and finished the test
        msg = testToExecuteController.checkIfTheStudentIsLast(StartTestController.getTestToExecute().getTestCode());
        sendMsg(msg);
        if(msgReceived==null) {return;}
        numbersOfStudent = msgReceived.convertData(TestToExecute.class).get(0);

        // Calculate the difference between the number of students who started and finished the test
        Integer needToLock = numbersOfStudent.getNumberOfStudentsStarted() - numbersOfStudent.getNumberOfStudentsFinished() - numbersOfStudent.getNumberOfStudents();

        // Check if the current student is the last one and lock the test if necessary
        if (needToLock == 0) {
            msg = testToExecuteController.getMsgToLockTest(StartTestController.getTestToExecute());
            sendMsg(msg);
            checkCopy();
        }
    }
    
    /**
     * Updates the average and median grades for a specific test code.
     * It retrieves all the student test records for the specified test code,
     * calculates the average and median grades from the list of grades,
     * and updates the median and average values in the test record.
     */
    public void updateAverageAndMedian() {
        double average = 0;
        double median = 0;

        // Retrieve all student test records for the specified test code
        msg = studentTestController.selectAllstudentBySpecificCodeTest(code.toString());
        sendMsg(msg);
        if(msgReceived==null) {return;}
        ArrayList<StudentTest> listOfStudent = msgReceived.convertData(StudentTest.class);
        ArrayList<Integer> listOfGrades = new ArrayList<>();

        // Extract grades from the student test records and calculate the average
        for (StudentTest student : listOfStudent) {
            listOfGrades.add(student.getGrade());
            average += student.getGrade();
        }
        average = average / listOfGrades.size();

        // Sort the list of grades and calculate the median
        Collections.sort(listOfGrades);
        median = listOfGrades.get(listOfGrades.size() / 2);

        // Update the median and average values in the test record
        msg = testToExecuteController.updateMedianAndAverage(code.toString(), average, median);
        sendMsg(msg);
    }
    
    /**
     * Retrieves the selected answers from the toggle groups and builds a string representation of the answers.
     * If a toggle group does not have a selected toggle (no answer provided), a "0" is appended to the string.
     * Otherwise, the number associated with the selected toggle is appended.
     */
    public void getAnswers() {
        StringBuilder sb = new StringBuilder();

        // Iterate over the toggle groups
        for (int i = 0; i < toggleGroups.size(); i++) {
            if (toggleGroups.get(i).getSelectedToggle() == null) {
                // No answer provided for the question, append "0"
                sb.append("0");
            } else {
                // Answer is selected, append the associated number
                sb.append((((NumberRadioButton) toggleGroups.get(i).getSelectedToggle()).getNumber()));
            }
        }

        // Store the answers as a string
        answers = sb.toString();
    }

    /**
     * Calculates the grade for the student based on the selected answers.
     * Iterates over the toggle groups and checks if the user has answered the question.
     * If the user has answered and the selected answer is correct, the corresponding points are added to the grade.
     */
    public void calculateGrade() {
        for (int i = 0; i < toggleGroups.size(); i++) {
            if (toggleGroups.get(i).getSelectedToggle() != null) {
                // User has answered the question
                if (questions.get(i).getCorrectAnswer() == (((NumberRadioButton) toggleGroups.get(i).getSelectedToggle()).getNumber())) {
                    // Selected answer is correct, add points to the grade
                    grade += questions.get(i).getPoints();
                    System.out.println(grade);
                }
            }
        }
    }

    /**
     * Submits the test for the student.
     * Updates the necessary data, calculates the grade, inserts the answers and grade into the database,
     * inserts the distribution data, shows an information alert, and redirects to the student menu.
     *
     * @param timeOfStudent The time taken by the student to complete the test.
     */
    public void testIsSubmit(Integer timeOfStudent) {
        // Update data
        msg = testToExecuteController.updateNumberOfStudenByOne(1, code.toString(), "finish");
        sendMsg(msg);

        calculateGrade();
        getAnswers();

        // Insert answers and grade into the database
        msg = studentTestController.InsertAnswersAndGradeManual("false", timeOfStudent, answers, grade, ChatClient.user.getId(), code.toString());
        sendMsg(msg);

        // Insert distribution data
        msg = testToExecuteController.insertDistributionByCode(code.toString(), grade, 1);
        sendMsg(msg);

        notification.showInformationAlert("The test was successfully submitted!");

        try {start("StudentMenu", "Login");} catch (Exception e) {e.printStackTrace();
        ((Menu)ChatClient.getScreen(ChatClient.user.getPremission()+"Menu")).setWelcome("Welcome " + ChatClient.user.getName());}
    }

    /**
     * Handles the situation when the test is locked and cannot be submitted.
     * Updates the necessary data, shows an error alert, and inserts the answers and grade into the database.
     * If the flagEndOrMiddle is set to "End", the grade is set to 0.
     */
    public void testIsLockCantSubmmit() {
    	
        msg = testToExecuteController.updateNumberOfStudenByOne(1, Integer.toString(StartTestController.getTestToExecute().getTestCode()), "cantSubmit");
        sendMsg(msg);

        notification.showErrorAlert("The test is locked!\n You will not be able to submit the test!");

        if (flagEndOrMiddle.equals("End")) {
            grade = 0;
            msg = studentTestController.InsertAnswersAndGradeManual("false", timeOfStudent, "00000", grade, ChatClient.user.getId(), code.toString());
        } else {
            calculateGrade();
            getAnswers();
            msg = studentTestController.InsertAnswersAndGradeManual("false", timeOfStudent, answers, grade, ChatClient.user.getId(), code.toString());
        }

        sendMsg(msg);

        // Insert distribution data
        msg = testToExecuteController.insertDistributionByCode(code.toString(), grade, 1);
        sendMsg(msg);

        try {
            start("StudentMenu", "Login");
            ((Menu)ChatClient.getScreen(ChatClient.user.getPremission()+"Menu")).setWelcome("Welcome " + ChatClient.user.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	/**
	 * Handles the case when the student has exceeded the allowed time for the test.
	 */
	public void timeOfStudentIsOverLoad() {
		notification.showErrorAlert("You have exceeded the allowed time!");
		msg = testToExecuteController.updateNumberOfStudenByOne(1,code.toString(),"cantSubmit");
		sendMsg(msg);
		msg=studentTestController.InsertAnswersAndGradeManual("false",timeOfStudent,"00000",0,ChatClient.user.getId() ,code.toString());
		sendMsg(msg);
		msg=testToExecuteController.insertDistributionByCode(code.toString(),grade,1);
		sendMsg(msg);
		try {start("StudentMenu", "Login"); ((Menu)ChatClient.getScreen(ChatClient.user.getPremission()+"Menu")).setWelcome("Welcome " + ChatClient.user.getName());} catch (Exception e) {e.printStackTrace();}
	}

	/**
	 * Sets the text of the countdown label to the specified string.
	 *
	 * @param countdownText The string to be set as the text of the countdown label.
	 */
	@Override
	public void setTextCountdown(String countdownText) {
	    timeLabel.setText(countdownText);
	}

	/**
	 * Callback method called when the time limit for the student has ended.
	 * Performs necessary actions such as notifying the end of time, checking if the student is the last one,
	 * and updating the average and median.
	 */
	@Override
	public void endOfTime() {
	    System.out.println("The time is up, my friend");
	    timeOfStudentIsOverLoad();
	    checkIfStudentIsTheLastOne();
	    updateAverageAndMedian();
	}

	/**
	 * Callback method called when the test is manually locked by the lecturer.
	 * Checks if the provided test code matches the current test code and displays an error alert.
	 * Sets the flag indicating the test is in the middle state and invokes the method to handle a locked test.
	 * Finally, updates the average and median.
	 *
	 * @param testCode The test code to compare with the current test code.
	 */
	@Override
	public void testGotManualyLockedByLecturer(String testCode) {
	    if (!testCode.equals(Integer.toString(code))) {
	        return;
	    }
	    // Stop the timer
        timeController.stopTimer();
        // Calculate the time taken by the student
        timeOfStudent = testToExecute.getTest().getDuration() - timeController.timeLeft();
        notification.setOnOkAction(new Runnable() {	
			@Override public void run() {
				flagEndOrMiddle="Middle";
				testIsLockCantSubmmit();
				updateAverageAndMedian();
				checkCopy();
		}});
        notification.showConfirmationAlertWithOnlyOk("Please click OK to continue the process","Sorry, but the test got locked by your lecturer..");
	}

	/**
	 * A custom radio button that associates a number with its selection.
	 */
	class NumberRadioButton extends RadioButton {
	    private int number;

	    /**
	     * Constructs a NumberRadioButton with the specified text and number.
	     *
	     * @param text   The text to be displayed next to the radio button.
	     * @param number The number associated with the radio button.
	     */
	    public NumberRadioButton(String text, int number) {
	        super(text);
	        this.number = number;
	    }

	    /**
	     * Returns the number associated with the radio button.
	     *
	     * @return The number associated with the radio button.
	     */
	    public int getNumber() {
	        return number;
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