package gui;

import java.util.ArrayList;

import enteties.StudentTest;
import enteties.TestToExecute;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * Controller class for the Show Grade screen.
 * Handles the interaction between the UI and the underlying logic for
 * displaying student grades.
 */
public class ShowGradeController extends AbstractController implements Tests {
	/**
	 * save the test to shown.
	 */
	private ArrayList<StudentTest> allTest;
	/**
	 * ObservableList of grades for the table.
	 */
	private ObservableList<StudentTest> gradesTable;
	/**
	 * the columns for the table from type String.
	 */
	@FXML
	private TableColumn<StudentTest, String> CommentCol, DateCol, GradeCol, NameCourseCol, ShowCol;
	/**
	 * the columns for the table from type Integer.
	 */
	@FXML
	private TableColumn<StudentTest, Integer> codeTestCol;
	/**
	 * the table with all the grade and tests after approve.
	 */
	@FXML
	private TableView<StudentTest> table;

	StudentTest StudentTestToShow;

	/**
	 * constructor Creating show buttons and insert information into TableView.
	 */
	public ShowGradeController() {
		allTest = msgReceived.convertData(StudentTest.class);
		for (StudentTest Grade : allTest) {
			Grade.setNewShow();
			Grade.getShow().setOnMouseClicked(event -> {
				try {
					showTestOpen(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
		gradesTable = FXCollections.observableArrayList(allTest);

	}

	/**
	 * Initializes the controller after its root element has been completely
	 * processed. set the table and populates the necessary data.
	 */

	@FXML
	protected void initialize() {
		CommentCol.setCellValueFactory(new PropertyValueFactory<StudentTest, String>("allNotes"));
		GradeCol.setCellValueFactory(new PropertyValueFactory<StudentTest, String>("grade"));
		ShowCol.setCellValueFactory(new PropertyValueFactory<StudentTest, String>("show"));
		System.out.println(codeTestCol);
		codeTestCol
				.setCellValueFactory(new Callback<CellDataFeatures<StudentTest, Integer>, ObservableValue<Integer>>() {
					@Override
					public ObservableValue<Integer> call(CellDataFeatures<StudentTest, Integer> param) {
						Integer value = param.getValue().getTestToExecute().getTestCode(); // Assuming getDate() returns
																							// an Integer
						return new SimpleIntegerProperty(value).asObject();
					}
				});
		NameCourseCol.setCellValueFactory(new Callback<CellDataFeatures<StudentTest, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<StudentTest, String> param) {
						return new SimpleStringProperty(param.getValue().getTestToExecute().getCourseName());
					}
				});
		DateCol.setCellValueFactory(new Callback<CellDataFeatures<StudentTest, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<StudentTest, String> param) {
				return new SimpleStringProperty(param.getValue().getTestToExecute().getDate());
			}
		});
		table.setItems(gradesTable);
		table.refresh();
	}

	/**
	 * Shown the right test in a separate window.
	 * 
	 * @param event click on show.
	 * @throws Exception
	 */
	@FXML
	private void showTestOpen(MouseEvent event) throws Exception {
		Button clickedButton = (Button) event.getSource(); // get the button that has been clicked
		for (StudentTest studentTest : allTest) // search for the studentTest.
			if (studentTest.getShow().equals(clickedButton)) {
				StudentTestToShow = studentTest;
				start("ShowStudentTest", "ShowGrade");
				break;
			}
	}

	/**
	 * Returns the TestToExecute object to be shown.
	 *
	 * @return The TestToExecute object to be shown.
	 */
	@Override
	public StudentTest getStudentTestToShow() {
		return StudentTestToShow;
	}

	@Override
	public TestToExecute getTestToExecuteToShow() {
		return null;
	}

	@Override
	public String getScreenState() {
		return "studentShowTest";
	}
}