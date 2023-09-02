package gui;

import java.util.ArrayList;

import client.ChatClient;
import communication.Msg;
import enteties.TestToExecute;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller class for the LecturerTestView screen.
 *  
 * @author Yuval Rozner 
 */
public class LecturerTestViewController extends AbstractController {
    
    /**
     * the table of the tests.
     */
	@FXML
    private TableColumn<TestToExecute, String> selectCol, testCodeCol, courseCol, dateCol, finishedCol, studentsStartedCol;
    /**
     * columns for the table.
     */
    @FXML
    private TableView<TestToExecute> table;
    /**
     * the toggleGrope for selecting a test to change or lock from table.
     */
    ToggleGroup toggleGroupOfTestToExecute = new ToggleGroup();
    /**
     * the observable list of running test for the table.
     */
    private ObservableList<TestToExecute> testTable;
    /**
     * the list of running test (type TestToExecute) got from DB.
     */
    private ArrayList<TestToExecute> testLst;
    /**
	 * object to save the TestToExecute chosen by radio button.
	 */
    private TestToExecute selectedTest;
    
    /**
     * Constructs a new instance of LecturerTestViewController.
     * This constructor is responsible for initializing the controller and retrieving the relevant TestToExecute data from the database.
     * It populates the testTable with TestToExecute objects and configures the toggleGroupOfTestToExecute with the corresponding radio buttons.
     */
    public LecturerTestViewController() {
    	// get the relevant TestToExecute from DB:
    	Msg msg = testToExecuteController.selectTestToExecuteByUser(ChatClient.user);
    	sendMsg(msg);
    	if (msgReceived == null) {notification.showInformationAlert("There are no Test relate to you to show statistic on.");return;}
    	testLst = msgReceived.convertData(TestToExecute.class); //ArrayList
    	// put some FX fields but only the RadioButton is relevant :
    	testTable = testToExecuteController.getObservLstWithFXValues(testLst); //ObservableList 
    	// toggle the radio in the table:
        for (TestToExecute test : testTable) {
            RadioButton radioButton = test.getRadioButton();
            toggleGroupOfTestToExecute.getToggles().add(radioButton);
            //Add event handler to the radio button
            radioButton.setOnAction(event -> {
            	selectedTest = test;
            });
        }
    }
    
    /**
     * Initializes the table view and sets up the cell value factories.
     * This method is automatically called after the FXML file has been loaded.
     * It configures the columns and binds them to the corresponding properties of the TestToExecute objects.
     * It also sets the items of the table view and refreshes it.
     */
    @FXML
    protected void initialize() {
    	selectCol.setCellValueFactory(new PropertyValueFactory<TestToExecute, String>("radioButton"));
    	testCodeCol.setCellValueFactory(new PropertyValueFactory<TestToExecute, String>("testCode"));
    	courseCol.setCellValueFactory(new PropertyValueFactory<TestToExecute, String>("courseName"));
    	dateCol.setCellValueFactory(new PropertyValueFactory<TestToExecute, String>("date"));
    	finishedCol.setCellValueFactory(new PropertyValueFactory<TestToExecute, String>("finished"));
    	studentsStartedCol.setCellValueFactory(new PropertyValueFactory<TestToExecute, String>("numberOfStudentsStarted"));
        table.setItems(testTable);
        table.refresh();
    }
	
    /**
	 * @return the selectedTest
	 */
	public TestToExecute getSelectedTest() {
		return selectedTest;
	}
    
    /**
     * Displays the statistics report for the lecturer.
     * This method is called when the "Show Statistics" button is clicked.
     * It opens the "lecturerStaticsReport" view and closes the "lecturerTestView" view.
     *
     * @param event The action event triggered by clicking the "Show Statistics" button.
     * @throws Exception if an error occurs during the view transition.
     */
	public void showStatistics(ActionEvent event) throws Exception {
		LecturerStaticsReportController.selectedTest = selectedTest;
		start("LecturerStaticsReport", "LecturerTestView");
	}
}