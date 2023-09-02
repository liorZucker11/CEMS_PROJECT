package controllers;

import java.util.ArrayList;

import communication.Msg;
import communication.MsgType;
import enteties.Question;
import enteties.Test;
import enteties.User;

/**
 * Controller class for managing Test.
 */
public class TestController {
	
	/**
	 * constructs a database select message to get the duration of he test.
	 * @param code of test the student take.
	 * @return msg representing select query.
	 */
	public Msg getDurationByCode(String code) {
		Msg msg = new Msg(MsgType.select);
		msg.setSelect("test.duration");
		msg.setFrom("test");
		msg.setFrom("testtoexecute");
		msg.setWhere("testtoexecute.testCode", code);
		msg.setWhereCol("testtoexecute.testId", "test.id");
		return msg;
	}

	/**
	 * Checks the inputs for creating a new test.
	 *
	 * @param id                    The ID of the test.
	 * @param number                The number of the test.
	 * @param courseNumber          The course number associated with the test.
	 * @param duration              The duration of the test.
	 * @param instructionsForStudent    The instructions for the student.
	 * @param instructionsForLecturer    The instructions for the lecturer.
	 * @return An Object representing the new Test if the inputs are valid, or a String with an error message if the inputs are invalid.
	 */
	public Object checkInputs(String id, String number, String courseNumber, String duration, String instructionsForStudent, String instructionsForLecturer) {
		String error = new String("");
		if(Integer.valueOf(id)>999999 || Integer.valueOf(id)<10101) error+= "id is not leagal.\n";
		if(Integer.valueOf(number)>99 || Integer.valueOf(number)<01) error+= "test number must be an integer between 01 and 99.\n";
		try { if(Integer.valueOf(duration)>500 || Integer.valueOf(number)<1) error+="the test duration must be between 1 and 500.\n";
		}catch(Exception e) {error+= "the test duration must be an integer between 1 and 500.\n";}
		if(instructionsForStudent.length()>2050) error+= "instructions for student can be a string up to 2056 characters.\n";
		if(instructionsForLecturer.length()>2050) error+= "instructions for lecturer can be a string up to 2056 characters.\n";
		if(error.length()!=0) return error;
		return new Test(id, number, courseNumber, Integer.valueOf(duration), instructionsForStudent, instructionsForLecturer);
	}

    /**
     * creates and returns a Msg for inserting a test to DB including lines for test_question.
     *
     * @param t The Test object to be inserted.
     * @param newTest_question 
     * @return Msg object representing the database insert message.
     */
	public Msg insertTest(Test t, ArrayList<Question> newTest_question) {
		Msg msgT = new Msg(MsgType.insert);
		msgT.setTableToUpdate("test");
		msgT.setColNames("id, number, courseNumber, duration, instructionsForStudent, instructionsForLecturer");
		ArrayList<Object> tmp = new ArrayList<>();
		tmp.add(t.getId());
		tmp.add(t.getNumber());
		tmp.add(t.getCourseNumber());
		tmp.add(t.getDuration());
		tmp.add(t.getInstructionsForStudent());
		tmp.add(t.getInstructionsForLecturer());
		msgT.setValues(tmp);
		
		Msg msgMany = new Msg(MsgType.manyMessages);
		msgMany.setMsgLst(msgT);
		
		for(Question q : newTest_question) {
			Msg msgTQ = new Msg(MsgType.insert);
			msgTQ.setTableToUpdate("test_question");
			msgTQ.setColNames("testId, questionId, points");
			tmp = new ArrayList<>();
			tmp.add(t.getId());
			tmp.add(q.getId());
			tmp.add(q.getPoints());
			msgTQ.setValues(tmp);
			msgMany.setMsgLst(msgTQ);
		}
		return msgMany;
	}

    /**
     * Constructs a database select message to retrieve tests associated with a user.
     *
     * @param user The User object for whom to retrieve the tests.
     * @return A Msg object representing the database select message.
     */
	public Msg selectTestByUser(User user) {
		Msg msg = new Msg(MsgType.select);
		msg.setSelect("test.*, course.*");
		msg.setFrom("test");
		msg.setFrom("user_subject");
		msg.setFrom("course");
		msg.setWhereCol("test.courseNumber", "course.number");
		msg.setWhereCol("user_subject.subjectNum", "course.subjectNum");
		msg.setWhere("user_subject.userId", user.getId());
		return msg;
	}
}