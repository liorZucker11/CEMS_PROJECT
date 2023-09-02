package enteties;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

/**
 * The TestToExecute class represents a test to be executed.
 * It contains various properties related to the test, such as test code, test
 * ID, testing type, date, average, median, lock, time extension, and more.
 * The class also provides methods to get and set these properties.
 * Additionally, it includes methods for creating JavaFX UI components like
 * CheckBox, RadioButton, Button, ComboBox, and TextField.
 */
public class TestToExecute {
	// in DB:
	private Integer testCode;
	private String testId;
	private String testingType;
	private String date;
	private Double average;
	private Double median;
	private String lock;
	private Integer timeExtension;
	private String lecturerId;
	private Integer numberOfStudentsStarted;
	private Integer numberOfStudentsFinished;
	private Integer numberOfStudents;
	private Integer[] distribusion = new Integer[10]; // 10 separate columns.

	// not in DB:
	private Test test;
	private Course course = null;
	@SuppressWarnings("unused") // sometimes in use.
	private ArrayList<StudentTest> studentsTestsLst;
	// for FX:
	CheckBox checkbox;
	RadioButton radioButton;
	Button button;
	@SuppressWarnings("rawtypes") // works without specific type.
	ComboBox comboBox;
	TextField textField;
	TextField textField1;

	private CheckBox select;
	private TextField codeField;
	private TextField type;
	private Button show;
	private TextField durationField;

	/**
	 * Constructs a TestToExecute object with the specified number of students.
	 *
	 * @param numberOfStudentsStarted  the number of students who started the test
	 * @param numberOfStudentsFinished the number of students who finished the test
	 * @param numberOfStudents         the total number of students
	 */
	public TestToExecute(Integer numberOfStudentsStarted, Integer numberOfStudentsFinished, Integer numberOfStudents) {
		this.numberOfStudentsStarted = numberOfStudentsStarted;
		this.numberOfStudentsFinished = numberOfStudentsFinished;
		this.numberOfStudents = numberOfStudents;
	}

	/*
	 * for id of lecturer
	 */
	public TestToExecute(String lecturerId, Integer testCode) {
		this.lecturerId = lecturerId;
	}

	/**
	 * for getting the lock.
	 */
	public TestToExecute(String lock) {
		this.lock = lock;
	}

	/**
	 * empty constructor.
	 */
	public TestToExecute() {
		super();
	}

	/**
	 * Constructs a TestToExecute object with the specified parameters.
	 *
	 * @param testCode                 the test code
	 * @param testId                   the test ID
	 * @param testingType              the testing type
	 * @param date                     the date of the test
	 * @param average                  the average score
	 * @param median                   the median score
	 * @param lock                     the lock status
	 * @param timeExtension            the time extension
	 * @param lecturerId               the ID of the lecturer
	 * @param numberOfStudentsStarted  the number of students who started the test
	 * @param numberOfStudentsFinished the number of students who finished the test
	 * @param numberOfStudents         the total number of students
	 * @param dis1                     the distribution value 1
	 * @param dis2                     the distribution value 2
	 * @param dis3                     the distribution value 3
	 * @param dis4                     the distribution value 4
	 * @param dis5                     the distribution value 5
	 * @param dis6                     the distribution value 6
	 * @param dis7                     the distribution value 7
	 * @param dis8                     the distribution value 8
	 * @param dis9                     the distribution value 9
	 * @param dis10                    the distribution value 10
	 * @param id                       the ID
	 * @param number                   the number
	 * @param courseNumber             the course number
	 * @param duration                 the duration
	 * @param instructionsForStudent   the instructions for students
	 * @param instructionsForLecturer  the instructions for the lecturer
	 * @param course_number            the course number
	 * @param name                     the name
	 * @param subjectNum               the subject number
	 */
	public TestToExecute(Integer testCode, String testId, String testingType, String date, Double average,
			Double median, String lock, Integer timeExtension,
			String lecturerId, Integer numberOfStudentsStarted, Integer numberOfStudentsFinished,
			Integer numberOfStudents, Integer dis1, Integer dis2, Integer dis3, Integer dis4, Integer dis5,
			Integer dis6, Integer dis7, Integer dis8, Integer dis9, Integer dis10,
			String id, String number, String courseNumber, Integer duration, String instructionsForStudent,
			String instructionsForLecturer,
			String course_number, String name, String subjectNum) {
		this.testCode = testCode;
		this.testId = testId;
		this.testingType = testingType;
		this.date = date;
		this.average = average;
		this.median = median;
		this.lock = lock;
		this.timeExtension = timeExtension;
		this.lecturerId = lecturerId;
		this.numberOfStudentsStarted = numberOfStudentsStarted;
		this.numberOfStudentsFinished = numberOfStudentsFinished;
		this.numberOfStudents = numberOfStudents;
		this.distribusion = new Integer[] { dis1, dis2, dis3, dis4, dis5, dis6, dis7, dis8, dis9, dis10 };
		this.test = new Test(id, number, courseNumber, duration, instructionsForStudent, instructionsForLecturer);
		this.test.setCourse(new Course(course_number, name, subjectNum));
	}

