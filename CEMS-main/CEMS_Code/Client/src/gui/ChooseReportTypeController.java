package gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import client.ChatClient;
import communication.Msg;
import enteties.Course;
import enteties.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

/**
 * Controller class for the Choose Report Type screen.
 * Allows the user to select a report type and choose a specific lecturer, student, or course for generating a report.
 * The selected report type and chosen user or course are passed to the next screen.
 * 
 * Author: Mor Shmuel
 */
public class ChooseReportTypeController extends AbstractController{

	/**
	 * back and shoe buttons.
	 */
    @FXML
    private Button back, show;
    
    /**
	 * ToggleGroup for selecting the report type.
	 */
    @FXML
    private ToggleGroup chooseTypeOfReport;

    /**
	 * comboBox to choose a report by name of chosen type.
	 */
    @FXML
    private ComboBox<String> comboBoxNames;
    
    /**
	 * the options for the radio buttons(course,lecturer,student).
	 */
    @FXML
    private RadioButton courseRadiobtn, lecturerRadiobtn, studentradiobtn;
    
    /**
     * The observable list of users changes for the table.
     */
    @SuppressWarnings("unused")
	private ObservableList<User> studentTable, lecturerTable;
    
    /**
     * The observable list of course changes for the table.
     */
    @SuppressWarnings("unused")
	private ObservableList<Course> courseTable;
    
    /**
	 * the list of users for the comboBox according to the subjects which math the hod user.
	 */
    private ArrayList<User> userLecturerLst, userStudentLst;
    
    /**
	 * the list of users for the comboBox according to the subjects which match the hod user.
	 */
    private ArrayList<Course> courseLst;
    
    /**
	 * the user selected from the comboBox.
	 */
    private User selectedLecturer, selectedStudent;
    
    /**
	 * the course selected from the comboBox.
	 */
    private Course selectedCourse;
    
    /**
	 * string that represent the chosen radio button.
	 */
    private String reportType;
    
    /**
     * Default constructor for the ChooseReportTypeController class.
     * Initializes the data for comboBox that fits to each radio button(lecturer,student and course).
     */
    public ChooseReportTypeController() {
    	//msgLecturer ---> users of lecturers belongs to hod subject
    	Msg msgLecturer = userController.selectUserByHodAndLecturer(ChatClient.user.getId());
    	sendMsg(msgLecturer);
    	if (msgReceived != null){ 
    		userLecturerLst = msgReceived.convertData(User.class);
    		lecturerTable = FXCollections.observableArrayList(userLecturerLst);}
    	
    	
    	
    	//msgStudent ---> users of students belongs to hod subject
    	Msg msgStudent = userController.selectUserByHodAndStudent(ChatClient.user.getId());
    	sendMsg(msgStudent);
    	if (msgReceived != null){ 
    		userStudentLst = msgReceived.convertData(User.class);
    		studentTable = FXCollections.observableArrayList(userStudentLst);}
    	
    	
    	//msgCourse ---> courses belongs to hod subject
    	Msg msgCourse = userController.selectUserByHodAndCourse(ChatClient.user.getId());
    	sendMsg(msgCourse);
    	if (msgReceived != null){ 
    		courseLst = msgReceived.convertData(Course.class);
    		courseTable = FXCollections.observableArrayList(courseLst);}	
    }
    
    /**
     * Initializes the controller after its root element has been completely processed.
     * This method is called when loading the FXML file.
     */
    @FXML
	protected void initialize() {
    	//Disable the ComboBox and Show button initially
        comboBoxNames.setDisable(true);
        show.setDisable(true);
    }
    
    /**
     * Event handler for the course radio button.
     * Populates the combo box with course names and sets the selected course.
     * 
     * @param event The action event.
     */
    @FXML
    void onCourseSelected(ActionEvent event) {
    	//Enable the ComboBox
        comboBoxNames.setDisable(false);
        
    	ObservableList<String> courseNames = FXCollections.observableArrayList();
        for (Course course : courseLst) {
            courseNames.add(course.getName());
        }
        comboBoxNames.setItems(courseNames);
        
        comboBoxNames.setOnAction(e -> {
            selectedCourse = getCourseSelected();
            if (selectedCourse != null) {
            	reportType = "course";
            	//Enable the Show button when an item is selected in the ComboBox
                show.setDisable(false);
            }
            else {
            	show.setDisable(true);  	
            }
        });
    }

