package controllers;

import java.util.ArrayList;

import communication.Msg;
import communication.MsgType;
import enteties.Course;
import enteties.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Controller class for managing course.
 */
public class CourseController {

    /**
     * Retrieves the names of the courses as an ObservableList.
     * 
     * @param coursesLst The list of the courses search in.
     * @return The ObservableList of course names.
     */
    public ObservableList<String> getCourseNames(ArrayList<Course> coursesLst) {
        ObservableList<String> couseNames = FXCollections.observableArrayList();
        for (Course course : coursesLst) {
        	couseNames.add(course.getName());
        }
        return couseNames;
    }
    
    /**
     * Finds a course by its name.
     *
     * @param courseName The name of the course to find.
     * @param courseLst The list of the courses search in.
     * @return The Course object with the specified name, or null if not found.
     */
    public Course findCourseByName(String courseName, ArrayList<Course> courseLst) {
        for (Course course : courseLst) {
            if (course.getName().equals(courseName)) {
                return course;
            }
        }
        return null; // Subject not found
    }

    /**
     * Constructs a database select message to retrieve questions associated with a course.
     *
     * @param course The Course object for whom to retrieve the questions.
     * @return A Msg object representing the database select message.
     */
	public Msg getMsgForQuestions(Course course) {
		Msg msg = new Msg(MsgType.select);
		msg.setSelect("question.*");
		msg.setFrom("question_course");
		msg.setFrom("question");
		msg.setWhereCol("question_course.questionId" ,"question.id");
		msg.setWhere("courseNum" ,course.getNumber());
		return msg;
	}	
	
    /**
     * Constructs a database select message to retrieve courses associated with a user.
     *
     * @param user The User object for whom to retrieve the courses.
     * @return A Msg object representing the database select message.
     */
	public Msg selectCourseByUser(User user) {
    	Msg msg = new Msg(MsgType.select);
    	msg.setSelect("course.number, course.name, course.subjectNum");
    	msg.setFrom("course");
    	msg.setFrom("user_subject");
    	msg.setWhereCol("user_subject.subjectNum", "course.subjectNum"); 
    	msg.setWhere("user_subject.userId", user.getId()); 
    	return msg;
    }
}