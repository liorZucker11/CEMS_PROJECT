package enteties;

import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;

/**
 * The Request class represents a Request entity.
 */
public class Request {
	// in DB:
	private Integer testCode;
	private String lecturerId;
	private String hodId;
	private Integer duration;
	private String explanation;
	// not in DB:
	@SuppressWarnings("unused") // sometimes in use.
	private User user;
	private Course course;
	@SuppressWarnings("unused") // sometimes in use.
	private TestToExecute testToExecute;
	@SuppressWarnings("unused") // sometimes in use.
	private Test test;

	// for FX:
	CheckBox checkbox;
	RadioButton radioButton;

	/**
	 * empty constructor.
	 */
	public Request() {
		super();
	}

	 /**
     * Constructs a Request object with the specified parameters.
     *
     * @param testCode     The test code.
     * @param lecurerId    The lecturer ID.
     * @param hodId        The HOD ID.
     * @param duration     The duration.
     * @param explanation  The explanation.
     */
	public Request(Integer testCode, String lecurerId, String hodId, Integer duration, String explanation) {
		this.testCode = testCode;
		this.lecturerId = lecurerId;
		this.hodId = hodId;
		this.duration = duration;
		this.explanation = explanation;
	}

	/**
     * Constructs a Request object with the specified parameters.
     *
     * @param testCode                The test code.
     * @param lecurerId               The lecturer ID.
     * @param hodId                   The HOD ID.
     * @param duration                The duration.
     * @param explanation             The explanation.
     * @param id                      The ID.
     * @param number                  The number.
     * @param courseNumber            The course number.
     * @param testDuration            The test duration.
     * @param instructionsForStudent  The instructions for students.
     * @param instructionsForLecturer The instructions for lecturers.
     * @param userId                  The user ID.
     * @param name                    The name.
     * @param username                The username.
     * @param password                The password.
     * @param premission              The permission.
     * @param loggedin                The logged-in status.
     * @param courseNum               The course number.
     * @param courseName              The course name.
     * @param subjectNum              The subject number.
     */
	public Request(Integer testCode, String lecurerId, String hodId, Integer duration, String explanation,
			String id, String number, String courseNumber, Integer testDuration, String instructionsForStudent,
			String instructionsForLecturer, String userId, String name, String username, String password,
			String premission, String loggedin, String courseNum, String courseName, String subjectNum) {
		this.testCode = testCode;
		this.lecturerId = lecurerId;
		this.hodId = hodId;
		this.duration = duration;
		this.explanation = explanation;
		this.test = new Test(id, number, courseNumber, testDuration, instructionsForStudent, instructionsForLecturer);
		this.user = new User(userId, name, username, password, premission, loggedin);
		this.course = new Course(courseNum, courseName, subjectNum);
	}

	/**
	 * @return the testCode
	 */
	public Integer getTestCode() {
		return testCode;
	}

	/**
	 * @param testCode the testCode to set
	 */
	public void setTestCode(Integer testCode) {
		this.testCode = testCode;
	}

	/**
	 * @return the lecurerId
	 */
	public String getLecturerId() {
		return lecturerId;
	}

	/**
	 * @param lecurerId the lecurerId to set
	 */
	public void setLecturerId(String lecurerId) {
		this.lecturerId = lecurerId;
	}

	/**
	 * @return the hodId
	 */
	public String getHodId() {
		return hodId;
	}

	/**
	 * @param hodId the hodId to set
	 */
	public void setHodId(String hodId) {
		this.hodId = hodId;
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
	 * @return the explanation
	 */
	public String getExplanation() {
		return explanation;
	}

	/**
     * Returns the original duration from the associated test.
     *
     * @return The original duration.
     */
	public Integer getOriginalDuration() {
		return this.test.getDuration();
	}

	/**
     * Returns the lecturer name from the associated user.
     *
     * @return The lecturer name.
     */
	public String getLecturerName() {
		return this.user.getName();
	}
	
	/**
     * Returns the test ID from the associated test.
     *
     * @return The test ID.
     */
	public String getTestId() {
		return this.test.getId();
	}

	/**
     * Returns the course name from the associated course.
     *
     * @return The course name.
     */
	public String getCourseName() {
		return this.course.getName();
	}

	/**
	 * @param explanation the explanation to set
	 */
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
     * Checks if this Request object is equal to the specified object.
     * Two Request objects are considered equal if they have the same testCode and lecturerId.
     *
     * @param obj The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
	@Override
	public boolean equals(Object obj) {
		Request r = (Request) obj;
		return (testCode.equals(r.getTestCode()) && lecturerId.equals(r.getLecturerId()));
	}

	/**
     * Returns a string representation of this Request object.
     *
     * @return A string representation of the object.
     */
	@Override
	public String toString() {
		return "Request [testCode=" + testCode + ", lecurerId=" + lecturerId + ", hodId=" + hodId
				+ ", additionToDuration=" + duration + ", explanation=" + explanation + "id(test.id)="
				+ this.test.getId() + "courseName=" + this.course.getName() + "lecturerName=" + this.user.getName()
				+ "testOriginalDuration=" + this.test.getDuration() + "]";
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// for FX:
	/**
     * Sets a new CheckBox for this Request object.
     */
	public void setNewCheckbox() {
		checkbox = new CheckBox();
	}

	/**
     * Returns the CheckBox associated with this Request object.
     *
     * @return The CheckBox.
     */
	public CheckBox getCheckbox() {
		return checkbox;
	}

	/**
     * Sets a new RadioButton for this Request object.
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
     * Returns the RadioButton associated with this Request object.
     *
     * @return The RadioButton.
     */
	public RadioButton getRadioButton() {
		return radioButton;
	}
}