package gui;

import client.ChatClient;
import client.ClientUI;
import communication.Msg;
import communication.MsgType;
import controllers.CourseController;
import controllers.QuestionController;
import controllers.RequestController;
import controllers.StudentTestController;
import controllers.SubjectController;
import controllers.TestController;
import controllers.TestToExecuteController;
import controllers.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import notifications.NotificationAlertsController;

/**
 * The AbstractController class is an abstract class that serves as a base class for other controller classes in the GUI package.
 * It implements the SceneSetter interface and provides common methods and fields used by the controllers.
 */
public abstract class AbstractController implements SceneSetter {
	private static Stage primaryStage;
	public static Msg msgReceived;
	private Scene scene;
	private String fxmlName;
	public String prevScreen;
	
    /**
	 * object to use the UserController class method.
	 */
    public static UserController userController = new UserController();
    /**
	 * object to use the CourseController class method.
	 */
    public static CourseController courseController = new CourseController();
    /**
	 * object to use the SubjectController class method.
	 */
    public static SubjectController subjectController = new SubjectController();
    /**
	 * object to use the RequestController class method.
	 */
    public static RequestController requestController = new RequestController();
    /**
	 * object to use the TestController class method.
	 */
    public static TestController testController = new TestController();
    /**
	 * object to use the StudentTestController class method.
	 */
    public static StudentTestController studentTestController = new StudentTestController();
    /**
	 * object to use the TestToExecuteController class method.
	 */
    public static TestToExecuteController testToExecuteController = new TestToExecuteController();
    /**
	 * object to use the QuestionController class method.
	 */
    public static QuestionController questionController = new QuestionController();
    /**
	 * object to use the notifications class.
	 */
    public static NotificationAlertsController notification = new NotificationAlertsController();

    /**
     * Starts the controller by setting the FXML name and previous screen, and loading the FXML file.
     * 
     * @param fxmlName    the name of the FXML file
     * @param prevScreen  the name of the previous screen
     * @throws Exception if an error occurs during the loading of the FXML file
     */
	public void start(String fxmlName, String prevScreen) throws Exception {
		this.fxmlName = convertToSentence(fxmlName);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + fxmlName + ".fxml"));
		Parent root = loader.load();
		ChatClient.screens.put(fxmlName, loader.getController());
		Scene tmpScene = new Scene(root);
		((SceneSetter)loader.getController()).setScene(tmpScene);
		((SceneSetter)loader.getController()).setPrevScreen(prevScreen);
		ChatClient.lastCurrentScreen = loader.getController();
		primaryStage.setTitle(convertToSentence(fxmlName));
		primaryStage.setScene(tmpScene);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

	/**
	 * Sets the name of the previous screen.
	 * 
	 * @param prevScreen the name of the previous screen
	 */
	public void setPrevScreen(String prevScreen) {
		this.prevScreen = prevScreen;
	}

	/**
	 * Sets the scene for the controller.
	 * 
	 * @param scene the scene to be set
	 */
	public void setScene(Scene scene) {
		this.scene = scene;
	}

	/**
	 * Sends a message to the server using the ChatClient.
	 * 
	 * @param msg the message to be sent
	 */
	public static void sendMsg(Msg msg) {
		ClientUI.client.handleMessageFromClientUI(msg);
	}

	/**
	 * Sets the primary stage for the application.
	 * 
	 * @param primaryStage the primary stage to be set
	 */
	public static void setPrimaryStage(Stage primaryStage) {
		AbstractController.primaryStage = primaryStage;
	}

	/**
	 * Displays the scene on the primary stage.
	 */
	public void display() {
		primaryStage.setTitle(fxmlName);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Handles the action event when the exit button is clicked.
	 * It performs the logout and disconnect operations before exiting the application.
	 * 
	 * @param event the action event
	 * @throws Exception if an error occurs during the logout operation
	 */
	public void exitBtn(ActionEvent event) throws Exception {
		try {
			logout();
			sendMsg(new Msg(MsgType.disconnect));
		} catch (Throwable t) {
			System.out.println("error getExitBtn");
			System.exit(0);
		}
		System.exit(0); // exit
	}
	/**
	 * Retrieves the name of the FXML file.
	 * 
	 * @return the name of the FXML file
	 */
	public String getFxmlName() {
		return fxmlName;
	}
	
	/**
	 * Retrieves the primary stage.
	 * 
	 * @return the primary stage
	 */
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	/**
	 * Retrieves the received message.
	 * 
	 * @return the received message
	 */
	public static Msg getMsgReceived() {
		return msgReceived;
	}

	/**
	 * Sets the received message.
	 * 
	 * @param msgReceived the received message to be set
	 */
	public static void setDataReceived(Msg msgReceived) {
		AbstractController.msgReceived = msgReceived;
	}
	
	/**
	 * Handles the action event when the back button is clicked.
	 * It hides the current window and displays the previous screen.
	 * 
	 * @param event the action event
	 * @throws Exception if an error occurs during the display of the previous screen
	 */
	public void backBtn(ActionEvent event) throws Exception {
		((Node)event.getSource()).getScene().getWindow().hide();
		ChatClient.getScreen(prevScreen).display();
		ChatClient.lastCurrentScreen = ChatClient.getScreen(prevScreen);
		primaryStage.setTitle(convertToSentence(prevScreen));
	}
	
	/**
	 * Performs the logout operation by sending a loggedin message with status "no" to the server.
	 * 
	 * @throws Exception if an error occurs during the logout operation
	 */
	public void logout() throws Exception {
		if(ChatClient.user==null) return;
    	sendMsg(userController.getLoggedinMsg(ChatClient.user, "no"));
	}
	
	/**
	 * Converts a string in the format "thisIsTheStringNeedToBeConvertedToSentence" to a sentence format.
	 *
	 * @param input the input string to be converted
	 * @return the converted string in sentence format
	 */
	private static String convertToSentence(String input) {
        StringBuilder output = new StringBuilder();
        // Convert the first character to uppercase
        output.append(Character.toUpperCase(input.charAt(0)));
        // Iterate through the remaining characters
        for (int i = 1; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            // Check if the character is an uppercase letter
            if (Character.isUpperCase(currentChar)) {
                // Add a space before the uppercase letter
                output.append(' ');
            }
            // Add the current character to the output
            output.append(currentChar);
        }
        // Return the converted string
        return output.toString();
    }
	
	/**
	 * Displays a popup message with the given message.
	 * 
	 * @param msg the message to be displayed
	 */
	public void popMessage(String msg) {
		notification.showInformationAlert(msg);
	}
}