	/**
	 * Constructs a new TestToExecute object with the given parameters.
	 *
	 * @param testCode                 the test code
	 * @param testId                   the test ID
	 * @param testingType              the testing type
	 * @param date                     the date
	 * @param average                  the average
	 * @param median                   the median
	 * @param lock                     the lock status
	 * @param timeExtension            the time extension
	 * @param lecturerId               the lecturer ID
	 * @param numberOfStudentsStarted  the number of students who started the test
	 * @param numberOfStudentsFinished the number of students who finished the test
	 * @param numberOfStudents         the total number of students
	 * @param dis1             the distribution array
	 * @param dis2
	 * @param dis3
	 * @param dis4
	 * @param dis5
	 * @param dis6
	 * @param dis7
	 * @param dis8
	 * @param dis9
	 * @param dis10
	 */
	public TestToExecute(Integer testCode, String testId, String testingType, String date, Double average,
			Double median, String lock, Integer timeExtension,
			String lecturerId, Integer numberOfStudentsStarted, Integer numberOfStudentsFinished,
			Integer numberOfStudents, Integer dis1, Integer dis2, Integer dis3, Integer dis4, Integer dis5,
			Integer dis6, Integer dis7, Integer dis8, Integer dis9, Integer dis10) {
		this.testCode = testCode;
		this.testId = testId;
		this.testingType = testingType;
		this.date = date;
		this.average = average;
		this.median = median;
		this.lock = lock;
		this.timeExtension = timeExtension;
		this.lecturerId = lecturerId;
		this.numberOfStudentsStarted = numberOfStudentsStarted;
		this.numberOfStudentsFinished = numberOfStudentsFinished;
		this.numberOfStudents = numberOfStudents;
		this.distribusion = new Integer[] { dis1, dis2, dis3, dis4, dis5, dis6, dis7, dis8, dis9, dis10 };
	}

	/**
	 * @param testCode
	 * @param testId
	 * @param testingType
	 * @param date
	 * @param average
	 * @param median
	 * @param lock
	 * @param timeExtension
	 * @param lecturerId
	 * @param numberOfStudentsStarted
	 * @param numberOfStudentsFinished
	 * @param distribusion
	 */
	public TestToExecute(Integer testCode, String testId, String testingType, String date, Double average,
			Double median, String lock, Integer timeExtension,
			String lecturerId, Integer numberOfStudentsStarted, Integer numberOfStudentsFinished,
			Integer[] distribusion) {
		this.testCode = testCode;
		this.testId = testId;
		this.testingType = testingType;
		this.date = date;
		this.average = average;
		this.median = median;
		this.lock = lock;
		this.timeExtension = timeExtension;
		this.lecturerId = lecturerId;
		this.numberOfStudentsStarted = numberOfStudentsStarted;
		this.numberOfStudentsFinished = numberOfStudentsFinished;
		this.distribusion = distribusion;
	}

