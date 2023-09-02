package unittests;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import communication.Msg;
import communication.MsgType;
import controllers.SubjectController;
import enteties.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SubjectControllerTest {

	SubjectController subjectController;
	
	@BeforeEach
	void setUpBeforeClass() throws Exception {
		subjectController = new SubjectController();
	}
	
	//Description: verifies the behavior of the getMsgForCourses method when the subject number is set("44").
	//Input: Course object = Course object with number 44.
	//Expected Result: Msg object should have the type MsgType.select, select all attributes from course table and have a WHERE condition
	//                 by the subject number got as input.
	@Test
    void getMsgForCoursesTest_SubjectNumberSet() {  
        Subject subject = new Subject();
        subject.setNumber("44");
        Msg result = subjectController.getMsgForCourses(subject);
        assertEquals(MsgType.select, result.getType());
        assertEquals("*", result.getSelect().get(0));
        assertEquals("course", result.getFrom().get(0));
        assertEquals("subjectNum", result.getWhere().keySet().iterator().next());
        assertEquals("44", result.getWhere().values().iterator().next());
    }
	
	//Description: verifies the behavior of the getMsgForCourses method when the subject number is not set(null).
	//Input: Course object = Course object with null number.
	//Expected Result: Msg object should be initialize but without a where section(null).
	@Test
    void getMsgForCoursesTest_SubjectNumberNotSet() {  
        Subject subject = new Subject();
        Msg result = subjectController.getMsgForCourses(subject);
        assertEquals(MsgType.select, result.getType());
        assertEquals("*", result.getSelect().get(0));
        assertEquals("course", result.getFrom().get(0));
        assertEquals(null, result.getWhere());
    }

	//Description: verifies the behavior of the getMsgForQuestions method when the course input is null.
	//Input: Course object = null.
	//Expected Result: Msg object should not be initialize and should return as null.
	@Test
    void getMsgForCoursesTest_SubjectInputIsNull() {  
        Subject subject = null;
        Msg result = null;
        try {
        	result = subjectController.getMsgForCourses(subject);
        }catch(Exception e) {fail("exception.");}
        assertEquals(null, result);
    }
	
	//Description: verifies the behavior of the getSubjectNames method when the input is legal list of Subject objects in size 2.
	//Input: ArrayList<Subject> with the names {"Algebra", Object Oriented"}.
	//Expected Result: ObservableList<String> = {"Algebra", Object Oriented"}.
	@Test
    void getSubjectNamesTest_legalSubjectListInput() {
		ArrayList<Subject> lst = new ArrayList<>();
		Subject sub1 = new Subject();
		sub1.setName("Algebra");
		Subject sub2 = new Subject();
		sub2.setName("Object Oriented");
		lst.add(sub1);
		lst.add(sub2);
		ObservableList<String> result = null;
		ArrayList<String> tmpLst = new ArrayList<>();
		tmpLst.add("Algebra");
		tmpLst.add("Object Oriented");
		ObservableList<String> expected = FXCollections.observableArrayList(tmpLst);
        try {result = subjectController.getSubjectNames(lst);
        }catch(Exception e) {fail("exception.");}
        assertEquals(expected, result);
    }
	
	//Description: verifies the behavior of the getSubjectNames method when the input is empty list of Subject objects.
	//Input: empty ArrayList<Subject> .
	//Expected Result: empty ObservableList<String>.
	@Test
    void getSubjectNamesTest_emptySubjectListInput() {
		ArrayList<Subject> lst = new ArrayList<>();
		ObservableList<String> result = null;
		ObservableList<String> expected = FXCollections.observableArrayList();
        try {
        	result = subjectController.getSubjectNames(lst);
        }catch(Exception e) {fail("exception.");}
        assertEquals(expected, result);
    }
	
	//Description: verifies the behavior of the getSubjectNames method when the input is list of Subject objects is null.
	//Input: ArrayList<Subject> = null.
	//Expected Result: null.
	@Test
    void getSubjectNamesTest_subjectListInputIsNull() {
		ObservableList<String> result = null;
        try {result = subjectController.getSubjectNames(null);
        }catch(Exception e) {fail("exception.");}
        assertNull(result);
    }
	
	//Description: verifies the behavior of the getSubjectNames method when the input is not empty list of Subject with null values.
	//Input: ArrayList<Subject> in size 2 and 2 null in it.
	//Expected Result: null.
	@Test
    void getSubjectNamesTest_subjectListInputWithNullObjects() {
		ArrayList<Subject> lst = new ArrayList<>();
		lst.add(null);
		lst.add(null);
		ObservableList<String> result = null;
		ObservableList<String> expected = null;
        try {
        	result = subjectController.getSubjectNames(lst);
        }catch(Exception e) {fail("exception.");}
        assertEquals(expected, result);
    }
	
	//Description: verifies the behavior of the getSubjectNames method when the input is list of Subject in size of 1,objects is null.
	//             but the name of the subject is null.
	//Input: ArrayList<Subject> in size 1 and name is null.
	//Expected Result: ObservableList<String> = {null}.
	@Test
    void getSubjectNamesTest_subjectListInputWithNullNames() {
		ArrayList<Subject> lst = new ArrayList<>();
		Subject sub1 = new Subject();
		sub1.setName(null);
		lst.add(sub1);
		ObservableList<String> result = null;
		ArrayList<String> tmpLst = new ArrayList<>();
		tmpLst.add(null);
		ObservableList<String> expected = FXCollections.observableArrayList(tmpLst);
        try {
        	result = subjectController.getSubjectNames(lst);
        }catch(Exception e) {fail("exception.");}
        assertEquals(expected, result);
    }
	
    // Description: verifies the behavior of the findSubjectByName method, searching for an existing subject in the list.
    // Input: subjectName = "Math", subjectsLst = [Subject("Physics"), Subject("Math"), Subject("Chemistry")].
    // Expected result: Subject("Math").
	@Test
    public void findSubjectByNameTest_existingSubjectInTheInputList() {
        ArrayList<Subject> subjectsLst = new ArrayList<>();
        Subject sub1 = new Subject();
        sub1.setName("Physics");
        subjectsLst.add(sub1);
        Subject sub2 = new Subject();
        sub2.setName("Math");
        subjectsLst.add(sub2);
        Subject sub3 = new Subject();
        sub3.setName("Chemistry");
        subjectsLst.add(sub3);
        Subject expected = sub2;
        Subject result = new Subject();
        try{ result = subjectController.findSubjectByName("Math", subjectsLst);
        }catch(Exception e) {fail("exception.");}
        assertEquals(expected.getName(), result.getName());
    }
	
    // Description: verifies the behavior of the findSubjectByName method, searching for a non-existing subject in the list.
    // Input: subjectName = "Software", subjectsLst = [Subject("Physics"), Subject("Math")).
    // Expected result: null.
	@Test
    public void findSubjectByNameTest_noneExistingSubjectInTheInputList() {
        ArrayList<Subject> subjectsLst = new ArrayList<>();
        Subject sub1 = new Subject();
        sub1.setName("Physics");
        subjectsLst.add(sub1);
        Subject sub2 = new Subject();
        sub2.setName("Math");
        subjectsLst.add(sub2);
        Subject result = null;
        try{ result = subjectController.findSubjectByName("Software", subjectsLst);
        }catch(Exception e) {fail("exception.");}
        assertNull(result);
    }
	
    // Description: verifies the behavior of the findSubjectByName method, searching in an empty subject list.
    // Input: subjectName = "Software", subjectsLst = {} empty.
    // Expected result: null.
	@Test
    public void findSubjectByNameTest_emptySubjectListInput() {
        ArrayList<Subject> subjectsLst = new ArrayList<>();
        Subject result = null;
        try{ result = subjectController.findSubjectByName("Software", subjectsLst);
        }catch(Exception e) {fail("exception.");}
        assertNull(result);
    }
	
    // Description: verifies the behavior of the findSubjectByName method, searching in an a null subject list.
    // Input: subjectName = "Software", subjectsLst = null.
    // Expected result: null.
	@Test
    public void findSubjectByNameTest_subjectListInputIsNull() {
        ArrayList<Subject> subjectsLst = null;
        Subject result = null;
        try{ result = subjectController.findSubjectByName("Software", subjectsLst);
        }catch(Exception e) {fail("exception.");}
        assertNull(result);
    }
	
    // Description: verifies the behavior of the findSubjectByName method, searching a null string in a legal subject list.
    // Input: subjectName = null, subjectsLst = [Subject("Physics"), Subject("Math")).
    // Expected result: null.
	@Test
    public void findSubjectByNameTest_StringInputIsNullAndLegalSubjectListInput() {
        ArrayList<Subject> subjectsLst = new ArrayList<>();
        Subject sub1 = new Subject();
        sub1.setName("Physics");
        subjectsLst.add(sub1);
        Subject sub2 = new Subject();
        sub2.setName("Math");
        subjectsLst.add(sub2);
        Subject result = null;
        try{ result = subjectController.findSubjectByName(null, subjectsLst);
        }catch(Exception e) {fail("exception.");}
        assertNull(result);
    }	
}