package gui;

/**
 * The Menu interface defines a method for setting a welcome message.
 * Classes implementing this interface should provide an implementation
 * for the setWelcome method.
 */
public interface Menu {
	/**
     * Sets the welcome message.
     * 
     * @param name the name to be included in the welcome message
     */
	public void setWelcome(String name);
}