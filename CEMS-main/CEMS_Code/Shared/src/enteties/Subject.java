package enteties;

import java.util.ArrayList;

import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;

/**
 * Represents a Subject.
 * The class provides various constructors to create different instances of a subject object.
 */
public class Subject {
    //in DB:
	private String number;
	private String name;
	//not in DB:
	private ArrayList<Course> courses;
	//for FX:
	CheckBox checkbox;
	RadioButton radioButton;
	
	/**
	 * empty constructor.
	 */
	public Subject() {super();}
	
	/**
	 * Constructs a Subject object with the specified number and name.
	 *
	 * @param number The number of the subject.
	 * @param name The name of the subject.
	 */
	public Subject(String number, String name) {
		this.number = number;
		this.name = name;
	}
	
	/**
	 * Constructs a Subject object with the specified number, name, and list of courses.
	 *
	 * @param number The number of the subject.
	 * @param name The name of the subject.
	 * @param courses The list of courses associated with the subject.
	 */
	public Subject(String number, String name, ArrayList<Course> courses) {
		this.number = number;
		this.name = name;
		this.courses = courses;
	}
	
	/**
	 * Returns the number of the subject.
	 *
	 * @return The number of the subject.
	 */
	public String getNumber() {
		return number;
	}
	
	/**
	 * Sets the number of the subject.
	 *
	 * @param number The number of the subject to set.
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	
	/**
	 * Returns the name of the subject.
	 *
	 * @return The name of the subject.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the subject.
	 *
	 * @param name The name of the subject to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the list of courses associated with the subject.
	 *
	 * @return The list of courses associated with the subject.
	 */
	public ArrayList<Course> getCourses() {
		return courses;
	}
	
	/**
	 * Sets the list of courses associated with the subject.
	 *
	 * @param courses The list of courses to set.
	 */
	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}
	
	/**
	 * Compares this subject with the specified object for equality. Returns
	 * {@code true} if the given object is also a subject and has the same number
	 * as this subject.
	 *
	 * @param obj The object to compare for equality.
	 * @return {@code true} if the given object is equal to this subject, {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		Subject s = (Subject) obj;
		return number.equals(s.getNumber());
	}

	/**
	 * Returns a string representation of this subject. The string representation
	 * contains the number, name, and courses of the subject.
	 *
	 * @return A string representation of this subject.
	 */
	@Override
	public String toString() {
		return "Subject [number=" + number + ", name=" + name + ", courses=" + courses + "]";
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//for FX:
	
	/**
	 * Creates a new checkbox for the subject.
	 */
	public void setNewCheckbox() {
		checkbox = new CheckBox();
	}
	
	/**
	 * Returns the checkbox associated with the subject.
	 *
	 * @return The checkbox associated with the subject.
	 */
	public CheckBox getCheckbox() {
		return checkbox;
	}
	
	/**
	 * Creates a new radio button for the subject.
	 */
	public void setNewRadioButton() {
		radioButton = new RadioButton();
	}
	
	/**
	 * Returns the radio button associated with the subject.
	 *
	 * @return The radio button associated with the subject.
	 */
	public RadioButton getRadioButton() {
		return radioButton;
	}
}