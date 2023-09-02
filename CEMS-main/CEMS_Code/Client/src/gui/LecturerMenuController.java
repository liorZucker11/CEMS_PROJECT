package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * The LecturerMenuController class is a controller class for the lecturer menu GUI.
 * It extends the AbstractController class and handles user interactions and events.
 */
public class LecturerMenuController extends AbstractController implements Menu{
	
    @FXML
    private Label welcomeLbl;
 
    /**
     * Initializes a new instance of the LecturerMenuController class.
     */
    public LecturerMenuController() {
    }
    
    /**
     * Handles the action event for approving grades.
     *
     * @param event The action event triggered.
     * @throws Exception if an error occurs during the operation.
     */
	@FXML
    void approveGrades(ActionEvent event) throws Exception{
		start("ApproveGrade", "LecturerMenu");
    }

	/**
	 * Handles the action event for creating a question.
	 *
	 * @param event The action event triggered.
	 * @throws Exception if an error occurs during the operation.
	 */
    @FXML
    void createQuestion(ActionEvent event) throws Exception {
		start("CreateQuestion", "LecturerMenu");
    }

    /**
     * Handles the action event for creating a test.
     *
     * @param event The action event triggered.
     * @throws Exception if an error occurs during the operation.
     */
    @FXML
    void createTest(ActionEvent event) throws Exception {
		start("CreateTest", "LecturerMenu");
    }

    /**
     * Handles the action event for executing a test.
     *
     * @param event The action event triggered.
     * @throws Exception if an error occurs during the operation.
     */
    @FXML
    void executeTest(ActionEvent event) throws Exception {
    	start("ExecuteTest", "LecturerMenu"); 
    }

    /**
     * Handles the action event for viewing reports.
     *
     * @param event The action event triggered.
     * @throws Exception if an error occurs during the operation.
     */
    @FXML
    void reports(ActionEvent event) throws Exception {
		start("LecturerTestView", "LecturerMenu");
    }

    /**
     * Handles the action event for running a test.
     *
     * @param event The action event triggered.
     * @throws Exception if an error occurs during the operation.
     */
    @FXML
    void runningTest(ActionEvent event) throws Exception {
    	start("RunningTest", "LecturerMenu");
    }
    
    /**
     * Logs out the user.
     *
     * @param event The action event triggered.
     * @throws Exception if an error occurs during the operation.
     */
	public void logout(ActionEvent event) throws Exception {
    	super.logout();
    	super.backBtn(event);
	}

	/**
	 * Sets the welcome label with the given name.
	 *
	 * @param name The name to be displayed in the welcome label.
	 */
	@Override
	public void setWelcome(String name) {
		welcomeLbl.setText(name);
	}
}