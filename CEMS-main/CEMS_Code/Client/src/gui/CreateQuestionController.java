package gui;

import java.util.ArrayList;

import client.ChatClient;
import communication.Msg;
import enteties.Course;
import enteties.Question;
import enteties.Subject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller class for the Create Question screen.
 *  
 * @author Yuval Rozner 
 */
public class CreateQuestionController extends AbstractController{
	/**
	 * ToggleGroup for selecting the correct answer.
	 */
    @FXML
    private ToggleGroup ChooseCorrectAnswerGroup;
	/**
	 * columns for the table of courses.
	 */
    @FXML
    private TableColumn<Course, String> courseCol, selectCol;
    /**
	 * comboBox to choose a subject.
	 */
    @FXML
    private ComboBox<String> subjectComboBox;
    /**
	 * table of courses connected to the chosen subject.
	 */
    @FXML
    private TableView<Course> coursesTable;
    /**
	 * the text content of the answers.
	 */
    @FXML
    private TextField answer1TextField , answer2TextField, answer3TextField, answer4TextField;
    /**
	 * instructions for answering the question.
	 */
    @FXML
    private TextField instructionTextField;
    /**
	 * the number of the question (100-999).
	 */
    @FXML
    private TextField questionNumberTextField;
    /**
	 * the question text content.
	 */
    @FXML
    private TextField questionTextField;
    /**
	 * the options for the correct answer.
	 */
    @FXML
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    /**
	 * the list of subject for the comboBox according to the subjects which math the user.
	 */
    private ArrayList<Subject> subjectsLst;
    /**
	 * the subject selected from the comboBox.
	 */
    private Subject selectedSubject;
    /**
	 * boolean variable to indicate that any subject has already been chose. for not letting the user create a question without choosing subject.
	 */
    private boolean subjectChoose = false; //indicates that a subject was chosen.
   
    /**
     * Default constructor for the CreateQuestionController class.
     * Initializes the subjectsLst.
     */
    public CreateQuestionController() {
    	Msg msg = subjectController.selectSubjectByUser(ChatClient.user);
    	sendMsg(msg);
    	if (msgReceived != null){ subjectsLst = msgReceived.convertData(Subject.class); } 
    	
    }
    
    /**
     * Initializes the controller after its root element has been completely processed.
     * Sets up the table columns and initializes the subjectComboBox.
     */
    @FXML
    protected void initialize() {
    	if(subjectsLst==null) {notification.showErrorAlert("There are no subjects"); return;}
        courseCol.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
        selectCol.setCellValueFactory(new PropertyValueFactory<Course, String>("checkbox"));
        subjectComboBox.setItems(subjectController.getSubjectNames(subjectsLst));
        subjectComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
        	@Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                	subjectChoose = true;
                    // Code to be executed when the selected item changes and newValue is not null
                    // Find the Subject object based on the new value
                    selectedSubject = subjectController.findSubjectByName(newValue, subjectsLst);
                    sendMsg(subjectController.getMsgForCourses(selectedSubject));
                    if (msgReceived == null) {return;}
                    selectedSubject.setCourses(msgReceived.convertData(Course.class)); 
                    // Get the list of courses associated with the selected subject
                    if (selectedSubject != null) {
                    	ArrayList<Course> courses = subjectController.returnCoursesWithCheckbox(selectedSubject);
                        // Set the items of a table with the list of courses
                    	coursesTable.setItems(FXCollections.observableArrayList(courses));
                    }
                }
            }
        });
    }
	
    /**
     * Saves the question when the "Save" button is clicked.
     * Validates the input fields and inserts the question into the database.
     *
     * @param event The ActionEvent triggered by clicking the "Save" button.
     */
    @FXML
    void save(ActionEvent event) {
    	if(!subjectChoose) { notification.showErrorAlert("you must choose a subject."); return; } //checks if chose subject.
    	//tries to create a Question Object:
    	Object newQuestion = questionController.checkInputs(selectedSubject.getNumber()+questionNumberTextField.getText(),
    			questionNumberTextField.getText(), questionTextField.getText(), selectedSubject.getNumber(),
    			ChatClient.user.getId(), answer1TextField.getText(), answer2TextField.getText(), answer3TextField.getText(),
    			answer4TextField.getText(),	Integer.valueOf(((RadioButton)ChooseCorrectAnswerGroup.getSelectedToggle()).getText()),
    			instructionTextField.getText());
    	// if there are issues in inputs:
    	if(newQuestion instanceof String) {	notification.showErrorAlert((String)newQuestion); return; }
    	//sets up the relevant courses in the question object by the selected courses in table:
    	ArrayList<Course> relevantCourses = new ArrayList<>();
    	for(Course c : coursesTable.getItems()) {
    		if(c.getCheckbox().isSelected()) relevantCourses.add(c);
    	}
    	((Question)newQuestion).setCourses(relevantCourses);
    	// insert the data into DB:
    	Msg msg = questionController.insertQuestion((Question)newQuestion); // create an insert query msg.
    	if(msg==null) {System.out.println("can't create this question.."); return;}
    	sendMsg(msg);
    	notification.showInformationAlert("Data inserted to the DB.");
    	resetFields();
    }
    
    /**
     * Resets all input fields to their default values.
     */
    private void resetFields() {
    	answer1TextField.setText("");
    	answer2TextField.setText("");
    	answer3TextField.setText("");
    	answer4TextField.setText("");
    	instructionTextField.setText("");
    	questionTextField.setText("");
    	questionNumberTextField.setText("");
    	radioButton1.setSelected(true);
    }
}