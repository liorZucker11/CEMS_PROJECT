package gui;

import enteties.StudentTest;
import enteties.TestToExecute;
/**
 * The Tests interface defines methods for accessing test-related information in the GUI.
 */
public interface Tests {
    /**
     * Retrieves the StudentTest object to show in the GUI.
     *
     * @return The StudentTest object to display.
     */
	public StudentTest getStudentTestToShow();
    /**
     * Retrieves the TestToExecute object to show in the GUI.
     *
     * @return The TestToExecute object to display.
     */
	public TestToExecute getTestToExecuteToShow();
    /**
     * Retrieves the current screen state in the GUI.
     *
     * @return The current screen state as a String.
     */
	public String getScreenState();
}