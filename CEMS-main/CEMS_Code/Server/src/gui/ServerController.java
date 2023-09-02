package gui;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;

import communication.Msg;
import communication.MsgType;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import notifications.NotificationAlertsController;
import ocsf.server.ConnectionToClient;
import server.CEMSserver;

/**
 * The ServerController class controls the GUI and handles events for the server application.
 */
public class ServerController {
	/**
	 * the observable list for the table of connected clients.
	 */
	private ObservableList<ConnectionToClient> connectedObserv = FXCollections.observableArrayList();
	/**
	 * for the singleton design pattern
	 */
	private CEMSserver cemsServer;
	/**
	 * input field.
	 */
	@FXML
	private TextField DBNameTxt;
	/**
	 * input field.
	 */
	@FXML
	private TextField DBUsernameTxt;
	/**
	 * input field.
	 */
	@FXML
	private Button btnConnect;
	/**
	 * button field.
	 */
	@FXML
	public Button btnDisconnect;
	/**
	 * the table of connected clients.
	 */
	@FXML
	private TableView<ConnectionToClient> clientsTable;
	/**
	 * the columns of the table of connected clients.
	 */
	@FXML
	private TableColumn<ConnectionToClient, String> clientHostName, clientIp, clientStatus;
	/**
	 * server console.
	 */
	@FXML
	private TextArea console;
	/**
	 * input field.
	 */
	@FXML
	private PasswordField passwordTxt;
	/**
	 * input field.
	 */
	@FXML
	private TextField portxt;
	/**
	 * input field.
	 */
	@FXML
	private TextField serverIdTxt;
	/**
	 * input field.
	 */
	@FXML
    private TextField userFilePath;
	/**
	 * input field.
	 */
	@FXML
	private TextField folderTxt;
	/**
	 * object to use the notifications class.
	 */
    private static NotificationAlertsController notification = new NotificationAlertsController();
	
	
	/**
     * Handles the event when the "Connect" button is clicked.
     *
     * @param event The action event.
     */
	@FXML
	public void connect(ActionEvent event) {
		String p;
		p = getport();
		if (p.trim().isEmpty())
			addConsole("You must enter a port number.\n");
		else {
			cemsServer = new CEMSserver(Integer.valueOf(p), this);
			try {
				cemsServer.listen(); // Start listening for connections
			} catch (Exception ex) {
			}
		}
	}
	
    /**
     * gets the port as a String.
     *
     * @return String of the port.
     */
	private String getport() {
		return portxt.getText();
	}

    /**
     * Handles the event when the "Disconnect" button is clicked.
     *
     * @param event The action event.
     */
	@FXML
	public void disconnect(ActionEvent event) {
		try {
			cemsServer.sendToAllClients(new Msg(MsgType.bye));
			cemsServer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		cemsServer.setConn(null);
		connectedObserv.clear(); // clear the connections list.
		btnDisconnect.setDisable(true);
		btnConnect.setDisable(false);
		addConsole("Server is close.\n");
		addConsole("Server has stopped listening for connections.\n");
		CEMSserver.serverController.addConsole("\n__________________________________________________________  clear  _________________________________________________\n\n");
	}

    /**
     * Handles the event when the "Exit" button is clicked.
     *
     * @param event The action event.
     */
	@FXML
	public void exit(ActionEvent event) {
		if(!btnDisconnect.isDisable()) disconnect(null);
		System.out.println("Exit CEMS Server app.");
		System.exit(0);
	}

    /**
     * Initializes the connected client table (when server app is up).
     */
	@FXML
	protected void initialize() {
		clientHostName.setCellValueFactory(data -> {
			ConnectionToClient connectionToClient = data.getValue();
			InetAddress inetAddress = connectionToClient.getInetAddress();
    	    String hostName = inetAddress.getHostName();
    	    return new SimpleStringProperty(hostName);
    	});
		clientIp.setCellValueFactory(data -> {
			ConnectionToClient connectionToClient = data.getValue();
			InetAddress inetAddress = connectionToClient.getInetAddress();
    	    String hostAddress = inetAddress.getHostAddress();
    	    return new SimpleStringProperty(hostAddress);
    	});	
		clientStatus.setCellValueFactory(cellData -> new ReadOnlyStringWrapper("Connected"));
		clientsTable.setItems(connectedObserv);
	}

	
   @FXML
    void importUserBtn(ActionEvent event) throws SQLException {
	   if (cemsServer == null )
		   notification.showErrorAlert("Cant import users. Please connected to the server");
	   else if (!cemsServer.importUsers(userFilePath.getText()))
		   notification.showErrorAlert("Can't import users. Please check that the File path is correct and you data is correct!");
	   else
		   notification.showInformationAlert("Data imported successfuly!");
    }
	
    /**
     * Adds the given IP to the connectedObserv list.
     *
     * @param connected The ConnectionToClient IP to be added.
     */
	public void addConnected(ConnectionToClient connected) {
		connectedObserv.add(connected);
	}

    /**
     * Removes the given IP from the connectedObserv list.
     *
     * @param connected The ConnectionToClient IP to be removed.
     */
	public void removeConnected(ConnectionToClient connected) {
		connectedObserv.remove(connected);
	}

    /**
     * Adds a message to the console.
     *
     * @param msg The message to be added.
     */
	public void addConsole(String msg) {
		console.appendText(msg); // append without read the prev text & scroll down automatically.
	}

    /**
     * Sets the disable property of the "Connect" button.
     *
     * @param flag True to disable the button, false otherwise.
     */
	public void setConnectDisable(boolean flag) {
		btnConnect.setDisable(flag);
	}

    /**
     * Sets the disable property of the "Disconnect" button.
     *
     * @param flag True to disable the button, false otherwise.
     */
	public void setDisconnectDisable(boolean flag) {
		btnDisconnect.setDisable(flag);
	}

    /**
     * gets the DBName as a String.
     *
     * @return String of the DBName.
     */
	public String getDBNameTxt() {
		return DBNameTxt.getText();
	}

    /**
     * gets the DBUsername as a String.
     *
     * @return String of the DBUsername.
     */
	public String getDBUsernameTxt() {
		return DBUsernameTxt.getText();
	}

    /**
     * gets the ServerId as a String.
     *
     * @return String of the ServerId.
     */
	public String getServerIdTxt() {
		return serverIdTxt.getText();
	}

    /**
     * gets the Password as a String.
     *
     * @return String of the Password.
     */
	public String getPasswordTxt() {
		return passwordTxt.getText();
	}

	/**
	 * @return the folderTxt
	 */
	public String getFolderTxt() {
		return folderTxt.getText();
	}
	
}