package unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import communication.Msg;
import communication.MsgType;
import controllers.CourseController;
import enteties.Course;

public class CourseControllerTest {

	CourseController courseController;
	
	@BeforeEach
	void setUpBeforeClass() throws Exception {
		courseController = new CourseController();
	}

	//Description: checks the getMsgForQuestions method when the course number is set("22").
	//Input: Course object with the number set to "22".
	//Expected Result: Msg object with type MsgType.select, select all attributes of a question, have two table names: "question_course" and "question", 
	//					condition that joins the two tables on "question_course.questionId" and "question.id", condition that filters by the course number "22".
	@Test
    void getMsgForQuestionsTest_CourseNumberSet() {  
        Course course = new Course();
        course.setNumber("22");
        Msg result = courseController.getMsgForQuestions(course);
        assertEquals(MsgType.select, result.getType());
        assertEquals("question.*", result.getSelect().get(0));
        assertEquals("question_course", result.getFrom().get(0));
        assertEquals("question", result.getFrom().get(1));
        assertEquals("question_course.questionId", result.getWhereCol().keySet().iterator().next());
        assertEquals("question.id", result.getWhereCol().values().iterator().next());
        assertEquals("courseNum", result.getWhere().keySet().iterator().next());
        assertEquals("22", result.getWhere().values().iterator().next());
    }
	
	//Description: checks the getMsgForQuestions method when the course number is not set.
	//Input: Course object with null number.
	//Expected Result: Msg object initialize but without a where section(null).
	@Test
    void getMsgForQuestionsTest_CourseNumberNotSet() {  
        Course course = new Course();
        Msg result = courseController.getMsgForQuestions(course);
        assertEquals(MsgType.select, result.getType());
        assertEquals("question.*", result.getSelect().get(0));
        assertEquals("question_course", result.getFrom().get(0));
        assertEquals("question", result.getFrom().get(1));
        assertEquals("question_course.questionId", result.getWhereCol().keySet().iterator().next());
        assertEquals("question.id", result.getWhereCol().values().iterator().next());
        assertEquals(null, result.getWhere());
    }
	
	//Description: checks the getMsgForQuestions method when the course input is null.
	//Input: Course object = null.
	//Expected Result: result = null.
	@Test
    void getMsgForQuestionsTest_CourseIsNull() {  
        Course course = null;
        Msg result = null;
        try {result = courseController.getMsgForQuestions(course);
        }catch(Exception e) {fail("exception.");}
        assertEquals(null, result);
    }	
	
	//Description: checks the findCourseByName method when the course exists in the course list.
	//Input: course name "Algebra" and a list of courses including a course with the name "Algebra".
	//Expected Result: Course object, with the number "12", name "Algebra", and subject number "23".
	@Test
    void FindCourseByNameTest_CourseExists() {
        ArrayList<Course> courseList = new ArrayList<>();
        courseList.add(new Course("11", "Logic" , "22"));
        courseList.add(new Course("12", "Algebra" , "23"));
        Course result = courseController.findCourseByName("Algebra", courseList);
        assertNotNull(result);
        assertEquals("12", result.getNumber());
        assertEquals("Algebra", result.getName());
        assertEquals("23", result.getSubjectNum());
    }
	
	//Description: checks the findCourseByName method when the course does not exist in the course list.
	//Input: course name "Algorithms" and a list of courses that does not include a course with the name "Algorithms".
	//Expected Result: result = null, indicating that the course was not found.
	@Test
    void FindCourseByNameTest_CourseNotExists() {
        ArrayList<Course> courseList = new ArrayList<>();
        courseList.add(new Course("11", "Logic" , "22"));
        courseList.add(new Course("12", "Algebra" , "23"));
        Course result = courseController.findCourseByName("Algorithms", courseList);
        assertNull(result);
    }
	
	//Description: checks the findCourseByName method when the course list is null.
	//Input: course name "Algorithms" and a null course list.
	//Expected Result: result = null.
	@Test
    void FindCourseByNameTest_CourseListNull() {
		Course result = null;
        try {
        	result = courseController.findCourseByName("Algorithms", null);
		}catch(NullPointerException e) {fail("Exception");};
		assertNull(result);
    }
	
	//Description: checks the findCourseByName method when the course name is null.
	//Input: null course name and a list of courses.
	//Expected Result: result = null.
	@Test
    void FindCourseByNameTest_CourseNameNull() {
		ArrayList<Course> courseList = new ArrayList<>();
        courseList.add(new Course("11", "Logic" , "22"));
        courseList.add(new Course("12", "Algebra" , "23"));
        Course result = null;
        try {
        	result = courseController.findCourseByName(null, courseList);
		}catch(NullPointerException e) {fail("Exception");};
		assertNull(result);
		
    }
	
	//Description: checks the findCourseByName method when the course list is empty.
	//Input: course name "Algorithms" and an empty course list.
	//Expected Result: result = null, indicating that the course was not found.
	@Test
	void FindCourseByNameTest_EmptyCourseList() {
	    ArrayList<Course> courseList = new ArrayList<>();
	    Course result = courseController.findCourseByName("Algorithms", courseList);
	    assertNull(result);
	}
	
	//Description: checks the findCourseByName method when the course name search is case-sensitive.
	//Input: course name "logic" (with a lowercase 'l') and a list of courses with a course named "Logic" (with an uppercase 'L').
	//Expected Result: result = null, indicating that the course was not found due to case sensitivity.
	@Test
	void FindCourseByNameTest_CaseSensitive() {
	    ArrayList<Course> courseList = new ArrayList<>();
	    courseList.add(new Course("11", "Logic", "22"));
	    Course result = courseController.findCourseByName("logic", courseList);
	    assertNull(result);
	}
}
