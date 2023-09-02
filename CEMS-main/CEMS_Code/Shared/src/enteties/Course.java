package enteties;

import java.util.ArrayList;

import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;

/**
 * The Course class represents a course entity.
 */
public class Course {
    //in DB:
	private String number;
	private String name;
	private String subjectNum;
	//not in DB:
	ArrayList<Question> questions;
	
	//for FX:
	CheckBox checkbox;
	RadioButton radioButton;

	/**
	 * empty constructor.
	 */
	public Course() {super();}
	
	/**
     * Constructs a Course object with the specified number, name, and subject number.
     *
     * @param number     The course number.
     * @param name       The course name.
     * @param subjectNum The subject number.
     */
	public Course(String number, String name, String subjectNum) {
		this.number = number;
		this.name = name;
		this.subjectNum = subjectNum;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the subjectNum
	 */
	public String getSubjectNum() {
		return subjectNum;
	}
	
	/**
	 * @param subjectNum the subjectNum to set
	 */
	public void setSubjectNum(String subjectNum) {
		this.subjectNum = subjectNum;
	}
	

	/**
	 * @return the questions
	 */
	public ArrayList<Question> getQuestions() {
		return questions;
	}

	/**
	 * @param questions the questions to set
	 */
	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}

	/**
     * Compares this Course object with the specified object for equality.
     *
     * @param obj The object to compare.
     * @return {@code true} if the specified object is equal to this Course, {@code false} otherwise.
     */
	@Override
	public boolean equals(Object obj) {
		Course c = (Course) obj;
		return number.equals(c.getNumber());
	}
	
	/**
     * Returns a string representation of the Course object.
     *
     * @return A string representation of the Course object.
     */
	@Override
	public String toString() {
		return "Course [number=" + number + ", name=" + name + ", subjectNum=" + subjectNum + "]";
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//for FX:
	/**
     * Sets a new CheckBox for the course.
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
     * Returns the CheckBox associated with the course.
     *
     * @return The CheckBox object.
     */
	public CheckBox getCheckbox() {
		return checkbox;
	}
	
	/**
     * Sets a new RadioButton for the course.
     */
	public void setNewRadioButton() {
		radioButton = new RadioButton();
	}
	
	/**
     * Returns the RadioButton associated with the course.
     *
     * @return The RadioButton object.
     */
	public RadioButton getRadioButton() {
		return radioButton;
	}
}