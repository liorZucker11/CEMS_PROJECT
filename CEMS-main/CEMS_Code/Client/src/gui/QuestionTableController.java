package gui;

import java.util.ArrayList;

import client.ChatClient;
import communication.Msg;
import controllers.UserController;
import enteties.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * Controller class for the Question Table screen.
 * Handles the interaction between the UI and the underlying logic for displaying questions.
 * 
 * @author Mor Shmuel
 */
public class QuestionTableController extends AbstractController {
	/**
     * The list of questions displayed in the table.
     */
    private ArrayList<Question> questionList = new ArrayList<>();

    /**
     * The observable list used to populate the question table.
     */
    private ObservableList<Question> QTable;
    
    /**
	 * object to use the UserController class method.
	 */
    private static UserController userController = new UserController();

    /**
     * The table view that displays the questions.
     */
    @FXML
    private TableView<Question> table = new TableView<Question>();
    
    /**
     * the columns for the table.
     */
    @FXML
    private TableColumn<Question, String> idCol, questionTextCol, lecturerCol, courseCol;

    /**
     * the columns for the table.
     */
    @FXML
    private TableColumn<Question, Integer> questionNumberCol;

    /**
     * the Question test wanted to be shown.
     */
    public Question questionToShow;

    /**
     * to show the answer.
     */
    @FXML
    private RadioButton answer1RadioButton, answer2RadioButton, answer3RadioButton, answer4RadioButton;
    /**
     * to show the answer.
     */
    @FXML
    private ToggleGroup answersToggleGroup;
    /**
     * to show the answer.
     */
    @FXML
    private Label questionLabel;

    /**
     * Default constructor for the CreateTestController class.
     * initialize the questionList.
     */
    public QuestionTableController() {
        Msg msg = userController.selectQuestionByhodId(ChatClient.user.getId());
        sendMsg(msg);
        if(msgReceived!=null) 
        	questionList = msgReceived.convertData(Question.class);
        QTable = FXCollections.observableArrayList(questionList);
    }

    /**
     * Event handler for displaying the answers of the selected question.
     *
     * @param event The MouseEvent triggering the event.
     */
    @FXML
    void showAnswers(MouseEvent event) {
        //Get the selected question from the table.
        Question selectedQuestion = table.getSelectionModel().getSelectedItem();
        //Display the question and answers in the UI.
        questionLabel.setText(selectedQuestion.getQuestion());
        answer1RadioButton.setText(selectedQuestion.getAnswers()[0]);
        answer2RadioButton.setText(selectedQuestion.getAnswers()[1]);
        answer3RadioButton.setText(selectedQuestion.getAnswers()[2]);
        answer4RadioButton.setText(selectedQuestion.getAnswers()[3]);
        //Select the correct answer toggle.
        ObservableList<Toggle> toggles = answersToggleGroup.getToggles();
        Toggle toggle = toggles.get(selectedQuestion.getCorrectAnswer()-1); // Index 2 represents the third toggle this is why we minus the answer with 1
        answersToggleGroup.selectToggle(toggle);
    }

    /**
     * Initializes the Question Table screen.
     * Configures the table columns and sets the table items.
     */
    @FXML
    protected void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<Question, String>("id"));
        courseCol.setCellValueFactory(new PropertyValueFactory<Question, String>("courseName"));
        questionNumberCol.setCellValueFactory(new PropertyValueFactory<Question, Integer>("number"));
        lecturerCol.setCellValueFactory(new PropertyValueFactory<Question, String>("userName"));
        questionTextCol.setCellValueFactory(new PropertyValueFactory<Question, String>("question"));

        table.setItems(QTable);
        table.refresh();
    }
    
    @FXML
    void onMouseEnterdBackBtn(MouseEvent event) {

    }

    @FXML
    void onMouseExitedBackBtn(MouseEvent event) {

    }

}