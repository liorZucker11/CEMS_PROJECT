package gui;

import java.util.ArrayList;

import client.ChatClient;
import communication.Msg;
import enteties.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

/**
 * The controller class for the Approve Changes screen in the GUI.
 * This class handles the logic and behavior of the screen's components.
 * 
 * @author Mor Shmuel
 */
public class ApproveChangesController extends AbstractController{
	
	 /**
     * The table column for the course name, explanation, test ID, lecturer name, radio button, duration, new doration.
     */
    @FXML
    private TableColumn<Request, String> CourseCol,ExplanationCol,IDTestCol,LecturerCol,checkBoxCol,previousCol,newDurationCol;

    /**
     * The table view for displaying the requests.
     */
    @FXML
    private TableView<Request> table  = new TableView<Request>();
    
    /**
     * The back confirm buttons.
     */
    @FXML
    private Button back,  confirm;

    /**
     * The text field for displaying the explanation of the selected request.
     */
    @FXML
    private Text textExplanation;
    
    /**
     * ToggleGroup for the requests in the table.
     */
	private ToggleGroup requestToggleGroup;
    
	/**
     * The selected request.
     */
    private Request chooseRequest; 
    
    /**
	 * list of Requests.
	 */
    private ArrayList<Request> request;
    
    /**
     * The observable list of changes for the table.
     */
    private ObservableList<Request> changesTable;
    
    /**
     * Initializes a new instance of the ApproveChangesController class.
     * This constructor is called when the screen is loaded.
     * @throws Exception 
     */
    public ApproveChangesController() throws Exception {
    	requestToggleGroup = new ToggleGroup();
    	Msg msg = requestController.selectRequest(ChatClient.user.getId());
    	sendMsg(msg); 
    	
    	if (msgReceived != null){ request = msgReceived.convertData(Request.class); }
    	
    	try {
    		if(request==null) {return;}
        	for (Request req : request) {
   			 req.setNewRadioButton();
   	         requestToggleGroup.getToggles().add((RadioButton)req.getRadioButton()); 
   		    	
   			 req.getRadioButton().selectedProperty().addListener((observable, oldValue, newValue) -> {
   		    		if (newValue == true) {
   		    			chooseRequest = req;
   		    			confirm.setDisable(false); //Enable the "Confirm" button
   		    		} 
   			 	});
        	}
        	changesTable = FXCollections.observableArrayList(request);
        	table.setItems(changesTable);
        	table.refresh();
    		}catch(Exception e) {}
    }
    
    /**
     * Initializes the controller class.
     * This method is automatically called after the FXML file has been loaded.
     */
    @FXML
	protected void initialize() {
    	if(request==null) {notification.showErrorAlert("There are no tests to confirm grades for."); return;}
    	CourseCol.setCellValueFactory(new PropertyValueFactory<Request, String>("courseName"));
    	ExplanationCol.setCellValueFactory(new PropertyValueFactory<Request, String>("explanation"));
    	IDTestCol.setCellValueFactory(new PropertyValueFactory<Request, String>("testId"));
    	LecturerCol.setCellValueFactory(new PropertyValueFactory<Request, String>("lecturerName"));
    	checkBoxCol.setCellValueFactory(new PropertyValueFactory<Request, String>("radioButton"));
    	previousCol.setCellValueFactory(new PropertyValueFactory<Request, String>("originalDuration"));
    	newDurationCol.setCellValueFactory(new PropertyValueFactory<Request, String>("duration"));

		table.setItems(changesTable);
		table.refresh();
	}
    
    /**
     * Event handler for displaying the answers of the selected request.
     *
     * @param event The MouseEvent triggering the event.
     */
    @FXML
    void showAnswers(MouseEvent event) {
        //Get the selected request from the table.
    	Request selectedReq = table.getSelectionModel().getSelectedItem();
        //Display the explanation in the UI.
    	textExplanation.setText(selectedReq.getExplanation());
    }

    /**
     * Event handler for the confirm button.
     * Approves the selected request.
     *
     * @param event The ActionEvent triggering the event.
     */
    @FXML
    void aprroveBtn(ActionEvent event) { //needed to add delete query
        if (chooseRequest != null) {
        	notification.setOnCancelAction(new Runnable() {	@Override public void run() {return;}});
        	notification.setOnOkAction(new Runnable() {
				@Override
				public void run() {
					Msg msg1 = requestController.getMsgToUpdateRequestDuration(chooseRequest);
		        	sendMsg(msg1);
		        	notification.showInformationAlert("Change approved in DB.");
		        	changesTable.remove(chooseRequest);
		        	return;
				}});
        
        	notification.showConfirmationAlert("you can't unapprove after that.", "Are you sure you want to approve the request?");
        } else {notification.showErrorAlert("You didnt select a request to approve."); return;}
    }
}