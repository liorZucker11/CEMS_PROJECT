package unittests;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import communication.Msg;
import communication.MsgType;
import controllers.QuestionController;
import enteties.Course;
import enteties.Question;
import java.util.ArrayList;


public class QuestionControllerTest {

	QuestionController questionController;
	
	@BeforeEach
	void setUpBeforeClass() throws Exception {
		questionController = new QuestionController();
	}
	
	//Description: checks the insertQuestion method in the QuestionController class when inserting a question with associated courses.
	//Input: a new Question object and set its properties(id, number, question, subjectNum, lecturerId, answers, correct answer, instructions, courses).
	//			2 Msg object (tempMsg1, expectedMsg1) 
	//Expected Result: a Msg object (actualMsg1) with the same type, tableToUpdate, colNames, and values as expectedMsg1.
	@Test
	void InsertQuestionTest_WithCourses() {
	    Question question = new Question();
	    question.setId("22101");
	    question.setNumber(101);
	    question.setQuestion("Which logical operator represents the conjunction (AND) operation?");
	    question.setSubjectNum("22");
	    question.setLecturerId("21234560");
	    question.setAnswers(new String[]{"OR", "AND", "NOR", "NOT"});
	    question.setCorrectAnswer(2);
	    question.setInstructions("Select the correct answer.");

	    Msg tempMsg1 = new Msg(MsgType.insert);
	    tempMsg1.setTableToUpdate("question");
	    tempMsg1.setColNames("id, number, question, subjectNum, lecturerId, answer1, answer2, answer3, answer4, correctAnswer, instructions");
	    
	    ArrayList<Object> values = new ArrayList<>();
	    values.add("22101");
	    values.add(101);
	    values.add("Which logical operator represents the conjunction (AND) operation?");
	    values.add("22");
	    values.add("21234560");
	    values.add("OR");
	    values.add("AND");
	    values.add("NOR");
	    values.add("NOT");
	    values.add(2);
	    values.add("Select the correct answer.");
	    tempMsg1.setValues(values);
	    
	    Msg expectedMsg1 = new Msg(MsgType.manyMessages);
	    expectedMsg1.setMsgLst(tempMsg1);

	    ArrayList<Course> courses = new ArrayList<>();
	    courses.add(new Course("11", "Logic", "22"));
	    courses.add(new Course("12", "Algebra", "23"));
	    question.setCourses(courses);

	    Msg actualMsg1 = questionController.insertQuestion(question);
	    assertEquals(actualMsg1.getType(), expectedMsg1.getType());
	    assertEquals(expectedMsg1.getTableToUpdate(), actualMsg1.getTableToUpdate());
	    assertEquals(expectedMsg1.getColNames(), actualMsg1.getColNames());
	    assertEquals(expectedMsg1.getValues(), actualMsg1.getValues());
	}
	
	//Description: checks the insertQuestion method in the QuestionController class when inserting a question without any associated courses.
	//Input: a new Question object and set its properties(id, number, question, subjectNum, lecturerId, answers, correct answer, instructions, courses-empty ArrayList).
	//			2 Msg object (tempMsg1, expectedMsg1) 
	//Expected Result: a Msg object (actualMsg1) with the same type, tableToUpdate, colNames, and values as expectedMsg1.
	@Test
	void InsertQuestionTest_WithoutCourses() {
	    Question question = new Question();
	    question.setId("22101");
	    question.setNumber(101);
	    question.setQuestion("Which logical operator represents the conjunction (AND) operation?");
	    question.setSubjectNum("22");
	    question.setLecturerId("21234560");
	    question.setAnswers(new String[]{"OR", "AND", "NOR", "NOT"});
	    question.setCorrectAnswer(2);
	    question.setInstructions("Select the correct answer.");

	    Msg tempMsg1 = new Msg(MsgType.insert);
	    tempMsg1.setTableToUpdate("question");
	    tempMsg1.setColNames("id, number, question, subjectNum, lecturerId, answer1, answer2, answer3, answer4, correctAnswer, instructions");

	    ArrayList<Object> values = new ArrayList<>();
	    values.add("22101");
	    values.add(101);
	    values.add("Which logical operator represents the conjunction (AND) operation?");
	    values.add("22");
	    values.add("21234560");
	    values.add("OR");
	    values.add("AND");
	    values.add("NOR");
	    values.add("NOT");
	    values.add(2);
	    values.add("Select the correct answer.");
	    ArrayList<Course> courses = new ArrayList<>();
	    question.setCourses(courses);
	    tempMsg1.setValues(values);
	    
	    Msg expectedMsg1 = new Msg(MsgType.manyMessages);
	    expectedMsg1.setMsgLst(tempMsg1);

	    Msg actualMsg1 = questionController.insertQuestion(question);
	    assertEquals(actualMsg1.getType(), expectedMsg1.getType());
	    assertEquals(expectedMsg1.getTableToUpdate(), actualMsg1.getTableToUpdate());
	    assertEquals(expectedMsg1.getColNames(), actualMsg1.getColNames());
	    assertEquals(expectedMsg1.getValues(), actualMsg1.getValues());
	}
	
