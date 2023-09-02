package gui;

import java.util.ArrayList;

import client.ChatClient;
import communication.Msg;
import enteties.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller class for the HodMenu screen.
 * This class handles the actions and logic related to the HodMenu view.
 * It displays the HodMenu screen and provides functionality for various buttons and actions.
 * Implements the Menu interface for setting the welcome label.
 * Extends the HodScreen class for common HoD screen functionality.
 * 
 * @author Mor Shmuel
 */
public class HodMenuController extends AbstractController implements Menu{
	
	@FXML
    private Label welcomeLbl;
	
	/**
	 * the list of Request for finding if there are any requests waiting .
	 */
    private ArrayList<Request> request = null;
	
    /**
     * Constructs a new instance of HodMenuController.
     * This constructor is responsible for initializing the controller,
     * retrieving the relevant requests from the database,
     * and displaying the pop-up requests if there are any.
     */
	public HodMenuController(){
		Msg msg = requestController.selectFoundRequest(ChatClient.user.getId());
    	sendMsg(msg);
    	if (msgReceived != null)
    		request = msgReceived.convertData(Request.class);
    	//Display the pop-up requests if there are any
    	if(request!=null) {
    		popMessage("You got a new Request waiting for you.");
       	}
	}

	/**
     * Displays the Questions table view.
     * This method is called when the "Show Questions" button is clicked.
     * It opens the "questionTable" view and closes the "hodMenu" view.
     *
     * @param event The action event triggered by clicking the "Show Questions" button.
     * @throws Exception if an error occurs during the view transition.
     */
    @FXML
    void ShowQuestions(ActionEvent event) throws Exception {
    	start("QuestionTable", "HodMenu");
    }

    /**
     * Displays the approve changes view.
     * This method is called when the "Confirm Duration" button is clicked.
     * It opens the "approveChanges" view and closes the "hodMenu" view.
     *
     * @param event The action event triggered by clicking the "Confirm Duration" button.
     * @throws Exception if an error occurs during the view transition.
     */
    @FXML
    void approveChange(ActionEvent event) throws Exception {
		start("ApproveChanges", "HodMenu");
    }

    /**
     * Logs out the user and navigates to the login screen.
     * This method is called when the "Log Out" button is clicked.
     * It performs the logout action and navigates to the login screen.
     *
     * @param event The action event triggered by clicking the "Log Out" button.
     * @throws Exception if an error occurs during the view transition.
     */
    @FXML
    void logOut(ActionEvent event) throws Exception{
    	super.logout();
    	super.backBtn(event);
    }

    /**
     * Displays the choose report type view.
     * This method is called when the "Reports" button is clicked.
     * It opens the "chooseReportType" view and closes the "hodMenu" view.
     *
     * @param event The action event triggered by clicking the "Reports" button.
     * @throws Exception if an error occurs during the view transition.
     */
    @FXML
    void reports(ActionEvent event) throws Exception {
		start("ChooseReportType", "HodMenu");
    }

    /**
     * Displays the show tests data view.
     * This method is called when the "Show Test" button is clicked.
     * It opens the "showTestsData" view and closes the "hodMenu" view.
     *
     * @param event The action event triggered by clicking the "Show Test" button.
     * @throws Exception if an error occurs during the view transition.
     */
    @FXML
    void showTest(ActionEvent event) throws Exception {
		start("ShowTestsData", "HodMenu");
    }
    
    /**
     * Sets the welcome label text.
     * This method is called by the parent Menu class to set the welcome label.
     *
     * @param name The name to be displayed in the welcome label.
     */
    @Override
    public void setWelcome(String name) {
		welcomeLbl.setText(name);
	}
}