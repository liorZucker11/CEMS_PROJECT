package enteties;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

/**
 * The Question class represents a question entity.
 */
public class Question {
	//in DB:
	private String id;
	private Integer number;
	private String question;
	private String subjectNum;
	private String lecturerId;
	private String[] answers = new String[4]; //in DB its 4 separate strings
	private Integer correctAnswer;
	private String instructions;
	//not in DB:
	private Subject subject = null;
	private ArrayList<Course> courses = null;
	private Integer points = null;
	private Course course = null; //added by mor
	private User user = null; //added by mor
	//for FX:
	CheckBox checkbox;
	RadioButton radioButton;
	TextField textField;
	
	//private CheckBox select;
	//private TextField points;
	//private Button showQ = new Button();
	
	/**
	 * empty constructor.
	 */
	public Question() {super();}
	
	/**
     * Constructs a Question object with the specified parameters.
     * 
     * @param id           the ID of the question
     * @param number       the number of the question
     * @param question     the question text
     * @param subjectNum   the subject number
     * @param lecturerId   the ID of the lecturer
     * @param answers      an array of answer strings
     * @param correctAnswer the index of the correct answer
     * @param instructions the instructions for the question
     * @param courses      a list of courses associated with the question
     */
	public Question(String id, Integer number, String question, String subjectNum, String lecturerId, String[] answers, Integer correctAnswer, String instructions, ArrayList<Course> courses) {
		this.id = id;
		this.number = number;
		this.question = question;
		this.subjectNum = subjectNum;
		this.lecturerId = lecturerId;
		this.answers = answers;
		this.correctAnswer = correctAnswer;
		this.instructions = instructions;
		this.courses = courses;
	}
	
	/**
     * Constructs a Question object with the specified parameters.
     * 
     * @param id            the ID of the question
     * @param number        the number of the question
     * @param question      the question text
     * @param subjectNum    the subject number
     * @param lecturerId    the ID of the lecturer
     * @param ans1          the first answer
     * @param ans2          the second answer
     * @param ans3          the third answer
     * @param ans4          the fourth answer
     * @param correctAnswer the index of the correct answer
     * @param instructions  the instructions for the question
     */
	public Question(String id, Integer number, String question, String subjectNum, String lecturerId, String ans1, String ans2, String ans3, String ans4, Integer correctAnswer, String instructions) {
		this.id = id;
		this.number = number;
		this.question = question;
		this.subjectNum = subjectNum;
		this.lecturerId = lecturerId;
		this.answers = new String[] {ans1, ans2, ans3, ans4};
		this.correctAnswer = correctAnswer;
		this.instructions = instructions;
	}
	
	/**
     * Constructor for the Question class with parameters.
     *
     * @param id            the ID of the question
     * @param number        the question number
     * @param question      the question text
     * @param subjectNum    the subject number
     * @param lecturerId    the lecturer ID
     * @param answers       the array of answers
     * @param correctAnswer the index of the correct answer
     * @param instructions  the instructions for the question
     * @param courses
     * @param subjectName
     */
	public Question(String id, Integer number, String question, String subjectNum, String lecturerId, String[] answers, Integer correctAnswer, String instructions, ArrayList<Course> courses, String subjectName) {
		this.id = id;
		this.number = number;
		this.question = question;
		this.subjectNum = subjectNum;
		this.lecturerId = lecturerId;
		this.answers = answers;
		this.correctAnswer = correctAnswer;
		this.instructions = instructions;
		this.courses = courses;
		this.subject = new Subject(subjectNum, subjectName);
	}
	/**
	 * constructor for creating Question object with course parameter, belongs to that specific question
	 * 
	 * @param id
	 * @param number
	 * @param question
	 * @param subjectNum
	 * @param lecturerId
	 * @param ans1
	 * @param ans2
	 * @param ans3 
	 * @param ans4
	 * @param correctAnswer
	 * @param instructions
	 * @param numberCourse
	 * @param name
	 * @param subjectNumber
	 */
	public Question(String id, Integer number, String question, String subjectNum, String lecturerId,  String ans1, String ans2, String ans3, String ans4, Integer correctAnswer, String instructions, String numberCourse, String name, String subjectNumber) {
		this.id = id;
		this.number = number;
		this.question = question;
		this.subjectNum = subjectNum;
		this.lecturerId = lecturerId;
		this.answers = new String[] {ans1, ans2, ans3, ans4};
		this.correctAnswer = correctAnswer;
		this.instructions = instructions;
		this.course = new Course(numberCourse, name, subjectNumber);
	}
	
