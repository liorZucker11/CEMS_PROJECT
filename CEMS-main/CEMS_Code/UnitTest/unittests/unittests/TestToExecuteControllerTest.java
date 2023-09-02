package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import communication.Msg;
import communication.MsgType;
import controllers.TestToExecuteController;
import enteties.TestToExecute;
import java.util.ArrayList;

public class TestToExecuteControllerTest {

	TestToExecuteController testToExecuteController;
	
	@BeforeEach
	void setUpBeforeClass() throws Exception {
		testToExecuteController = new TestToExecuteController();
	}

	//Description: checks the insertTestToExecute method when providing a valid TestToExecute object.
    //Input: TestToExecute object with valid values(testCode, testId, testType, date, lecturerId).
    //Expected Result: Msg object representing the database insert message(expectedMsg = resultMsg).
    @Test
    public void insertTestToExecuteTest_ValidTestToExecute() {
    	TestToExecute testToExecute = new TestToExecute();
    	testToExecute.setTestCode(1000);
    	testToExecute.setTestId("231201");
    	testToExecute.setTestingType("Online");
    	testToExecute.setDate("25.06.2023");
    	testToExecute.setLecturerId("31234561");

    	Msg expectedMsg = new Msg(MsgType.insert);
    	expectedMsg.setTableToUpdate("testtoexecute");
    	expectedMsg.setColNames("testCode, testId, testingType, date, lecturerId");
    	ArrayList<Object> expectedValues = new ArrayList<>();
    	expectedValues.add(1000);
    	expectedValues.add("231201");
    	expectedValues.add("Online");
    	expectedValues.add("25.06.2023");
    	expectedValues.add("31234561");
    	expectedMsg.setValues(expectedValues);
    	
    	Msg resultMsg = testToExecuteController.insertTestToExecute(testToExecute);
    	assertEquals(resultMsg.getType(), expectedMsg.getType());
	    assertEquals(expectedMsg.getTableToUpdate(), resultMsg.getTableToUpdate());
	    assertEquals(expectedMsg.getColNames(), resultMsg.getColNames());
	    assertEquals(expectedMsg.getValues(), resultMsg.getValues());
    }
    
    //Description: checks the insertTestToExecute method when providing a null TestToExecute object.
    //Input: null TestToExecute object.
    //Expected Result: Msg object representing the database insert message with null values and column names.
    @Test
    public void insertTestToExecuteTest_NullTestToExecute() {
        TestToExecute testToExecute = null;
        Msg expectedMsg = new Msg(MsgType.insert);
        expectedMsg.setTableToUpdate("testtoexecute");
        expectedMsg.setColNames("testCode, testId, testingType, date, lecturerId");
        ArrayList<Object> expectedValues = new ArrayList<>();
        expectedMsg.setValues(expectedValues);
        Msg resultMsg = null;
        try {
        	 resultMsg = testToExecuteController.insertTestToExecute(testToExecute);
        }catch(NullPointerException e) {fail("Exception");};
        assertEquals(resultMsg.getType(), expectedMsg.getType());
        assertEquals(expectedMsg.getTableToUpdate(), resultMsg.getTableToUpdate());
        assertEquals(expectedMsg.getColNames(), resultMsg.getColNames());
        assertEquals(expectedMsg.getValues(), resultMsg.getValues());
    }
    
    //Description: checks the insertTestToExecute method when providing a TestToExecute object with some value missing.
    //Input: TestToExecute object with missing testId and lecturerId values.
    //Expected Result: Msg object representing the database insert message with the provided values 
    //					and empty column names for the missing values([[1000, Online, 25.06.2023]]).
    @Test
    public void insertTestToExecuteTest_PartialTestToExecute() {
        TestToExecute testToExecute = new TestToExecute();
        testToExecute.setTestCode(1000);
        testToExecute.setTestingType("Online");
        testToExecute.setDate("25.06.2023");

        Msg expectedMsg = new Msg(MsgType.insert);
        expectedMsg.setTableToUpdate("testtoexecute");
        expectedMsg.setColNames("testCode, testId, testingType, date, lecturerId");
        ArrayList<Object> expectedValues = new ArrayList<>();
        expectedValues.add(1000);
        expectedValues.add("Online");
        expectedValues.add("25.06.2023");
        expectedMsg.setValues(expectedValues);

        Msg resultMsg = testToExecuteController.insertTestToExecute(testToExecute);
        assertEquals(resultMsg.getType(), expectedMsg.getType());
        assertEquals(expectedMsg.getTableToUpdate(), resultMsg.getTableToUpdate());
        assertEquals(expectedMsg.getColNames(), resultMsg.getColNames());
        assertEquals(expectedMsg.getValues(), resultMsg.getValues());
    }
    
