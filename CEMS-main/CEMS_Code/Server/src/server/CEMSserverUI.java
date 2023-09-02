package server;

import gui.ServerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The {@code CEMSserverUI} class is responsible for launching the server-side user interface.
 * It extends the {@link javafx.application.Application} class and provides the entry point
 * for the JavaFX application.
 */
public class CEMSserverUI extends Application {

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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ServerPort.fxml"));
		Parent root = loader.load();
		ServerController serverController = loader.getController();
		Scene scene = new Scene(root);
		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);

		primaryStage.setOnCloseRequest(event -> {
			event.consume(); // Prevent the default close action
			serverController.exit(null);
		});
		primaryStage.show();
	}
}