	//Description: checks the insertQuestion method in the QuestionController class when inserting a question with null values for all its fields.
	//Input: a new Question object and set its properties all set to null(id, number, question, subjectNum, lecturerId, answers, correct answer, instructions, courses-empty ArrayList).
	//			2 Msg object (tempMsg1, expectedMsg1) 
	//Expected Result: a Msg object (actualMsg1) with the same type, tableToUpdate, colNames, and values as expectedMsg1(all null).
	@Test
	void InsertQuestionTest_WithNullQuestionFields() {
	    Question question = new Question();
	    Msg actualMsg1 = null;
	    Msg expectedMsg1 = null;
	    try {
		    question.setId(null);
		    question.setNumber(null);
		    question.setQuestion(null);
		    question.setSubjectNum(null);
		    question.setLecturerId(null);
		    question.setAnswers(null);
		    question.setCorrectAnswer(null);
		    question.setInstructions(null);

		    Msg tempMsg1 = new Msg(MsgType.insert);
		    tempMsg1.setTableToUpdate("question");
		    tempMsg1.setColNames("id, number, question, subjectNum, lecturerId, answer1, answer2, answer3, answer4, correctAnswer, instructions");

		    ArrayList<Object> values = new ArrayList<>();
		    values.add(null);
		    values.add(null);
		    values.add(null);
		    values.add(null);
		    values.add(null);
		    values.add(null);
		    values.add(null);
		    values.add(null);
		    values.add(null);
		    values.add(null);
		    values.add(null);
		    ArrayList<Course> courses = new ArrayList<>();
		    question.setCourses(courses);
		    tempMsg1.setValues(values);
		    
		    expectedMsg1 = new Msg(MsgType.manyMessages);
		    expectedMsg1.setMsgLst(tempMsg1);
		    actualMsg1 = questionController.insertQuestion(question);
	    }catch(NullPointerException e) {fail("Exception");};

	    assertEquals(actualMsg1.getType(), expectedMsg1.getType());
	    assertEquals(expectedMsg1.getTableToUpdate(), actualMsg1.getTableToUpdate());
	    assertEquals(expectedMsg1.getColNames(), actualMsg1.getColNames());
	    assertEquals(expectedMsg1.getValues(), actualMsg1.getValues());
	}

	//Description: checks the checkInputs method in the QuestionController class when validating valid input values.
	//Input: id, number, question, subjectNum, lecturerId, ans1, ans2, ans3, ans4, correct answer, instructions.
	//Expected Result: a Question object with the same input values.
	@Test
    public void checkInputsTest_ValidInputs() {
        String id = "22101";
        String number = "101";
        String question = "Which logical operator represents the conjunction (AND) operation?";
        String subjectNum = "22";
        String lecturerId = "21234560";
        String ans1 = "OR";
        String ans2 = "AND";
        String ans3 = "NOR";
        String ans4 = "NOT";
        Integer correctAns = 2;
        String instructions = "Select the correct answer.";
        Object result = questionController.checkInputs(id, number, question, subjectNum, lecturerId, ans1, ans2, ans3, ans4, correctAns, instructions);

        assertTrue(result instanceof Question);
        Question createdQuestion = (Question) result;
        assertEquals(id, createdQuestion.getId());
        assertEquals(Integer.valueOf(number), createdQuestion.getNumber());
        assertEquals(question, createdQuestion.getQuestion());
        assertEquals(subjectNum, createdQuestion.getSubjectNum());
        assertEquals(lecturerId, createdQuestion.getLecturerId());
        assertArrayEquals(new String[]{ans1, ans2, ans3, ans4}, createdQuestion.getAnswers());
        assertEquals(correctAns, createdQuestion.getCorrectAnswer());
        assertEquals(instructions, createdQuestion.getInstructions());
    }

