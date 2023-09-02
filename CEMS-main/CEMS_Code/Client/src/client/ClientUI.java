package client;

import gui.AbstractController;
import gui.ClientConnectionController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The {@code ClientUI} class is responsible for launching the client-side user interface.
 * It extends the {@link javafx.application.Application} class and provides the entry point
 * for the JavaFX application.
 */
public class ClientUI extends Application {
	
	/**
	 * for the singleton design pattern.
	 */
	public static ChatClient client;

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args the command-line arguments
     * @throws Exception if an error occurs during application launch
     */
	public static void main(String args[]) throws Exception {
		launch(args);
	}

    /**
     * Starts the JavaFX application and initializes the primary stage.
     *
     * @param primaryStage the primary stage for the application
     * @throws Exception if an error occurs during application startup
     */
	@Override
	public void start(Stage primaryStage) throws Exception {
		AbstractController.setPrimaryStage(primaryStage);

		// original line:
		new ClientConnectionController().start("ClientConnection", null);

		primaryStage.setOnCloseRequest(event -> { // Prevent the default close action
			event.consume();
			try {
				ChatClient.getScreen("ClientConnection").exitBtn(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Exit Client app.");
			System.exit(0);
		});
	}
}