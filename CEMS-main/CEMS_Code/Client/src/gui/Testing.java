package gui;
/**
 * The Testing interface defines a method for handling the case when a test is manually locked by a lecturer.
 */
public interface Testing {
    /**
     * force to Handles the scenario when a test is manually locked by a lecturer.
     *
     * @param testCode The code of the test being checked for manual locking.
     */
	public void testGotManualyLockedByLecturer(String testCode);
	public void testdurationGotChanged(String string, Integer duration);
}