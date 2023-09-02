package gui;

import java.util.ArrayList;
import java.util.Collections;

//import JDBC.MsgType;
import client.ChatClient;
import communication.Msg;
import enteties.Course;
import enteties.StudentTest;
import enteties.TestToExecute;
import enteties.User;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Label;

/**
 * Controller class for the Lecturer Statistics Report screen.
 * Generates statistics reports for lecturers, students, and courses.
 * Displays the average and median grades in a BarChart.
 * Calculates and displays the distribution of grades.
 * Handles data retrieval from the database and populates the BarChart.
 * Supports different types of reports based on the chosen report type.
 * Supports generating reports for lecturers, students, and courses.
 * 
 * @author Mor Shmuel 
 */
public class LecturerStaticsReportController extends AbstractController{

	/**
	 * Selected lecturer or the student users for the report.
	 */
	public static User selectedLecturer, selectedStudent;
	
	/**
	 * Selected course for the report.
	 */
	public static Course selectedCourse;
	
	/**
	 * Selected test to execute for the report.
	 */
	public static TestToExecute selectedTest;
	
	/**
	 * labels of the screen.
	 */
	@FXML
    private Label avg, median, finishTest, notFinishTest, startTest, subTitle;
	
	/**
	 * barChart for the report.
	 */
	@FXML
	private BarChart<String, Integer> barChart;
    
    /**
	 * the list of StudentTest for the BarChart according to the tests of the student.
	 */
    private ArrayList<StudentTest> studenntTestLst;
    
    /**
	 * the list of TestToExecute for the BarChart according to the tests to execute of the user.
	 */
    private ArrayList<TestToExecute> testToExecuteLst;
    
    /**
	 * boolean flags for knowing what type of report is needed.
	 */
    private boolean testToexec=false, studentTest=false, courseFlag=false, testFlag=false;
	
