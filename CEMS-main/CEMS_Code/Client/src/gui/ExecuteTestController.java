package gui;

import java.util.ArrayList;

import client.ChatClient;
import communication.Msg;
import enteties.StudentTest;
import enteties.Test;
import enteties.TestToExecute;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

/**
 * Controller class for the ExecuteTest screen.
 * 
 * @author Yuval Rozner 
 */
public class ExecuteTestController extends AbstractController implements Tests{

	/**
	 * toggleGroup for choosing a test to execute.
	 */
	ToggleGroup toggleGroupOfTestToExecute;
	/**
	 * the TestToExecute for the show button.
	 */
	TestToExecute testToExecuteToShow;
	/**
	 * the ObservableList for the table.
	 */
	private ObservableList<TestToExecute> testToExecuteTable;
	/**
	 * table of tests.
	 */
    @FXML
    private TableView<TestToExecute> table = new TableView<TestToExecute>();
    /**
     * screens buttons.
     */
    @FXML
    private Button backbtn, executebtn;
    /**
     * table columns.
     */
    @FXML
    private TableColumn<TestToExecute,String> codecol,coursnamecol,numTestcol,selectcol,showcol,typecol,datecol;
    /**
	 * the wanted test to execute.
	 */
    private TestToExecute selectedTest = null;
    /**
	 * the list of testToExecute for the table created by the list of tests.
	 */
    private ArrayList<TestToExecute> executeTests;
    /**
	 * the list of tests for the comboBox according to the user logged in.
	 */
    private ArrayList<Test> testLst;

    /**
     * Constructs an instance of ExecuteTestController.
     * Initializes the toggle group for test execution selection.
     * Retrieves the list of tests for the current user.
     * Executes the tests and generates a list of TestToExecute objects.
     * Sets up event handlers and listeners for each test execution in the list.
     * Populates the test execution table with the generated data.
     */
	@SuppressWarnings("unchecked")
	public ExecuteTestController() {
    	toggleGroupOfTestToExecute = new ToggleGroup();
    	Msg msg = testController.selectTestByUser(ChatClient.user);
    	sendMsg(msg);
    	if (msgReceived == null){return;}
    	testLst = msgReceived.convertData(Test.class);
    	executeTests = testToExecuteController.executeListOfTests(testLst, ChatClient.user);
    	
        for (TestToExecute test : executeTests) {
        	toggleGroupOfTestToExecute.getToggles().add((RadioButton)test.getRadioButton()); 
        	test.getButton().setOnMouseClicked(event -> { 
        		try { showTestOpen(event);
				} catch (Exception e) {	e.printStackTrace();} 	});
        	
        	test.getRadioButton().selectedProperty().addListener((observable, oldValue, newValue) -> {
        		if (newValue == true) {
        			selectedTest = test;
        			test.getComboBox().setDisable(false);
                	test.getTextField().setDisable(false);
                 	test.getTextField1().setDisable(false);
        		}
        		else {
        			test.getComboBox().setDisable(true);
                	test.getTextField().setDisable(true);
                 	test.getTextField1().setDisable(true);
        		} 
        	});
        	test.getComboBox().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            	@Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    test.setTestingType((String) test.getComboBox().getValue()); }
            });
        }
        testToExecuteTable = FXCollections.observableArrayList(executeTests);
        table.setItems(testToExecuteTable);
		table.refresh();
    }
    	
	/**
	 * Initializes the test execution table and its columns.
	 * Sets up the cell value factories for each column and populates the table with the test execution data.
	 */
    @FXML
	protected void initialize() {
    	numTestcol.setCellValueFactory(new PropertyValueFactory<TestToExecute, String>("testId"));
    	coursnamecol.setCellValueFactory(new PropertyValueFactory<TestToExecute, String>("courseName"));
    	datecol.setCellValueFactory(new PropertyValueFactory<TestToExecute, String>("textField"));
    	showcol.setCellValueFactory(new PropertyValueFactory<TestToExecute, String>("button"));
    	selectcol.setCellValueFactory(new PropertyValueFactory<TestToExecute, String>("radioButton"));	
    	codecol.setCellValueFactory(new PropertyValueFactory<TestToExecute, String>("textField1"));
    	typecol.setCellValueFactory(new PropertyValueFactory<TestToExecute, String>("comboBox"));
		table.setItems(testToExecuteTable);
		table.refresh();
	}

    /**
     * Handles the execution of a test when the execute test button is clicked.
     *
     * @param event The action event triggered by clicking the execute test button.
     * @throws Exception 
     */
    @FXML
    void executeTestBtn(ActionEvent event) throws Exception {
    	if(selectedTest==null) {notification.showErrorAlert("You must choose a test to execute."); return;}
    	Object newTestToExecute = testToExecuteController.checkInputs(selectedTest, ChatClient.user.getId(), selectedTest.getTextField().getText(), selectedTest.getTextField1().getText(),(String)selectedTest.getComboBox().getValue());
    	if(newTestToExecute instanceof String) {notification.showErrorAlert((String)newTestToExecute); return;}
    	notification.setOnCancelAction(new Runnable() {	@Override public void run() {return;}});
	    notification.setOnOkAction(new Runnable() {
			@Override
			public void run() {
		    	Msg msg = testToExecuteController.insertTestToExecute((TestToExecute)newTestToExecute);
		    	sendMsg(msg);
		    	notification.showInformationAlert("The test executed with code "+((TestToExecute)newTestToExecute).getTestCode()+".");
		    	try {resetFields();} catch (Exception e) {e.printStackTrace();}	}});
	    String notificationStr = "testing Code: "+((TestToExecute)newTestToExecute).getTestCode()+", "+
		    	((TestToExecute)newTestToExecute).getCourseName()+", "+((TestToExecute)newTestToExecute).getTestingType()+
		    	", on "+((TestToExecute)newTestToExecute).getDate();
	    notification.showConfirmationAlert(notificationStr , "Are you sure you want to execute the test?");
    }
    
    /**
     * Resets all input fields to their default values.
     * @throws Exception 
     */
    private void resetFields() throws Exception {
    	start("ExecuteTest", "LecturerMenu"); 
    }
    
    /**
     * Handles the event when a test execution is clicked to be shown.
     * Retrieves the corresponding TestToExecute object based on the clicked button.
     * Sets the selected test execution to be shown and starts the "showStudentTest" view.
     *
     * @param event The mouse event triggered by clicking the show button of a test execution.
     * @throws Exception if an exception occurs during the process.
     */
    @FXML
    private void showTestOpen(MouseEvent event) throws Exception{
    	 Button clickedButton = (Button) event.getSource(); //get the button that has been clicked
         for (TestToExecute testToExecute : testToExecuteTable)  //search for the studentTest.
        	 if (testToExecute.getButton().equals(clickedButton)) { 
             	testToExecuteToShow = testToExecute;
             	start("ShowStudentTest", "ExecuteTest");
             }		
    }
    
    /**
     * Returns the TestToExecute object to be shown.
     *  @return The TestToExecute object to be shown.
     */
    @Override
    public TestToExecute getTestToExecuteToShow() {
		return testToExecuteToShow;
    }
    /**
     * Returns the StudentTest object to be shown.
     *
     * @return The StudentTest object to be shown.
     */
	@Override
	public StudentTest getStudentTestToShow() {
		return null;
	}
	/**
     *   Returns the screen state.
     * @return The screen state.
     */
	@Override
	public String getScreenState() {
		return "lecturerHodShowTest";
	}
}