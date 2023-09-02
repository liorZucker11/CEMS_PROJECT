package controllers;

import java.util.ArrayList;

import communication.Msg;
import communication.MsgType;
import enteties.StudentTest;
import enteties.TestToExecute;
import enteties.User;

public class StudentTestController {
	/**
	 * Creates a message object to update the student's answers, grade, and other
	 * details in the database for a manual test.
	 *
	 * @param approved   The approval status for the test.
	 * @param timePassed The time passed by the student to complete the test (in
	 *                   minutes).
	 * @param ansewrs    The answers provided by the student.
	 * @param grade      The grade obtained by the student.
	 * @param id         The ID of the student.
	 * @param code       The code of the test.
	 * @return The message object for updating the student's test details.
	 */
	public Msg InsertAnswersAndGradeManual(String approved, Integer timePassed, String ansewrs, Integer grade,String id, String code) {
		Msg msg = new Msg(MsgType.update);
		msg.setTableToUpdate("studenttest");
		msg.setSet("answers", ansewrs);
		msg.setSet("grade", grade);
		msg.setSet("timePassed", timePassed);
		msg.setSet("approved", approved);
		msg.setWhere("studentId", id);
		msg.setWhere("testCode", code);
		return msg;
	}

	/**
	 * constructs a database select message to check if user already did this test.
	 * 
	 * @param user
	 * @param code of test
	 * @return msg representing select query.
	 */
	public Msg studentAlreadyAccessed(User user, String code) {
		Msg msg = new Msg(MsgType.select);
		msg.setSelect("studenttest.studentId");
		msg.setFrom("studenttest");
		msg.setWhereCol("studentId", user.getId());
		msg.setWhere("testCode", code);
		return msg;

	}

	/**
	 * Constructs a database insert message to insert StudentTest.
	 *
	 * @param user The User object for whom to insert the Test..
	 * @param testToExecute
	 * @return Msg object representing the database insert message.
	 */
	public Msg insertStudentTest(TestToExecute testToExecute, User user) {
		Msg msg = new Msg(MsgType.insert);
		msg.setTableToUpdate("studenttest");
		msg.setColNames("studentId,testCode");
		ArrayList<Object> tmp = new ArrayList<Object>();
		tmp.add(user.getId());
		tmp.add(testToExecute.getTestCode());
		msg.setValues(tmp);
		return msg;
	}

	/**
	 * Constructs a database select message to retrieve StudentTest including test
	 * to execute course and test for the right student.
	 *
	 * @param user The User object for whom to retrieve the Test..
	 * @return A Msg object representing the database select message.
	 */
	public Msg getMsgForStudentTestsByID(User user) {
		Msg msg = new Msg(MsgType.select);
		msg.setSelect("studenttest.*,testtoexecute.*, test.*, course.*, user.name");
		msg.setFrom("studenttest");
		msg.setFrom("testtoexecute");
		msg.setFrom("test");
		msg.setFrom("course");
		msg.setFrom("user");
		msg.setWhereCol("testtoexecute.testId", "test.id");
		msg.setWhereCol("test.courseNumber", "course.number");
		msg.setWhereCol("studenttest.testCode", "testtoexecute.testCode");
		msg.setWhereCol("studenttest.studentId", "user.id");
		msg.setWhere("studenttest.studentId", user.getId());
		msg.setWhere("studenttest.approved", "true");
		return msg;
	}

	/**
	 * Constructs a database select message to retrieve StudentTest including
	 * studentName associated with a TestToExecute.
	 *
	 * @param t The TestToExecute object for whom to retrieve the StudentTest.
	 * @return A Msg object representing the database select message.
	 */
	public Msg getMsgForStudentTestsByTestToExecute(TestToExecute t) {
		Msg msg = new Msg(MsgType.select);
		msg.setSelect("studenttest.*, user.name");
		msg.setFrom("studenttest");
		msg.setFrom("user");
		msg.setWhereCol("studenttest.studentId", "user.id");
		msg.setWhere("testCode", t.getTestCode());
		msg.setWhere("approved", "false");
		return msg;
	}

	/**
	 * Constructs a database update message to update a StudentTest approved field,
	 * grade and lecturerNote.
	 *
	 * @param st The StudentTest object to be updated.
	 * @param student
	 * @return Msg object representing the database select message.
	 */
	public Msg getMsgToUpdateStudentTests(StudentTest st, User student) {
		Msg msg = new Msg(MsgType.update);
		msg.setTableToUpdate("studenttest");
		msg.setSet("approved", st.getApproved());
		msg.setSet("grade", st.getGrade());
		msg.setSet("lecturerNotes", st.getLecturerNotes());
		msg.setSet("changeReason", st.getChangeReason());
		msg.setWhere("studentId", st.getStudentId());
		msg.setWhere("testCode", st.getTestCode());
		
		String popText = "You got a new grade for test "+st.getTestCode()+".";
		Msg msg2 = new Msg(MsgType.pop);
		msg2.setUser(student);
		msg2.setPopText(popText);
		
		Msg many = new Msg(MsgType.manyMessages);
		many.setMsgLst(msg);
		many.setMsgLst(msg2);
		return many;
	}
	
	/**
	 * Creates a message object to select all answers names and id  of a student's code test.
	 *
	 * @param code The code of the test.
	 * @return A message object representing the select query.
	 */
	
	public Msg selectAllAnswersOfstudentCodeTest(Integer code){
		Msg msg=new Msg(MsgType.select);
		msg.setSelect("studenttest.answers");
		msg.setSelect("studenttest.studentId");
		msg.setSelect("user.`name`");
		msg.setSelect("studenttest.testCode");
		msg.setFrom("studenttest");
		msg.setFrom("`user`");
		msg.setWhere("testCode",code);
		msg.setWhereCol("studenttest.studentId","`user`.id");
		return msg;
	}

	/**
	 * Constructs a database select message to retrieve StudentTest.
	 *
	 * @param ID The ID for whom to retrieve the StudentTest.
	 * @return Msg object representing the database select message.
	 */
	public Msg getMsgForStudentTestsByID(String ID) {
		Msg msg = new Msg(MsgType.select);
		msg.setSelect("studenttest.*");
		msg.setFrom("studenttest");
		msg.setFrom("user");
		msg.setWhereCol("studenttest.studentId", "user.id");
		msg.setWhere("user.id", ID);
		return msg;
	}
	
	/**
	 * Creates a message to select all student tests with a specific test code.
	 *
	 * @param code The test code to filter the student tests.
	 * @return A Msg object configured for the select operation.
	 */
	public Msg selectAllstudentBySpecificCodeTest(String code) {
		Msg msg=new Msg(MsgType.select);
		msg.setSelect("studenttest.grade");
		msg.setFrom("studenttest");
		msg.setWhere("testCode",code);
		return msg;
	}
}