	/**
	 * Constructs a new TestToExecute object with the given parameters.
	 *
	 * @param testCode                 the test code
	 * @param testId                   the test ID
	 * @param testingType              the testing type
	 * @param date                     the date
	 * @param average                  the average
	 * @param median                   the median
	 * @param lock                     the lock status
	 * @param timeExtension            the time extension
	 * @param lecturerId               the time lecturer ID
	 * @param numberOfStudentsStarted  the number Of Students Started a test
	 * @param numberOfStudentsFinished the number Of Students Finished a test
	 * @param numberOfStudents         the number Of Students who not finished on
	 *                                 time
	 * @param distribusion             the distribution
	 */
	public TestToExecute(Integer testCode, String testId, String testingType, String date, Double average,
			Double median, String lock, Integer timeExtension,
			String lecturerId, Integer numberOfStudentsStarted, Integer numberOfStudentsFinished,
			Integer numberOfStudents, Integer[] distribusion) {
		this.testCode = testCode;
		this.testId = testId;
		this.testingType = testingType;
		this.date = date;
		this.average = average;
		this.median = median;
		this.lock = lock;
		this.timeExtension = timeExtension;
		this.lecturerId = lecturerId;
		this.numberOfStudentsStarted = numberOfStudentsStarted;
		this.numberOfStudentsFinished = numberOfStudentsFinished;
		this.numberOfStudents = numberOfStudents;
		this.distribusion = distribusion;
	}

	/**
	 * Returns the test code.
	 *
	 * @return the test code
	 */
	public Integer getTestCode() {
		return testCode;
	}

	/**
	 * Sets the test code.
	 *
	 * @param testCode the test code to set
	 */
	public void setTestCode(Integer testCode) {
		this.testCode = testCode;
	}

	/**
	 * Returns the test ID.
	 *
	 * @return the test ID
	 */
	public String getTestId() {
		return testId;
	}

	/**
	 * Sets the test ID.
	 *
	 * @param testId the test ID to set
	 */
	public void setTestId(String testId) {
		this.testId = testId;
	}

	/**
	 * Returns the testing type.
	 *
	 * @return the testing type
	 */
	public String getTestingType() {
		return testingType;
	}

	/**
	 * Sets the testing type.
	 *
	 * @param testingType the testing type to set
	 */
	public void setTestingType(String testingType) {
		this.testingType = testingType;
	}

	/**
	 * Returns the date.
	 *
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Returns the average.
	 *
	 * @return the average
	 */
	public Double getAverage() {
		return average;
	}

	/**
	 * Sets the average.
	 *
	 * @param average the average to set
	 */
	public void setAverage(Double average) {
		this.average = average;
	}

	/**
	 * Returns the median.
	 *
	 * @return the median
	 */
	public Double getMedian() {
		return median;
	}

	/**
	 * Sets the median.
	 *
	 * @param median the median to set
	 */
	public void setMedian(Double median) {
		this.median = median;
	}

	/**
	 * Returns the lock status.
	 *
	 * @return the lock status
	 */
	public String getLock() {
		return lock;
	}

	public String getFinished() {
		if (lock.equals("false"))
			return "running";
		if (lock.equals("true"))
			return "finished";
		return null;
	}

	/**
	 * Sets the lock status.
	 *
	 * @param lock the lock status to set
	 */
	public void setLock(String lock) {
		this.lock = lock;
	}

	/**
	 * Returns the time extension.
	 *
	 * @return the time extension
	 */
	public Integer getTimeExtension() {
		return timeExtension;
	}

	/**
	 * Sets the time extension.
	 *
	 * @param timeExtension the time extension to set
	 */
	public void setTimeExtension(Integer timeExtension) {
		this.timeExtension = timeExtension;
	}