    /**
     * Default constructor for the LecturerStaticsReportController class.
     * Initializes the data for the BarChart.
     */
    public LecturerStaticsReportController() {
        if (ChatClient.lastCurrentScreen instanceof ChooseReportTypeController) {
            switch (((ChooseReportTypeController) ChatClient.lastCurrentScreen).getReportType()) {
                case "lecturer":
                    User selectedLecturer = ((ChooseReportTypeController) ChatClient.lastCurrentScreen).getSelectedLecturer();
                    try {
                        Msg msg1 = testToExecuteController.selectTestToExecuteByLecturer(selectedLecturer.getId());
                        
                        sendMsg(msg1);
                        if (msgReceived != null)
                        	 testToExecuteLst = msgReceived.convertData(TestToExecute.class);
                       
                      
                        if(testToExecuteLst==null) {notification.showErrorAlert("There are no tests to see."); return;}
                        testToexec = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    break;
                case "student":
                    User selectedStudent = ((ChooseReportTypeController) ChatClient.lastCurrentScreen).getSelectedStudent();
                    try {
                        Msg msg2 = studentTestController.getMsgForStudentTestsByID(selectedStudent.getId());
                        sendMsg(msg2);
                        if (msgReceived != null)
                        	studenntTestLst = msgReceived.convertData(StudentTest.class);
                        if(studenntTestLst==null) {notification.showErrorAlert("There are no tests to see."); return;}
                        studentTest = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "course":
                    Course selectedCourse = ((ChooseReportTypeController) ChatClient.lastCurrentScreen).getSelectedCourse();
                    try {
                        Msg msg3 = testToExecuteController.selectTestToExecuteByCourseName(selectedCourse.getName());
                        sendMsg(msg3);
                        if (msgReceived != null)
                        	testToExecuteLst = msgReceived.convertData(TestToExecute.class);
                        if(testToExecuteLst==null) {notification.showErrorAlert("There are no tests to see."); return;}
                        testToexec = true;
                        courseFlag = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        else if(ChatClient.lastCurrentScreen instanceof LecturerTestViewController) {
        	TestToExecute selectedTest = ((LecturerTestViewController) ChatClient.lastCurrentScreen).getSelectedTest();
        	testFlag = true;
        	System.out.println("selectedTest= " + selectedTest);
        }
    }
    
    /**
     * Initializes the controller after its root element has been completely processed.
     * Calculates the average, median, and grade distribution based on the selected report type and the retrieved data.
     * Populates the BarChart with the calculated data.
     */
	@SuppressWarnings("unchecked")
	@FXML
	protected void initialize() {
		double avgByType=0, medianByType=0;
    	
		//Create series for the BarChart
	    XYChart.Series<String, Integer> series1 = new XYChart.Series<>(); //red
	    XYChart.Series<String, Integer> series2 = new XYChart.Series<>(); //orange
	    XYChart.Series<String, Integer> series3 = new XYChart.Series<>(); //green
	    
	    int[] distributionByType = new int[10];
	    if(testToexec) {
	    	if(courseFlag) {
	    		subTitle.setText("By Course: " + selectedCourse.getName());
	    	}else { subTitle.setText("By Lecturer: " + selectedLecturer.getName());}
	    	distributionByType = getDistributionForTestToExecute();
	    	avgByType = getAvgForTestToExecute();
	    	medianByType = getMedianForTestToExecute();
	    } else if(studentTest) {
	    	subTitle.setText("By Student: " + selectedStudent.getName());
	    	distributionByType = getDistributionForStudentTest();
	    	avgByType = getAvgForStudentTest();
	    	medianByType = getMedianForStudentTest();
	    } else if(testFlag) {
	    	subTitle.setText("By Test: " + selectedTest.getCourseName() + " - " + selectedTest.getTestId());
	    	distributionByType = getDistributionForOneTestToExecute();
	    	avgByType = selectedTest.getAverage();
	    	medianByType = selectedTest.getMedian();
	    }
	    
	    avg.setText(String.format("%.2f", avgByType));
	    median.setText(String.format("%.2f", medianByType));
	    

	    //Add the data points to the series
	    for (int i = 0; i < 10; i++) {
            if (i >= 0 && i<5) {
            	String label = getLabelForIndex(i); //Replace with your label for each index
                series1.getData().add(new Data<>(label, distributionByType[i]));
            } else if (i >=5  && i<8){
                String label = getLabelForIndex(i); //Replace with your label for each index
                series2.getData().add(new Data<>(label, distributionByType[i]));
            } else {
            	String label = getLabelForIndex(i); //Replace with your label for each index
                series3.getData().add(new Data<>(label, distributionByType[i]));
            }
	    }

	    //Add the series to the BarChart
	    barChart.getData().addAll(series1,series2,series3);
	    barChart.setBarGap(0); //Adjust the value to make the bars thicker
	    barChart.setCategoryGap(0);
	    barChart.setLegendVisible(false);
	}
	
	/**
     * Calculates the grade distribution for TestToExecute.
     *
     * @return an array representing the grade distribution
     */
	private int[] getDistributionForTestToExecute() {
		// Initialize the distribution array
	    int[] distribution = new int[10];
	    int start=0, finish=0, notFinish=0;

	    //Iterate over the testToExecuteLst and populate the distribution array
	    for (TestToExecute test : testToExecuteLst) {
	    	start += test.getNumberOfStudentsStarted();
	    	finish += test.getNumberOfStudentsFinished();
	    	//notFinish += test.getNumberOfStudents();
	    	Integer[] distributionPerTest = test.getDistribusion();
	    	for(int i=0; i<distribution.length; i++) {
	    		distribution[i]+=distributionPerTest[i];
	    	}
	    }
	    startTest.setText("Number of students who started the test: " + start);
	    finishTest.setText("Number of students who finished the test: " + finish);
	    notFinishTest.setText("Number of students who did not finish the test on time: " + notFinish);
		return distribution;
	}
	
	/**
     * Calculates the grade distribution for one TestToExecute.
     *
     * @return an array representing the grade distribution
     */
	private int[] getDistributionForOneTestToExecute() {
		// Initialize the distribution array
	    int[] distribution = new int[10];
	    int start=0, finish=0, notFinish=0;
	    start = selectedTest.getNumberOfStudentsStarted();
	    finish = selectedTest.getNumberOfStudentsFinished();
	    notFinish = selectedTest.getNumberOfStudents();
	    Integer[] distributionTest = selectedTest.getDistribusion();
	    for(int i=0; i<distribution.length; i++) {
	    	distribution[i]+=distributionTest[i];
    	}
	    startTest.setText("Number of students who started the test: " + start);
	    finishTest.setText("Number of students who finished the test: " + finish);
	    notFinishTest.setText("Number of students who did not finish the test on time: " + notFinish);
	    return distribution;
	}
	
	/**
     * Calculates the grade distribution for StudentTest.
     *
     * @return an array representing the grade distribution
     */
	private int[] getDistributionForStudentTest() {
		// Initialize the distribution array
	    int[] distribution = new int[10];
	    
	    //Iterate over the StudentTest and populate the Grades
	    for (StudentTest grade : studenntTestLst) {
	    	Integer distributionPerGrade = grade.getGrade();
	    	int index = (distributionPerGrade/10)-1;
	    	if(index<0) {
	    		index=0;
	    	}
	    	distribution[index]++;
	    }
	    return distribution;
	}
	
	/**
     * Returns a label for a specific index in the grade distribution.
     *
     * @param index the index in the grade distribution array
     * @return a label for the index
     */
	private String getLabelForIndex(int index) {
	    int lowerBound = index * 10;
	    int upperBound = (index * 10) + 10;
	    return lowerBound + "-" + upperBound;
	}
	
	/**
     * Calculates the average grade for TestToExecute.
     *
     * @return the average grade
     */
	private double getAvgForTestToExecute() {
	    double avg = 0;
	    int cnt=0;
	    //Iterate over the testToExecuteLst and populate the avg
	    for (TestToExecute test : testToExecuteLst) {
	    	avg += test.getAverage();
	    	cnt++;
	    }
		return (avg/cnt);
	}
	
	/**
     * Calculates the average grade for StudentTest.
     *
     * @return the average grade
     */
	private double getAvgForStudentTest() {
		double avg = 0;
	    int cnt=0;
	    //Iterate over the StudentTest and populate the Grades
	    for (StudentTest grade : studenntTestLst) {
	    	avg += grade.getGrade();
	    	cnt++;
	    }
	    return (avg/cnt);
	}
	
	/**
     * Calculates the median grade for TestToExecute.
     *
     * @return the median grade
     */
	private double getMedianForTestToExecute() {
		ArrayList<Double> medians = new ArrayList<>();
	    //Iterate over the StudentTest and populate the Grades
	    for (TestToExecute median : testToExecuteLst) {
	    	medians.add(median.getMedian());
	    }
	    //Sort the ArrayList in ascending order
        Collections.sort(medians);;
        //Calculate the median
        double median;
        int size = medians.size();
        if (size % 2 == 0) {
            //ArrayList size is even
            double middle1 = medians.get(size / 2 - 1);
            double middle2 = medians.get(size / 2);
            median = (middle1 + middle2) / 2.0;
        } else {
            //ArrayList size is odd
            median = medians.get(size / 2);
        }
	    return median;
	}
	
	/**
     * Calculates the median grade for StudentTest.
     *
     * @return the median grade
     */
	private double getMedianForStudentTest() {
		ArrayList<Integer> grades = new ArrayList<>();
	    //Iterate over the StudentTest and populate the Grades
	    for (StudentTest grade : studenntTestLst) {
	    	grades.add(grade.getGrade());
	    }
	    //Sort the ArrayList in ascending order
        Collections.sort(grades);
        //Calculate the median
        double median;
        int size = grades.size();
        if (size % 2 == 0) {
            //ArrayList size is even
            int middle1 = grades.get(size / 2 - 1);
            int middle2 = grades.get(size / 2);
            median = (middle1 + middle2) / 2.0;
        } else {
            //ArrayList size is odd
            median = grades.get(size / 2);
        }
        return median;
	}
}