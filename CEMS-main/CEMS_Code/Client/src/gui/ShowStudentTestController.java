package gui;

import java.util.ArrayList;

import client.ChatClient;
import communication.Msg;
import enteties.Question;
import enteties.StudentTest;
import enteties.TestToExecute;
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
 * The controller class for displaying a student test.
 */
public class ShowStudentTestController extends AbstractController {
    
    /**
     * The container for displaying the test data.
     */
    @FXML
    private VBox dataVbox;
    
    /**
     * Labels for displaying test information.
     */
    @FXML
    private Label info1, info2, info3, info4, info5, info6, info7;
    
    /**
     * Container for displaying instructions for students/lecturers.
     */
    @FXML
    private TextFlow instructionsForStudentTextFlow,instructionsForLecturerTextFlow;
    
    /**
     * Toggle groups for answer options.
     */
    private ArrayList<ToggleGroup> toggleGroups = new ArrayList<ToggleGroup>();
    
    /**
     * Labels for displaying points per question.
     */
    ArrayList<Label> pointsLabels = new ArrayList<>();
    
    /**
     * The state of the screen: {studentShowTest, lecturerHodShowStudentTest, lecturerHodShowTest}.
     */
    private String screenState;
  
    GridPane gridPane;
    
    StudentTest studentTest;
    
    TestToExecute testToExecute;
    
    String testName;
    
    /**
     * List of questions for the test.
     */
    ArrayList<Question> questions = new ArrayList<Question>();
    
    /**
     * Constructs a new ShowStudentTestController and initializes the data.
     * This constructor initializes the necessary fields and retrieves the questions from the DB for the test.
     */
    public ShowStudentTestController() {
        Msg msg = initializeDataAndGetMsg();
        sendMsg(msg);
        if(msgReceived!=null)
        	questions = msgReceived.convertData(Question.class); // ArrayList
        if (questions == null)
        	return;
    }
    /**
     * Initializes the necessary data and retrieves the message containing the questions for the test.
     *
     * @return The message object containing the questions.
     */
    private Msg initializeDataAndGetMsg() {
        Msg msg = null;
        // ApproveChangesController, ExecuteTestController, ShowGradeController, ShowTestDataController
        if (ChatClient.lastCurrentScreen instanceof Tests) {
            screenState = ((Tests) ChatClient.lastCurrentScreen).getScreenState();
            studentTest = ((Tests) ChatClient.lastCurrentScreen).getStudentTestToShow();
            testToExecute = ((Tests) ChatClient.lastCurrentScreen).getTestToExecuteToShow();
            if (testToExecute == null)
                testToExecute = studentTest.getTestToExecute();
            testName = "(code-" + testToExecute.getTestCode() + ")  " + testToExecute.getTest().getCourse().getName() + "  " + testToExecute.getDate();

            // Create Msg to be send to the Server
            msg = questionController.getQuestionAndPointsByTestId(testToExecute.getTestId());
        }
        return msg;
    }

    /**
     * Initializes the controller and sets up the display of the test information and questions.
     * This method is automatically called after the FXML file has been loaded.
     */
    @FXML
    protected void initialize() {
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
            pointsLabels.add(pointsLabel); // Save the points label for later changes
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
                RadioButton answerRadioButton = new RadioButton(question.getAnswers()[j]);
                answerRadioButton.setMouseTransparent(true);
                answerRadioButton.setToggleGroup(answerGroup);
                gridPane.add(answerRadioButton, 0, i + row);
            }
           