	/**
	 * Returns the lecturer ID.
	 *
	 * @return the lecturer ID
	 */
	public String getLecturerId() {
		return lecturerId;
	}

	/**
	 * Sets the lecturer ID.
	 *
	 * @param lecturerId the lecturer ID to set
	 */
	public void setLecturerId(String lecturerId) {
		this.lecturerId = lecturerId;
	}

	/**
	 * Returns the number of students who started the test.
	 *
	 * @return the number of students who started the test
	 */
	public Integer getNumberOfStudentsStarted() {
		return numberOfStudentsStarted;
	}

	/**
	 * Sets the number of students who started the test.
	 *
	 * @param numberOfStudentsStarted the number of students who started the test to
	 *                                set
	 */
	public void setNumberOfStudentsStarted(Integer numberOfStudentsStarted) {
		this.numberOfStudentsStarted = numberOfStudentsStarted;
	}

	/**
	 * Returns the number of students who finished the test.
	 *
	 * @return the number of students who finished the test
	 */
	public Integer getNumberOfStudentsFinished() {
		return numberOfStudentsFinished;
	}

	/**
	 * Sets the number of students who finished the test.
	 *
	 * @param numberOfStudentsFinished the number of students who finished the test
	 *                                 to set
	 */
	public void setNumberOfStudentsFinished(Integer numberOfStudentsFinished) {
		this.numberOfStudentsFinished = numberOfStudentsFinished;
	}

	/**
	 * Returns the distribution array.
	 *
	 * @return Integer[] the distribution array
	 */
	public Integer[] getDistribusion() {
		return distribusion;
	}

	/**
	 * Sets the distribution array.
	 *
	 * @param distribusion the distribution array to set
	 */
	public void setDistribusion(Integer[] distribusion) {
		this.distribusion = distribusion;
	}

	/**
	 * Get the course.
	 *
	 * @return the course
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * Set the course.
	 *
	 * @param course the course to set
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

	/**
	 * Get the test.
	 *
	 * @return the test
	 */
	public Test getTest() {
		return test;
	}

	/**
	 * Set the test.
	 *
	 * @param test the test to set
	 */
	public void setTest(Test test) {
		this.test = test;
	}

