package gui;

import java.io.IOException;

import client.ChatClient;
import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * The ClientConnectionController class handles the client connection functionality.
 */
public class ClientConnectionController extends AbstractController{

    /**
     * The connect button.
     */
    @FXML
    private Button btnConnect;
    
    /**
     * The input field for the IP address.
     */
    @FXML
    private TextField inputIp;

    /**
     * The input field for the port number.
     */
    @FXML
    private TextField inputPort;
   
    /**
     * Handles the connect button click event.
     * Attempts to establish a connection to the specified IP address and port.
     * If successful, it starts the login screen.
     * If an error occurs, it prints an error message and terminates the client.
     *
     * @param event The action event triggered by the connect button.
     */
    @FXML
    void connect(ActionEvent event) {
        try {
            try {
                ClientUI.client = new ChatClient(inputIp.getText(), Integer.valueOf(inputPort.getText()));
            } catch (IOException exception) {
                System.out.println("Error: Can't setup connection! Terminating client.");
                System.exit(1);
            }  
            start("Login", "ClientConnection");
        } catch(Throwable t) {
            System.out.println("input ip and port - error connecting."); return;
        }    	
    }
}