	/**
     * Constructor for the Question class with parameters.
     *
     * @param id            the ID of the question
     * @param number        the question number
     * @param question      the question text
     * @param subjectNum    the subject number
     * @param lecturerId    the lecturer ID
     * @param ans1
	 * @param ans2
	 * @param ans3 
	 * @param ans4
     * @param correctAnswer the index of the correct answer
     * @param instructions  the instructions for the question
     * @param points
     */
	public Question(String id, Integer number, String question, String subjectNum, String lecturerId, String ans1, String ans2, String ans3, String ans4, Integer correctAnswer, String instructions,Integer points) {
		this.id = id;
		this.number = number;
		this.question = question;
		this.subjectNum = subjectNum;
		this.lecturerId = lecturerId;
		this.answers = new String[] {ans1, ans2, ans3, ans4};
		this.correctAnswer = correctAnswer;
		this.instructions = instructions;
		this.points = points;
	}
	
	/**
	 * constructor for creating Question object with course and user parameter, belongs to that specific question
	 * 
	 * @param id
	 * @param number
	 * @param question
	 * @param subjectNum
	 * @param lecturerId
	 * @param ans1
	 * @param ans2
	 * @param ans3 
	 * @param ans4
	 * @param correctAnswer
	 * @param instructions
	 * @param numberCourse
	 * @param name
	 * @param subjectNumber
	 * @param userId
	 * @param userName
	 * @param username
	 * @param password
	 * @param premission
	 * @param loggedin
	 */
	public Question(String id, Integer number, String question, String subjectNum, String lecturerId,  String ans1, String ans2, String ans3, String ans4, Integer correctAnswer, String instructions, String numberCourse, String name, String subjectNumber, String userId, String userName, String username, String password, String premission, String loggedin) {
		this.id = id;
		this.number = number;
		this.question = question;
		this.subjectNum = subjectNum;
		this.lecturerId = lecturerId;
		this.answers = new String[] {ans1, ans2, ans3, ans4};
		this.correctAnswer = correctAnswer;
		this.instructions = instructions;
		this.course = new Course(numberCourse, name, subjectNumber);
		this.user = new User(userId, userName, username, password, premission, loggedin);
	}
	
	/**
	 * Retrieves the name of the subject associated with the question.
	 *
	 * @return The name of the subject associated with the question.
	 */
	public String getSubjectName() {
		return subject.getName();
	}

	/**
     * Gets the ID of the question.
     *
     * @return the ID of the question
     */
	public String getId() {
		return id;
	}

	/**
     * Sets the ID of the question.
     *
     * @param id the ID of the question
     */
	public void setId(String id) {
		this.id = id;
	}

	/**
     * Gets the number of the question.
     *
     * @return the number of the question
     */
	public Integer getNumber() {
		return number;
	}

	/**
     * Sets the number of the question.
     *
     * @param number the number of the question
     */
	public void setNumber(Integer number) {
		this.number = number;
	}

	/**
     * Gets the question text.
     *
     * @return the question text
     */
	public String getQuestion() {
		return question;
	}

	/**
     * Sets the question text.
     *
     * @param question the question text
     */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
     * Gets the lecturer ID.
     *
     * @return the lecturer ID
     */
	public String getLecturerId() {
		return lecturerId;
	}

	/**
     * Sets the lecturer ID.
     *
     * @param lecturerId the lecturer ID
     */
	public void setLecturerId(String lecturerId) {
		this.lecturerId = lecturerId;
	}

	/**
     * Gets the array of answers.
     *
     * @return String[] the array of answers
     */
	public String[] getAnswers() {
		return answers;
	}
	/**
	 * get one answer
	 */
	public String getOneAnswer(Integer i) {
		return answers[i];
	}
	
	/**
     * Sets the array of answers.
     *
     * @param answers the array of answers
     */
	public void setAnswers(String[] answers) {
		this.answers = answers;
	}