	/**
	 * Check if the current object is equal to the given object.
	 *
	 * @param obj the object to compare
	 * @return true if the objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		TestToExecute t = (TestToExecute) obj;
		try {
			return testCode.equals(t.getTestCode());
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Get the string representation of the object.
	 *
	 * @return the string representation
	 */
	@Override
	public String toString() {
		return "TestToExecute [testCode=" + testCode + ", testId=" + testId + ", testingType=" + testingType + ", date="
				+ date + ", average=" + average + ", median="
				+ median + ", lock=" + lock + ", timeExtension=" + timeExtension + ", lecturerId=" + lecturerId
				+ ", numberOfStudentsStarted=" + numberOfStudentsStarted
				+ ", numberOfStudentsFinished=" + numberOfStudentsFinished + ", numberOfStudents=" + numberOfStudents
				+ ", distribusion=" + Arrays.toString(distribusion) + "]";
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// for FX:

	/**
	 * Set a new CheckBox.
	 */
	public void setNewCheckbox() {
		checkbox = new CheckBox();
	}

	/**
	 * Get the CheckBox.
	 *
	 * @return the CheckBox
	 */
	public CheckBox getCheckbox() {
		return checkbox;
	}

	/**
	 * Set a new RadioButton.
	 */
	public void setNewRadioButton() {
		radioButton = new RadioButton();
		radioButton.setStyle(
				"-fx-border-color: #009494;" +
                "-fx-border-width: 2px;" +
                "-fx-border-radius: 50%;" +
                "-fx-background-color: #FFFFFF;" +
                "-fx-background-radius: 40%;"
		);
		DropShadow hoverEffect = new DropShadow(2, Color.rgb(0, 0, 0, 0.6));
	    radioButton.setOnMouseEntered(event -> radioButton.setEffect(hoverEffect));
	    radioButton.setOnMouseExited(event -> radioButton.setEffect(null));
	}

	/**
	 * Get the RadioButton.
	 *
	 * @return the RadioButton
	 */
	public RadioButton getRadioButton() {
		return radioButton;
	}

	/**
	 * Set a new Button.
	 */
	public void setNewButton() {
		button = new Button();
		button.setStyle("-fx-background-color: #CCFFFF; -fx-background-radius: 30 0 0 30; -fx-font-weight: bold; -fx-font-family: \"Comic Sans MS\";");
        // Add hover effect
		button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #009494; -fx-background-radius: 30 0 0 30; -fx-font-weight: bold; -fx-font-family: \"Comic Sans MS\";"));
		button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #CCFFFF; -fx-background-radius: 30 0 0 30; -fx-font-weight: bold; -fx-font-family: \"Comic Sans MS\";"));
        // Add pressed effect
		button.setOnMousePressed(e -> button.setStyle("-fx-background-color: #82bfb6; -fx-background-radius: 30 0 0 30; -fx-font-weight: bold; -fx-font-family: \"Comic Sans MS\";"));
		button.setOnMouseReleased(e -> button.setStyle("-fx-background-color: #CCFFFF; -fx-background-radius: 30 0 0 30; -fx-font-weight: bold; -fx-font-family: \"Comic Sans MS\";"));
	}

	/**
	 * Get the Button.
	 *
	 * @return the Button
	 */
	public Button getButton() {
		return button;
	}

	/**
	 * Set the text of the Button.
	 *
	 * @param s the text to set
	 */
	public void setButtonText(String s) {
		button.setText(s);
	}

	/**
	 * Set a new ComboBox.
	 */
	@SuppressWarnings("rawtypes")
	public void setNewComboBox() {
		comboBox = new ComboBox();
		comboBox.setStyle(
		        "-fx-border-width: 1px; " +
		        "-fx-border-color: #92bce3; " +
		        "-fx-border-radius: 20px; " +
		        "-fx-background-radius: 20px; " +
		        "-fx-padding: 4px; " +
		        "-fx-font-size: 13px; " +
		        "-fx-font-weight: bold; " +
		        "-fx-font-family: 'Comic Sans MS';"
		    );
	}

	/**
	 * Get the ComboBox.
	 *
	 * @return the ComboBox
	 */
	@SuppressWarnings("rawtypes")
	public ComboBox getComboBox() {
		return comboBox;
	}

	/**
	 * Set a new TextField.
	 */
	public void setNewTextField() {
		textField = new TextField();
		textField.setStyle(
		        "-fx-border-color:  #92bce3;" +
		        "-fx-border-radius: 10px;" +
                "-fx-background-color:  #F8FFFF;" +
                "-fx-background-radius: 10px;"
		    );
	}

	/**
	 * Get the TextField.
	 *
	 * @return the TextField
	 */
	public TextField getTextField() {
		return textField;
	}

	/**
	 * Set a new TextField1.
	 */
	public void setNewTextField1() {
		textField1 = new TextField();
		textField1.setStyle(
		        "-fx-border-color:  #92bce3;" +
		        "-fx-border-radius: 10px;" +
                "-fx-background-color:  #F8FFFF;" +
                "-fx-background-radius: 10px;"
		    );
	}

	/**
	 * Get the TextField1.
	 *
	 * @return the TextField1
	 */
	public TextField getTextField1() {
		return textField1;
	}

	/**
	 * Get the course name.
	 *
	 * @return the course name
	 */
	public String getCourseName() {
		if (test == null)
			return null;
		if (test.getCourse() == null)
			return null;
		return test.getCourse().getName();
	}

	/**
	 * Set a new code field.
	 */
	public void setNewCodeField() {
		this.codeField = new TextField();
		this.codeField.setStyle(
				"-fx-background-color: #F0F8FF; -fx-border-width: 1px; -fx-border-color: #92bce3;  -fx-border-radius: 7px; -fx-font-weight: bold; -fx-font-family: \"Comic Sans MS\";");
		codeField.setDisable(true);

	}

	/**
	 * Set a new duration field.
	 */
	public void setNewDurationField() {
		this.durationField = new TextField();
		this.durationField.setStyle(
				"-fx-background-color: #F0F8FF; -fx-border-width: 1px; -fx-border-color: #92bce3;  -fx-border-radius: 7px; -fx-font-weight: bold; -fx-font-family: \"Comic Sans MS\";");
		durationField.setDisable(true);
		durationField.setText(test.getDuration().toString());
	}

	/**
	 * Sets up a new CheckBox for selection.
	 * The CheckBox is styled with background color, border, and font.
	 * Adds an event handler for the pressed and unpressed (checked/unchecked) state
	 * of the checkbox.
	 * Disables/enables related fields based on the checkbox state.
	 */
	public void setNewSelect() {
		this.select = new CheckBox();
		this.select.setStyle(
				"-fx-background-color: #F0F8FF; -fx-border-width: 1px; -fx-border-color: #92bce3;  -fx-border-radius: 3px; -fx-font-weight: bold; -fx-font-family: \"Comic Sans MS\";");
		// Add event handler for pressed and unpressed (checked / unchecked) state of
		// the checkbox.
		this.select.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				codeField.setDisable(!newValue);
				type.setDisable(!newValue);
				durationField.setDisable(!newValue);
				System.out.println("Checkbox " + (newValue ? "pressed" : "unpressed"));
			}
		});
	}

	/**
	 * Sets up a new Button for showing.
	 * The Button is styled with background color, font, hover effect, and pressed
	 * effect.
	 */
	public void setNewShow() {
		this.show = new Button();
		show.setText("Show");
		show.setStyle(
				"-fx-background-color: #CCFFFF; -fx-background-radius: 30 0 0 30; -fx-font-weight: bold; -fx-font-family: \"Comic Sans MS\";");
		// Add hover effect
		show.setOnMouseEntered(e -> show.setStyle(
				"-fx-background-color: #009494; -fx-background-radius: 30 0 0 30; -fx-font-weight: bold; -fx-font-family: \"Comic Sans MS\";"));
		show.setOnMouseExited(e -> show.setStyle(
				"-fx-background-color: #CCFFFF; -fx-background-radius: 30 0 0 30; -fx-font-weight: bold; -fx-font-family: \"Comic Sans MS\";"));
		// Add pressed effect
		show.setOnMousePressed(e -> show.setStyle(
				"-fx-background-color: #82bfb6; -fx-background-radius: 30 0 0 30; -fx-font-weight: bold; -fx-font-family: \"Comic Sans MS\";"));
		show.setOnMouseReleased(e -> show.setStyle(
				"-fx-background-color: #CCFFFF; -fx-background-radius: 30 0 0 30; -fx-font-weight: bold; -fx-font-family: \"Comic Sans MS\";"));
	}

	/**
	 * Sets up a new TextField for type input.
	 * The TextField is styled with background color, border, border radius, and
	 * font.
	 * Disables the TextField by default.
	 */
	public void setNewType() {
		this.type = new TextField();
		this.type.setStyle(
				"-fx-background-color: #F0F8FF; -fx-border-width: 1px; -fx-border-color: #92bce3;  -fx-border-radius: 7px; -fx-font-weight: bold; -fx-font-family: \"Comic Sans MS\";");
		type.setDisable(true);
	}

	/**
	 * Returns the total number of students.
	 *
	 * @return the total number of students
	 */
	public Integer getNumberOfStudents() {
		return numberOfStudents;
	}

	/**
	 * Sets the total number of students.
	 *
	 * @param numberOfStudents the total number of students to set
	 */
	public void setNumberOfStudents(Integer numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}
}