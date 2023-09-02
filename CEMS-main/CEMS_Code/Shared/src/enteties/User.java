package enteties;

import java.io.Serializable;

import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;

/**
 * The User class represents a user entity.
 * It stores information about a user, such as their ID, name, username, password, permission level,
 * and login status. It also includes additional fields for UI elements like CheckBox and RadioButton.
 * The class provides getters and setters for accessing and modifying the user's attributes.
 * It also overrides the equals(), hashCode(), and toString() methods for object comparison and string representation.
 * The User class is primarily used for user management and display purposes.
 * 
 */
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	//in DB:
	private String id;
	private String name;
	private String username;
	private String password;
	private String premission;
	private String loggedin; // yes/no
	//not in DB:
	
	//for FX:
	CheckBox checkbox;
	RadioButton radioButton;
	
	
	/**
	 * for get name of user
	 */
	public User(String name) {this.name=name;} 
	
	/**
	 * empty constructor.
	 */
	public User() {super();} 
	
	/**
     * Parameterized constructor for the User class.
     *
     * @param id         The ID of the user.
     * @param name       The name of the user.
     * @param username   The username of the user.
     * @param password   The password of the user.
     * @param premission The permission level of the user.
     * @param loggedin   The login status of the user (yes/no).
     */
	public User(String id, String name, String username, String password, String premission, String loggedin) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.premission = premission;
		this.loggedin = loggedin;
	}
	
	

	/**
     * Retrieves the ID of the user.
     *
     * @return The ID of the user.
     */
	public String getId() {
		return id;
	}

	/**
     * Sets the ID of the user.
     *
     * @param id The ID to set.
     */
	public void setId(String id) {
		this.id = id;
	}

	/**
     * Retrieves the name of the user.
     *
     * @return The name of the user.
     */
	public String getName() {
		return name;
	}

	/**
     * Sets the name of the user.
     *
     * @param name The name to set.
     */
	public void setName(String name) {
		this.name = name;
	}

	/**
     * Retrieves the username of the user.
     *
     * @return The username of the user.
     */
	public String getUsername() {
		return username;
	}

	 /**
     * Sets the username of the user.
     *
     * @param username The username to set.
     */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
     * Retrieves the password of the user.
     *
     * @return The password of the user.
     */
	public String getPassword() {
		return password;
	}

	/**
     * Sets the password of the user.
     *
     * @param password The password to set.
     */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
     * Retrieves the permission level of the user.
     *
     * @return The permission level of the user.
     */
	public String getPremission() {
		return premission;
	}

	/**
     * Sets the permission level of the user.
     *
     * @param premission The permission level to set.
     */
	public void setPremission(String premission) {
		this.premission = premission;
	}

	/**
     * Retrieves the login status of the user.
     *
     * @return The login status of the user.
     */
	public String getLoggedin() {
		return loggedin;
	}

	/**
     * Sets the login status of the user.
     *
     * @param loggedin The login status to set.
     */
	public void setLoggedin(String loggedin) {
		this.loggedin = loggedin;
	}
	
	/**
     * Checks if the current User object is equal to the provided object.
     *
     * @param obj The object to compare.
     * @return True if the objects are equal, false otherwise.
     */
	@Override
	public boolean equals(Object obj) {
		User u = (User) obj;
		return id.equals(u.getId());
	}
	
	/**
     * Generates the hash code for the User object.
     *
     * @return The hash code value.
     */
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	/**
     * Retrieves the string representation of the User object.
     *
     * @return The string representation of the User object.
     */
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + ", premission=" + premission + ", loggedin=" + loggedin + "]";
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//for FX:
	/**
     * Creates a new CheckBox object for the User.
     */
	public void setNewCheckbox() {
		checkbox = new CheckBox();
	}
	
	/**
     * Retrieves the CheckBox object associated with the User.
     *
     * @return The CheckBox object.
     */
	public CheckBox getCheckbox() {
		return checkbox;
	}
	
	/**
     * Creates a new RadioButton object for the User.
     */
	public void setNewRadioButton() {
		radioButton = new RadioButton();
	}
	
	/**
     * Retrieves the RadioButton object associated with the User.
     *
     * @return The RadioButton object.
     */
	public RadioButton getRadioButton() {
		return radioButton;
	}
}