	//Description: checks the checkInputs method in the QuestionController class when validating an invalid question ID.
	//Input: id, number, question, subjectNum, lecturerId, ans1, ans2, ans3, ans4, correct answer, instructions, but set an invalid question ID ("22990").
	//Expected Result: an error message indicating that the question ID must be built by the subject number + question number.
    @Test
    public void checkInputsTest_InvalidId() {
        String id = "22990";
        String number = "101";
        String question = "Which logical operator represents the conjunction (AND) operation?";
        String subjectNum = "22";
        String lecturerId = "21234560";
        String ans1 = "OR";
        String ans2 = "AND";
        String ans3 = "NOR";
        String ans4 = "NOT";
        Integer correctAns = 2;
        String instructions = "Select the correct answer.";
        Object result = questionController.checkInputs(id, number, question, subjectNum, lecturerId, ans1, ans2, ans3, ans4, correctAns, instructions);

        assertTrue(result instanceof String);
        Object errorMessage1 = result;
        assertEquals(errorMessage1, "the question id must buit by the subject number + question number.\n");
    }
    
    //Description: checks the checkInputs method in the QuestionController class when validating an invalid question number.
  	//Input: id, number, question, subjectNum, lecturerId, ans1, ans2, ans3, ans4, correct answer, instructions, but set an invalid question number ("99").
  	//Expected Result: an error message indicating that the question number must be between 100 and 999
    @Test
    public void checkInputsTest_InvalidNumber() {
        String id = "2299";
        String number = "99";
        String question = "Which logical operator represents the conjunction (AND) operation?";
        String subjectNum = "22";
        String lecturerId = "21234560";
        String ans1 = "OR";
        String ans2 = "AND";
        String ans3 = "NOR";
        String ans4 = "NOT";
        Integer correctAns = 2;
        String instructions = "Select the correct answer.";
        Object result = questionController.checkInputs(id, number, question, subjectNum, lecturerId, ans1, ans2, ans3, ans4, correctAns, instructions);

        assertTrue(result instanceof String);
        Object errorMessage = result;
        assertEquals(errorMessage, "the question number must be between 100 and 999.\n");
    }
    
    //Description: checks the checkInputs method in the QuestionController class when validating an invalid question content.
  	//Input: id, number, question, subjectNum, lecturerId, ans1, ans2, ans3, ans4, correct answer, instructions, but set an empty question content.
  	//Expected Result: an error message indicating that the question content can't be empty.
    @Test
    public void checkInputsTest_InvalidQuestion() {
        String id = "22101";
        String number = "101";
        String question = "";
        String subjectNum = "22";
        String lecturerId = "21234560";
        String ans1 = "OR";
        String ans2 = "AND";
        String ans3 = "NOR";
        String ans4 = "NOT";
        Integer correctAns = 2;
        String instructions = "Select the correct answer.";
        Object result = questionController.checkInputs(id, number, question, subjectNum, lecturerId, ans1, ans2, ans3, ans4, correctAns, instructions);

        assertTrue(result instanceof String);
        Object errorMessage = result;
        assertEquals(errorMessage, "the question content can't be empty.\n");
    }
    
    //Description: checks the checkInputs method in the QuestionController class when validating an invalid subject number
  	//Input: id, number, question, subjectNum, lecturerId, ans1, ans2, ans3, ans4, correct answer, instructions, but set an invalid subject number ("100").
  	//Expected Result: an error message indicating that the subject number must be a number between 01 to 99.
    @Test
    public void checkInputsTest_InvalidSubject() {
        String id = "100101";
        String number = "101";
        String question = "Which logical operator represents the conjunction (AND) operation?";
        String subjectNum = "100";
        String lecturerId = "21234560";
        String ans1 = "OR";
        String ans2 = "AND";
        String ans3 = "NOR";
        String ans4 = "NOT";
        Integer correctAns = 2;
        String instructions = "Select the correct answer.";
        Object result = questionController.checkInputs(id, number, question, subjectNum, lecturerId, ans1, ans2, ans3, ans4, correctAns, instructions);

        assertTrue(result instanceof String);
        Object errorMessage = result;
        assertEquals(errorMessage, "the subject number must be a number between 01 to 99.\n");
    }

