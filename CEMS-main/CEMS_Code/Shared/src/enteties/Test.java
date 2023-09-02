package enteties;

import javafx.scene.control.RadioButton;
/**
 * The Test class represents a test entity.
 * It contains various properties related to the test
 */
public class Test {
    //in DB:
    private String id; // 6 digits
    private String number; // 2 digits
    private String CourseNumber;
    private Integer duration; //minutes
    private String instructionsForStudent;
    private String instructionsForLecturer;
    //not in DB:
    private Course course;
    //for FX:
	private RadioButton radioButton;
    
	/**
	 * Constructs a Test object with the specified duration.
	 *
	 * @param duration The duration of the test in minutes.
	 */
	public Test(Integer duration) {this.duration=duration;}
	
	/**
	 * empty constructor.
	 */
	public Test() {super();}
    
	/**
	 * Constructs a Test object with the specified parameters.
	 *
	 * @param id                     The ID of the test.
	 * @param number                 The number of the test.
	 * @param courseNumber           The course number associated with the test.
	 * @param duration               The duration of the test in minutes.
	 * @param instructionsForStudent The instructions for students taking the test.
	 * @param instructionsForLecturer The instructions for the lecturer administering the test.
	 * @param courseNumber1          The course number for the associated course.
	 * @param courseName             The name of the associated course.
	 * @param subjectNum             The subject number of the associated course.
	 */	
	public Test(String id, String number, String courseNumber, Integer duration, String instructionsForStudent, String instructionsForLecturer,
			String courseNumber1, String courseName, String subjectNum) {
		this.id = id;
		this.number = number;
		CourseNumber = courseNumber;
		this.duration = duration;
		this.instructionsForStudent = instructionsForStudent;
		this.instructionsForLecturer = instructionsForLecturer;
		this.course = new Course(courseNumber1, courseName, subjectNum);
	}
	
	/**
	 * Constructs a Test object with the specified parameters.
	 *
	 * @param id                     The ID of the test.
	 * @param number                 The number of the test.
	 * @param courseNumber           The course number associated with the test.
	 * @param duration               The duration of the test in minutes.
	 * @param instructionsForStudent The instructions for students taking the test.
	 * @param instructionsForLecturer The instructions for the lecturer administering the test.
	 */
	public Test(String id, String number, String courseNumber, Integer duration, String instructionsForStudent, String instructionsForLecturer) {
		this.id = id;
		this.number = number;
		CourseNumber = courseNumber;
		this.duration = duration;
		this.instructionsForStudent = instructionsForStudent;
		this.instructionsForLecturer = instructionsForLecturer;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the courseNumber
	 */
	public String getCourseNumber() {
		return CourseNumber;
	}

	/**
	 * @param courseNumber the courseNumber to set
	 */
	public void setCourseNumber(String courseNumber) {
		CourseNumber = courseNumber;
	}

	/**
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	/**
	 * @return the instructionsForStudent
	 */
	public String getInstructionsForStudent() {
		return instructionsForStudent;
	}

	/**
	 * @param instructionsForStudent the instructionsForStudent to set
	 */
	public void setInstructionsForStudent(String instructionsForStudent) {
		this.instructionsForStudent = instructionsForStudent;
	}

	/**
	 * @return the instructionsForLecturer
	 */
	public String getInstructionsForLecturer() {
		return instructionsForLecturer;
	}

	/**
	 * @param instructionsForLecturer the instructionsForLecturer to set
	 */
	public void setInstructionsForLecturer(String instructionsForLecturer) {
		this.instructionsForLecturer = instructionsForLecturer;
	}
	
    /**
	 * @return the course
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(Course course) {
		this.course = course;
	}
	
	/**
	 * Compares this Test object to the specified object for equality.
	 *
	 * @param obj The object to compare.
	 * @return true if the specified object is equal to this Test object, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		Test t = (Test) obj;
		return id.equals(t.getId());
	}

	/**
	 * Returns a string representation of this Test object.
	 *
	 * @return A string representation of this Test object.
	 */
	@Override
	public String toString() {
		return "Test [id=" + id + ", number=" + number + ", CourseNumber=" + CourseNumber + ", duration=" + duration + ", instructionsForStudent="
				+ instructionsForStudent + ", instructionsForLecturer=" + instructionsForLecturer + "]";
	}

	/**
	 * Sets the style of the radio button associated with this Test object.
	 */
	public void setNewRadioButton() {
    	this.radioButton.setStyle("-fx-border-color: #CCFFFF; -fx-border-width: 2px; -fx-border-radius: 50%; -fx-background-color: #FFFFFF; -fx-box-shadow: 0 2px 4px rgba(0, 0, 0, 0.4);");
        this.radioButton.setOnMousePressed(e -> this.radioButton.setStyle("-fx-border-color: #CCFFFF; -fx-border-width: 2px; -fx-border-radius: 50%; -fx-background-color: #FFFFFF; -fx-box-shadow: 0 2px 4px rgba(0, 0, 0, 0.4);"));
        this.radioButton.setOnMouseReleased(e -> this.radioButton.setStyle("-fx-border-color: #CCFFFF; -fx-border-width: 2px; -fx-border-radius: 50%; -fx-background-color: #FFFFFF; -fx-box-shadow: 0 2px 4px rgba(0, 0, 0, 0.4);"));
    }

	/**
	 * Returns the radio button associated with this Test object.
	 *
	 * @return The radio button associated with this Test object.
	 */
	public RadioButton getRadioButton() {
		return this.radioButton;
	}
}