            toggleGroups.add(answerGroup);
        }

        // Add the grid pane to the data VBox
        dataVbox.getChildren().add(gridPane);

        // Show correct answers based on the screen state
        if (screenState.equals("lecturerHodShowStudentTest") || screenState.equals("studentShowTest"))
            showCorrectAnswersAndStudentAnswer();
        else // screenState = "lecturerHodShowTest"
            showCorrectAnswers();
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
        info2.setText("Course: " + testToExecute.getTest().getCourse().getName());
        info3.setText("Duration:" + testToExecute.getTest().getDuration() + " minutes");

        // Set instructions for students
        if (testToExecute.getTest().getInstructionsForStudent() != null) {
            textForTextFlow = new Text("Instructions For Student: " + testToExecute.getTest().getInstructionsForStudent());
            textForTextFlow.wrappingWidthProperty().bind(instructionsForStudentTextFlow.widthProperty());
            textForTextFlow.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 14));
            instructionsForStudentTextFlow.getChildren().addAll(textForTextFlow);
        }

        // Switch statement based on screen state
        switch (screenState) {
            case "lecturerHodShowStudentTest":
                info1.setText("Test Code: " + testToExecute.getTestCode());
                info4.setText("Date: " + testToExecute.getDate());
                info5.setText("Student Name:" + studentTest.getStudentName());
                info6.setText("Grade:" + String.valueOf(studentTest.getGrade()));

                // Set instructions for lecturer
                if (testToExecute.getTest().getInstructionsForLecturer() != null) {
                    textForTextFlow = new Text("Instructions For Lecturer: " + testToExecute.getTest().getInstructionsForLecturer());
                    textForTextFlow.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 14));
                    textForTextFlow.wrappingWidthProperty().bind(instructionsForLecturerTextFlow.widthProperty());
                    instructionsForLecturerTextFlow.getChildren().addAll(textForTextFlow);
                }
                break;
            case "lecturerHodShowTest":
                info1.setText("Test Id: " + testToExecute.getTest().getId());

                // Set instructions for lecturer
                if (testToExecute.getTest().getInstructionsForLecturer() != null) {
                    textForTextFlow = new Text("Instructions For Lecturer: " + testToExecute.getTest().getInstructionsForLecturer());
                    textForTextFlow.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 14));
                    textForTextFlow.wrappingWidthProperty().bind(instructionsForLecturerTextFlow.widthProperty());
                    instructionsForLecturerTextFlow.getChildren().addAll(textForTextFlow);
                }
                break;
            case "studentShowTest":
                info1.setText("Test Code: " + testToExecute.getTestCode());
                info4.setText("Date: " + testToExecute.getDate());
                info5.setText("Student Name:" + studentTest.getStudentName());
                info6.setText("Grade:" + String.valueOf(studentTest.getGrade()));
                break;
        }
    }

    /**
     * Displays the correct answers for each question.
     * This method highlights the correct answer for each question by styling the corresponding radio button,
     * indicating that it is the correct answer.
     */
    public void showCorrectAnswers() {
        for (int i = 0; i < toggleGroups.size(); i++) {
            Question question = questions.get(i);
            ToggleGroup questionToggleGroup = toggleGroups.get(i);
            int correctAnswerIndex = question.getCorrectAnswer() - 1;

            // Retrieve the correct radio button and style it as the correct answer
            RadioButton correctRadioButton = (RadioButton) questionToggleGroup.getToggles().get(correctAnswerIndex);
            correctRadioButton.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 14px; -fx-text-fill: green;");
            correctRadioButton.setText(correctRadioButton.getText() + " - Correct answer");
            correctRadioButton.setSelected(true);
        }
    }

    /**
     * Displays the correct answers for each question and highlights the student's selected answer.
     * This method highlights the correct answer for each question by styling the corresponding radio button,
     * indicating that it is the correct answer. Additionally, it identifies and highlights the student's selected
     * answer, indicating whether it is correct or incorrect.
     */
    public void showCorrectAnswersAndStudentAnswer() {
        for (int i = 0; i < toggleGroups.size(); i++) {
            Question question = questions.get(i); 
            String[] answers = studentTest.getAnswers().split("");
            ToggleGroup questionToggleGroup = toggleGroups.get(i);
            int correctAnswerIndex = question.getCorrectAnswer() - 1;

            // Retrieve the correct radio button and style it as the correct answer
            RadioButton correctRadioButton = (RadioButton) questionToggleGroup.getToggles().get(correctAnswerIndex);
            correctRadioButton.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 14px; -fx-text-fill: green;");
            correctRadioButton.setText(correctRadioButton.getText() + " - Correct answer");
            // Get the student's selected answer index
            int selectedAnswerIndex = Integer.parseInt(answers[i]) - 1;
            if (selectedAnswerIndex == -1) { //the student didn't choose an answer
            	Label didntChooseLabel = new Label((i+1) + ". You didn't choose any answer!");
            	didntChooseLabel.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 14px; -fx-text-fill: red;");
            	gridPane.add(didntChooseLabel, 0, i*7);
            	pointsLabels.get(i).setText("Points: 0");
                pointsLabels.get(i).setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 14px; -fx-text-fill: red;");
            }
            else {
            	 RadioButton selectedRadioButton = (RadioButton) questionToggleGroup.getToggles().get(selectedAnswerIndex);

                 // Highlight the student's selected answer
                 selectedRadioButton.setSelected(true);

                 if (selectedAnswerIndex != correctAnswerIndex) {  
                     // The student's selected answer is incorrect
                     pointsLabels.get(i).setText("Points: 0");
                     pointsLabels.get(i).setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 14px; -fx-text-fill: red;");
                     selectedRadioButton.setStyle("-fx-font-family: \"Comic Sans MS\"; -fx-font-size: 14px; -fx-text-fill: red;");
                     selectedRadioButton.setText(selectedRadioButton.getText() + " - Wrong answer");
                 }
            }
           
        }
    }
}