	/**
     * Gets the index of the correct answer.
     *
     * @return the index of the correct answer
     */
	public Integer getCorrectAnswer() {
		return correctAnswer;
	}

	/**
     * Sets the index of the correct answer.
     *
     * @param correctAnswer the index of the correct answer
     */
	public void setCorrectAnswer(Integer correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	/**
     * Gets the instructions for the question.
     *
     * @return the instructions for the question
     */
	public String getInstructions() {
		return instructions;
	}

	/**
     * Sets the instructions for the question.
     *
     * @param instructions the instructions for the question
     */
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	/**
	 * @return the courses
	 */
	public ArrayList<Course> getCourses() {
		return courses;
	}

	/**
	 * @param courses the courses to set
	 */
	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}

	/**
     * Gets the subject number.
     *
     * @return the subject number
     */
	public String getSubjectNum() {
		return subjectNum;
	}

	/**
     * Sets the subject number.
     *
     * @param subjectNum the subject number
     */
	public void setSubjectNum(String subjectNum) {
		this.subjectNum = subjectNum;
	}

	/**
     * Gets the points for the question.
     *
     * @return the points for the question
     */
	public Integer getPoints() {
		return points;
	}

	/**
     * Sets the points for the question.
     *
     * @param points the points for the question
     */
	public void setPoints(Integer points) {
		this.points = points;
	}
	
	/**
     * Gets the course associated with the question.
     *
     * @return the course associated with the question
     */
	public Course getCourse() {
		return course;
	}
	
	/**
	 * @return the course name
	 */
	public String getCourseName() {
		if(course==null)
			return null;
		return course.getName();
	}
	
	/**
     * Sets the course associated with the question.
     *
     * @param course the course associated with the question
     */
	public void setCourse(Course course) {
		this.course = course;
	}
	
	/**
	 * @return the user name
	 */
	public String getUserName() {
		if(user==null)
			return null;
		return user.getName();
	}
	
	/**
     * Sets the user associated with the question.
     *
     * @param user the user associated with the question
     */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Overrides the equals method to compare two Question objects based on their id field.
	 *
	 * @param obj The object to compare with the current Question object.
	 * @return true if the id of the current Question object is equal to the id of the given object, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		Question q = (Question) obj;
		return id.equals(q.getId());
	}
	
	/**
	 * Overrides the toString method to provide a string representation of the Question object.
	 *
	 * @return A string representation of the Question object, including its id, number, question, lecturerId,
	 *         answers, correctAns, instructions, and courses.
	 */
	@Override
	public String toString() {
		return "Question [id=" + id + ", number=" + number + ", question=" + question + ", lecturerId=" + lecturerId + ", answers=" + Arrays.toString(answers)
				+ ", correctAns=" + correctAnswer + ", instructions=" + instructions + ", courses=" + courses + "]";
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//for FX:
	/**
	 * Sets a new CheckBox object for the question.
	 */
	public void setNewCheckbox() {
		checkbox = new CheckBox();
		checkbox.setStyle(
		        "-fx-border-color: #009494;" +
		        "-fx-border-width: 2px;" +
		        "-fx-border-radius: 10%;" +
                "-fx-background-color: #FFFFFF;" +
                "-fx-background-radius: 10%;"
		    );
		    DropShadow hoverEffect = new DropShadow(4, Color.rgb(0, 0, 0, 0.6));
		    checkbox.setOnMouseEntered(event -> checkbox.setEffect(hoverEffect));
		    checkbox.setOnMouseExited(event -> checkbox.setEffect(null));
	}
	
	/**
     * Gets the checkbox associated with the question (for JavaFX).
     *
     * @return the checkbox associated with the question
     */
	public CheckBox getCheckbox() {
		return checkbox;
	}
	
	/**
	 * Sets a new RadioButton object for the question.
	 */
	public void setNewRadioButton() {
		radioButton = new RadioButton();
	}
	
	/**
     * Gets the radio button associated with the question (for JavaFX).
     *
     * @return the radio button associated with the question
     */
	public RadioButton getRadioButton() {
		return radioButton;
	}
	
	/**
	 * Sets a new TextField object for the question.
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
     * Gets the text field associated with the question (for JavaFX).
     *
     * @return the text field associated with the question
     */
	public TextField getTextField() {
		return textField;
	}
	
	
}