    //Description: checks the checkInputs method in the QuestionController class when validating invalid answer options.
  	//Input: id, number, question, subjectNum, lecturerId, ans1, ans2, ans3, ans4, correct answer, instructions, but set an empty answer option.
  	//Expected Result: an error message indicating that an empty answer is not allowed.
    @Test
    public void checkInputsTest_InvalidAnswers() {
        String id = "22101";
        String number = "101";
        String question = "Which logical operator represents the conjunction (AND) operation?";
        String subjectNum = "22";
        String lecturerId = "21234560";
        String ans1 = "OR";
        String ans2 = "AND";
        String ans3 = "";
        String ans4 = "NOT";
        Integer correctAns = 2;
        String instructions = "Select the correct answer.";
        Object result = questionController.checkInputs(id, number, question, subjectNum, lecturerId, ans1, ans2, ans3, ans4, correctAns, instructions);

        assertTrue(result instanceof String);
        Object errorMessage = result;
        assertEquals(errorMessage, "you can't enter an empty answer.\n");
    }
    
    //Description: checks the checkInputs method in the QuestionController class when validating an invalid correct answer.
  	//Input: id, number, question, subjectNum, lecturerId, ans1, ans2, ans3, ans4, correct answer, instructions, but set an invalid correct answer (5).
  	//Expected Result: an error message indicating that the correct answer must be a digit between 1 to 4.
    @Test
    public void checkInputsTest_InvalidCorrectAnswer() {
        String id = "22101";
        String number = "101";
        String question = "Which logical operator represents the conjunction (AND) operation?";
        String subjectNum = "22";
        String lecturerId = "21234560";
        String ans1 = "OR";
        String ans2 = "AND";
        String ans3 = "NOR";
        String ans4 = "NOT";
        Integer correctAns = 5;
        String instructions = "Select the correct answer.";
        Object result = questionController.checkInputs(id, number, question, subjectNum, lecturerId, ans1, ans2, ans3, ans4, correctAns, instructions);

        assertTrue(result instanceof String);
        Object errorMessage = result;
        assertEquals(errorMessage, "the correct answer must be a digit between 1 to 4.\n");
    }
    
    //Description: checks the checkInputs method in the QuestionController class when validating an invalid instructions string.
  	//Input: id, number, question, subjectNum, lecturerId, ans1, ans2, ans3, ans4, correct answer, instructions(length>128).
  	//Expected Result: an error message indicating that the instructions can be a string up to 128 characters.
    @Test
    public void checkInputsTest_InvalidInstructions() {
        String id = "22101";
        String number = "101";
        String question = "Which logical operator represents the conjunction (AND) operation?";
        String subjectNum = "22";
        String lecturerId = "21234560";
        String ans1 = "OR";
        String ans2 = "AND";
        String ans3 = "NOR";
        String ans4 = "NOT";
        Integer correctAns = 2;
        String instructions = "Select the correct answer.Select the correct answer.Select the correct answer.Select the correct answer.Select the correct answer.";
        Object result = questionController.checkInputs(id, number, question, subjectNum, lecturerId, ans1, ans2, ans3, ans4, correctAns, instructions);

        assertTrue(result instanceof String);
        Object errorMessage = result;
        assertEquals(errorMessage, "the instructions can be a string up to 128 characters.");
    }
    
    //Description: checks the checkInputs method in the QuestionController class when validating a null answer option.
  	//Input: id, number, question, subjectNum, lecturerId, ans1, ans2, ans3(null), ans4, correct answer, instructions.
  	//Expected Result: result = null. 
    @Test
    public void checkInputsTest_NullAnswer() {
    	Object result = null;
        String id = "22101";
        String number = "101";
        String question = "Which logical operator represents the conjunction (AND) operation?";
        String subjectNum = "22";
        String lecturerId = "21234560";
        String ans1 = "OR";
        String ans2 = "AND";
        String ans3 = null;
        String ans4 = "NOT";
        Integer correctAns = 2;
        String instructions = "Select the correct answer.";
        try {
        	result = questionController.checkInputs(id, number, question, subjectNum, lecturerId, ans1, ans2, ans3, ans4, correctAns, instructions);
        }catch(NullPointerException e) {fail("Exception");};
        assertNull(result);
    }
    
