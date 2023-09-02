package unittests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    MsgTest.class,
	CourseControllerTest.class,
	QuestionControllerTest.class,
	SubjectControllerTest.class,
	TestControllerTest.class,
	TestToExecuteControllerTest.class,
	UserControllerTest.class,
	DB_controllerTest.class
    })

public class AllTests {
	
}