package controllers;

import java.util.ArrayList;

import communication.Msg;
import communication.MsgType;
import enteties.Request;
import enteties.TestToExecute;
import enteties.User;

/**
 * Controller class for managing Request.
 */
public class RequestController {
	
    /**
     * creates and returns a Request object base on the data in a TestToExecute object.
     *
     * @param test The TestToExecute to create request by.
     * @param hod
     * @param lecturer
     * @return Object representing the Request if input are valid or error String if inputs are not valid.
     */
	public Object createRequestByTest(TestToExecute test, User hod, User lecturer) {
		String error = new String("");
    	try {
    		if(test.getTest().getDuration().equals(Integer.valueOf(test.getTextField().getText())))
    			error+="The new duration you enter is the same as the old one.\n";
    		if(Integer.valueOf(test.getTextField().getText())<1 || Integer.valueOf(test.getTextField().getText())>500)
				error+="The duration must be an integer between 1 and 500 (minutes).\n";
    	}catch(Exception e) {error+="The duration must be an integer between 1 and 500 (minutes).\n";}
    	if(test.getTextField1().getText().length()==0) error+="You must write an explanation for changing the duration.\n";
    	if(error.length()!=0) return error;
		Request request = new Request();
		request.setTestCode(test.getTestCode());
		request.setLecturerId(lecturer.getId());
		request.setHodId(hod.getId());
		request.setDuration(Integer.valueOf(test.getTextField().getText()));  
		request.setExplanation(test.getTextField1().getText());
		return request;
	}
	
    /**
     * creates and returns a Msg for inserting a TestToExecute to DB.
     *
     * @param request The Request to insert.
     * @return A Msg object representing the database insert message.
     */
	public Msg insertRequest(Request request) {
		Msg msg = new Msg(MsgType.insert);
		msg.setTableToUpdate("request");
		msg.setColNames("testCode, lecturerId, hodId, duration, explanation");
		ArrayList<Object> tmp = new ArrayList<>();
		tmp.add(request.getTestCode());
		tmp.add(request.getLecturerId());
		tmp.add(request.getHodId());
		tmp.add(request.getDuration());
		tmp.add(request.getExplanation());
		msg.setValues(tmp);
		return msg;
	}
	
	/**
     * Constructs a database select message to retrieve Request associated with many objects.
     * it includes the testToExecute object, the Test object inside it, the User object, and the Course object inside it.
     *
     * @param hod The User name String for whom to retrieve the TestToExecute.
     * @return A Msg object representing the database select message.
     */
	public Msg selectRequest(String hod) {
    	Msg msg = new Msg(MsgType.select);
    	msg.setSelect("request.*, test.*, user.*, course.*");
    	msg.setFrom("request");
    	msg.setFrom("testtoexecute");
    	msg.setFrom("test");
    	msg.setFrom("course");
    	msg.setFrom("user");
    	msg.setWhereCol("request.testCode", "testtoexecute.testCode");
    	msg.setWhereCol("testtoexecute.testId", "test.id");
    	msg.setWhereCol("request.lecturerId", "user.id");
    	msg.setWhereCol("test.courseNumber", "course.number"); 
    	msg.setWhere("request.hodId", hod); 
    	return msg;
    }
	
	/**
     * Constructs a database select message to retrieve Requests.
     *
     * @param hod The User ID String for whom to retrieve the request.
     * @return A Msg object representing the database select message.
     */
	public Msg selectFoundRequest(String hod) {
    	Msg msg = new Msg(MsgType.select);
    	msg.setSelect("request.*");
    	msg.setFrom("request"); 
    	msg.setWhere("request.hodId", hod); 
    	return msg;
    }
	
	/**
	 * Constructs a database update message to update a confirms duration change in the needed fields.
	 *
	 * @param req The Request.
	 * @return A Msg object representing the database select message.
	 */
	public Msg getMsgToUpdateRequestDuration(Request req) {
		Msg msgM = new Msg(MsgType.manyMessages);
		Msg msg1 = new Msg(MsgType.update);
		msg1.setTableToUpdate("test");
		msg1.setSet("duration", req.getDuration());
		msg1.setWhere("id", req.getTestId());
		
		Msg msg2 = new Msg(MsgType.update);
		msg2.setTableToUpdate("testtoexecute");
		msg2.setSet("timeExtenstion", 0);
		msg2.setWhere("testCode", req.getTestCode());
		
		Msg msg3= new Msg(MsgType.delete);
    	msg3.setDeleteFrom("request");
    	msg3.setWhere("testCode", req.getTestCode());
    	msg3.setWhere("lecturerId", req.getLecturerId());
    	
    	String popText = "Your request to change duration of test "+req.getTestCode()+" to "+ req.getDuration()+"min confirmed by your HOD.";
		Msg msg4 = new Msg(MsgType.pop);
		msg4.setUser(req.getUser());
		msg4.setPopText(popText);
		
		Msg msg5 = new Msg(MsgType.changeDuration);
		msg5.setTestCode(req.getTestCode());
		msg5.setDuration(req.getDuration());
    
		msgM.setMsgLst(msg1);
		msgM.setMsgLst(msg2);
		msgM.setMsgLst(msg3);
		msgM.setMsgLst(msg4);
		msgM.setMsgLst(msg5);
		return msgM;
	}
}