    /**
     * Event handler for the lecturer radio button.
     * Populates the combo box with lecturer names and sets the selected lecturer.
     * 
     * @param event The action event.
     */
    @FXML
    void onLecturerSelected(ActionEvent event) {
    	//Enable the ComboBox
        comboBoxNames.setDisable(false);
        
    	Set<String> lecturerNames = new HashSet<>();
        for (User lecturer : userLecturerLst) {
            lecturerNames.add(lecturer.getName());
        }
        ObservableList<String> uniqueLecturerNames = FXCollections.observableArrayList(lecturerNames);
        comboBoxNames.setItems(uniqueLecturerNames);
        
        comboBoxNames.setOnAction(e -> {
            selectedLecturer = getLecturerSelected();
            if (selectedLecturer != null) {
            	reportType = "lecturer";
            	//Enable the Show button when an item is selected in the ComboBox
                show.setDisable(false);
            }
            else {
            	show.setDisable(true);           	
            }
        });
    }

    /**
     * Event handler for the student radio button.
     * Populates the combo box with student names and sets the selected student.
     * 
     * @param event The action event.
     */
    @FXML
    void onStudentSelected(ActionEvent event) {
    	//Enable the ComboBox
        comboBoxNames.setDisable(false);
        
    	Set<String> studentNames = new HashSet<>();
        for (User student : userStudentLst) {
            studentNames.add(student.getName());
        }
        ObservableList<String> uniqueStudentNames = FXCollections.observableArrayList(studentNames);
        comboBoxNames.setItems(uniqueStudentNames);
 
        comboBoxNames.setOnAction(e -> {
            selectedStudent = getStudentSelected();
            if (selectedStudent != null) {
            	reportType = "student";
            	//Enable the Show button when an item is selected in the ComboBox
                show.setDisable(false);
            }
            else {
            	show.setDisable(true);  	
            }
        });
    }
    
    /**
     * Retrieves the selected course based on the combo box selection.
     * 
     * @return The selected course, or null if no course is selected.
     */
    private Course getCourseSelected() {
        String selectedCourseName = comboBoxNames.getSelectionModel().getSelectedItem();
        for (Course course : courseLst) {
            if (course.getName().equals(selectedCourseName)) {
                return course;
            }
        }
        return null;
    }
    
    /**
     * Retrieves the selected student based on the combo box selection.
     * 
     * @return The selected student, or null if no student is selected.
     */
    private User getStudentSelected() {
        String selectedUserName = comboBoxNames.getSelectionModel().getSelectedItem();
        for (User user : userStudentLst) {
            if (user.getName().equals(selectedUserName)) {
                return user;
            }
        }
        return null;
    }
    
    /**
     * Retrieves the selected lecturer based on the combo box selection.
     * 
     * @return The selected lecturer, or null if no lecturer is selected.
     */
    private User getLecturerSelected() {
        String selectedUserName = comboBoxNames.getSelectionModel().getSelectedItem();
        for (User user : userLecturerLst) {
            if (user.getName().equals(selectedUserName)) {
                return user;
            }
        }
        return null;
    }
    

    /**
	 * @return the reportType
	 */
	public String getReportType() {
		return reportType;
	}

	/**
	 * @return the selectedLecturer
	 */
	public User getSelectedLecturer() {
		return selectedLecturer;
	}

	/**
	 * @return the selectedStudent
	 */
	public User getSelectedStudent() {
		return selectedStudent;
	}

	/**
	 * @return the selectedCourse
	 */
	public Course getSelectedCourse() {
		return selectedCourse;
	}

	/**
     * Event handler for the "Show" button.
     * Sets the selected user or course based on the chosen report type and navigates to the "lecturerStaticsReport" screen.
     * 
     * @param event The action event.
     * @throws Exception if an error occurs during navigation to the next screen.
     */
	@FXML
    void showReport(ActionEvent event) throws Exception {
        if (reportType == "lecturer") {
        	LecturerStaticsReportController.selectedLecturer = selectedLecturer;
        }else if(reportType == "student") {
        	LecturerStaticsReportController.selectedStudent = selectedStudent;
        }else if(reportType == "course") {
        	LecturerStaticsReportController.selectedCourse = selectedCourse;
        }
    	start("LecturerStaticsReport", "ChooseReportType");
    }
}