    //Description: checks the checkInputs method when providing valid inputs.
    //Input: TestToExecute object with valid values (date, testCode, testingType) and a valid userId.
    //Expected Result: TestToExecute object with properties set according to the provided inputs.
    @Test
    public void checkInputsTest_ValidInputs() {
        TestToExecute selectedTest = new TestToExecute();
        String userId = "31234561";
        String date = "25.06.2023";
        String testCode = "1000";
        String testingType = "Online";
        TestToExecute expectedResult = new TestToExecute();
        expectedResult.setDate(date);
        expectedResult.setTestCode(1000);
        expectedResult.setTestingType(testingType);
        expectedResult.setLecturerId(userId);

        Object result = testToExecuteController.checkInputs(selectedTest, userId, date, testCode, testingType);
        assertEquals(expectedResult, result);
    }
    
    //Description: checks the checkInputs method when the test date is missing.
    //Input: TestToExecute object with a missing test date (date is an empty string, testCode, testingType) and a valid userId.
    //Expected Result: error message string: "You must enter test date.\n"
    @Test
    public void checkInputsTest_MissingTestDate() {
        TestToExecute selectedTest = new TestToExecute();
        String userId = "31234561";
        String date = "";
        String testCode = "1000";
        String testingType = "Online";
        
        Object result = testToExecuteController.checkInputs(selectedTest, userId, date, testCode, testingType);
        assertEquals("You must enter test date.\n", result);
    }
    
    //Description: checks the checkInputs method when an invalid test code is provided.
    //Input: TestToExecute object with an invalid test code (testCode is "12345", date, testingType) and a valid userId.
    //Expected Result: error message string: "Test code must be a number between 1000 and 9999.\n"
    @Test
    public void checkInputsTest_InvalidTestCode() {
        TestToExecute selectedTest = new TestToExecute();
        String userId = "31234561";
        String date = "25.06.2023";
        String testCode = "12345";
        String testingType = "Online";

        Object result = testToExecuteController.checkInputs(selectedTest, userId, date, testCode, testingType);
        assertEquals("Test code must be a number between 1000 and 9999.\n", result);
    }
    
    //Description: checks the checkInputs method when an invalid test code format is provided.
    //Input: TestToExecute object with an invalid test code format (testCode is "abc", date, testingType) and a valid userId.
    //Expected Result: error message string: "Test code must be an integer.\n"
    @Test
    public void checkInputsTest_InvalidTestCodeFormat() {
        TestToExecute selectedTest = new TestToExecute();
        String userId = "31234561";
        String date = "25.06.2023";
        String testCode = "abc";
        String testingType = "Online";

        Object result = testToExecuteController.checkInputs(selectedTest, userId, date, testCode, testingType);
        assertEquals("Test code must be an integer.\n", result);
    }
    
    //Description: checks the checkInputs method when the user ID is missing.
    //Input: TestToExecute object with valid values (date, testCode, testingType) and a missing user ID (userId is an empty string).
    //Expected Result: result = null, indicating that the user ID is not set.
    @Test
    public void checkInputsTest_ValidInputsAndMissingUserId() {
        TestToExecute selectedTest = new TestToExecute();
        String userId = "";
        String date = "25.06.2023";
        String testCode = "1000";
        String testingType = "Online";

        Object result = testToExecuteController.checkInputs(selectedTest, userId, date, testCode, testingType);
        assertNull(result);
    }
    
    //Description: checks the checkInputs method when providing null inputs.
    //Input: null TestToExecute object, null userId, null date, null testCode, null testingType.
    //Expected Result: result = null, indicating that the inputs are not valid.
    @Test
    public void checkInputsTest_NullInputs() {
        TestToExecute selectedTest = null;
        String userId = null;
        String date = null;
        String testCode = null;
        String testingType = null;
        Object result = null;
        try {
        	result = testToExecuteController.checkInputs(selectedTest, userId, date, testCode, testingType);
        }catch(NullPointerException e) {fail("Exception");};
        assertNull(result);
    }

}