    //Description: checks the QuestionController class when validating a valid points sum.
  	//Input: ArrayList of valid questions with different point values - 30, 40, 30.
  	//Expected Result: same ArrayList of valid questions since the points sum is equal to 100.
    @Test
    public void checkPointsTest_ValidPointsSum() {
        ArrayList<Question> validQuestions = new ArrayList<>();
        validQuestions.add(new Question("22101", 101, "Which logical operator represents the conjunction (AND) operation?", "22", "21234560", "OR", "AND", "NOR", "NOT", 2, "Select the correct answer.", 30));
        validQuestions.add(new Question("22101", 101, "Which logical operator represents the bla (NOR) operation?", "22", "21234560", "OR", "AND", "NOR", "NOT", 3, "Select the correct answer.", 40));
        validQuestions.add(new Question("22101", 101, "Which logical operator represents the blabla (NOT) operation?", "22", "21234560", "OR", "AND", "NOR", "NOT", 4, "Select the correct answer.", 30));

        Object result = questionController.checkPoints(validQuestions);
        assertEquals(validQuestions, result);
    }
    
    //Description: checks the QuestionController class when validating a points sum that is less than 100.
  	//Input: ArrayList of valid questions with a points sum of 90 - 30, 30, 30.
  	//Expected Result: an error message indicating that the points sum must be 100.
    @Test
    public void checkPointsTest_PointsSumLessThan100() {
        ArrayList<Question> validQuestions = new ArrayList<>();
        validQuestions.add(new Question("22101", 101, "Which logical operator represents the conjunction (AND) operation?", "22", "21234560", "OR", "AND", "NOR", "NOT", 2, "Select the correct answer.", 30));
        validQuestions.add(new Question("22101", 101, "Which logical operator represents the bla (NOR) operation?", "22", "21234560", "OR", "AND", "NOR", "NOT", 3, "Select the correct answer.", 30));
        validQuestions.add(new Question("22101", 101, "Which logical operator represents the blabla (NOT) operation?", "22", "21234560", "OR", "AND", "NOR", "NOT", 4, "Select the correct answer.", 30));

        Object result = questionController.checkPoints(validQuestions);
        assertEquals("the points sum must be 100.", result);
    }
    
    //Description: checks the QuestionController class when validating a points sum that is greater than 100.
  	//Input: ArrayList of valid questions with a points sum of 110 - 40, 40, 30.
  	//Expected Result: an error message indicating that the points sum must be 100.
    @Test
    public void checkPointsTest_PointsSumGreaterThan100() {
        ArrayList<Question> validQuestions = new ArrayList<>();
        validQuestions.add(new Question("22101", 101, "Which logical operator represents the conjunction (AND) operation?", "22", "21234560", "OR", "AND", "NOR", "NOT", 2, "Select the correct answer.", 40));
        validQuestions.add(new Question("22101", 101, "Which logical operator represents the bla (NOR) operation?", "22", "21234560", "OR", "AND", "NOR", "NOT", 3, "Select the correct answer.", 40));
        validQuestions.add(new Question("22101", 101, "Which logical operator represents the blabla (NOT) operation?", "22", "21234560", "OR", "AND", "NOR", "NOT", 4, "Select the correct answer.", 30));

        Object result = questionController.checkPoints(validQuestions);
        assertEquals("the points sum must be 100.", result);
    }
    
    //Description: checks the QuestionController class when validating an empty list of questions.
  	//Input: an empty ArrayList of questions.
  	//Expected Result: result = null.
    @Test
    public void checkPointsTest_EmptyQuestionsList() { 
        ArrayList<Question> emptyQuestions = new ArrayList<>();
        Object result = questionController.checkPoints(emptyQuestions);
        assertEquals(null, result);
    }
    
    //Description: checks the QuestionController class when validating a null list of questions.
  	//Input: null value as the list of questions.
  	//Expected Result: result = null.
    @Test
    public void checkPointsTest_NullQuestionsList() { 
    	Object result = null;
    	try {
    		result = questionController.checkPoints(null);
        }catch(NullPointerException e) {fail("Exception");};
        assertNull(result);
    }
}
