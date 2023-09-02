package client;

import java.io.IOException;
import java.util.HashMap;

import communication.Msg;
import communication.MsgType;
import controllers.CemsFileController;
import enteties.User;
import gui.AbstractController;
import gui.ManualTestController;
import gui.Testing;
import notifications.NotificationAlertsController;
import ocsf.client.AbstractClient;

/**
 * The ChatClient class is responsible for handling communication between the client and the server.
 */
public class ChatClient extends AbstractClient {
	/**
	 * The mapping of screen names to their corresponding AbstractController objects.
	 */
	public static HashMap<String, AbstractController> screens = new HashMap<String, AbstractController>();
	
	/**
	 * The last displayed screen.
	 */
	public static AbstractController lastCurrentScreen;
	
	/**
	 * The currently logged-in user.
	 */
	public static User user;
	
	/**
	 * Indicates whether the client is awaiting a response from the server.
	 */
	public static boolean awaitResponse = false;
	
	private CemsFileController cemsFileController = new CemsFileController();

	/**
	 * Constructs an instance of the ChatClient.
	 *
	 * @param host The server to connect to.
	 * @param port The port number to connect on.
	 * @throws IOException If an I/O error occurs while opening the connection.
	 */
	public ChatClient(String host, int port) throws IOException {
		super(host, port); // Call the superclass constructor
		openConnection();
		System.out.println("connected to server");
	}

	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param tmpMsg The message from the server.
	 */
	public void handleMessageFromServer(Object tmpMsg) {
		Msg msg = ((Msg) tmpMsg);
		System.out.println("Message recieved from server -> " + msg);
		if (msg instanceof Msg) {
			switch (msg.getType()) {
			case succeeded:
				System.out.println("server executed the query.");
				break;
			case succeededAll:
				System.out.println("server handle all bunch of msgs.");
				break;
			case bye:
				System.out.println("client forced to stop by the server.");
				System.exit(0);
				break;
			case data:
				AbstractController.setDataReceived(msg);
				break;
			case user:
				user = msg.convertData(User.class).get(0);
				break;
			case empty:
				AbstractController.msgReceived = null;
				System.out.println("server didn't find any data matching this query.");
				break;
			case insertFail:
				System.out.println("can't insert data to DB.");
				break;
			case insertSucceeded:
				System.out.println("Data inserted into DB.");
				break;
			case lockTest:
				System.out.println("Test code " + msg.getTestCode() + " got locked.");
				if(lastCurrentScreen instanceof Testing) 
					((Testing)lastCurrentScreen).testGotManualyLockedByLecturer(msg.getTestCode().toString());
				break;
			case file:
				try {
					cemsFileController.saveFile(msg);
					new NotificationAlertsController().showInformationAlert("The test was download successfully!");
					((ManualTestController)lastCurrentScreen).timeController.startTimer();
				}catch (Exception e) {
					Msg msg1 = new Msg(MsgType.filePopMsg);
					msg1.setPopText("Error getting file from server");
					handleMessageFromServer(msg1);
				}
				
				break; 
			case pop:
				User dest = msg.getUser();
				if(!user.equals(dest)) return;
				String popText = msg.getPopText();
				popText += "\n\n  <-- this message sent also to mail: "+ dest.getUsername()+"@gmail.com and to phone number: 052-"+(dest.getId().substring(0,7)+" -->");
				lastCurrentScreen.popMessage(popText);
				break;
			case changeDuration:
				System.out.println("Test code " + msg.getTestCode() + " duration got changed.");
				if(lastCurrentScreen instanceof Testing) 
					((Testing)lastCurrentScreen).testdurationGotChanged(msg.getTestCode().toString(), msg.getDuration());
				break;
			case filePopMsg:
				lastCurrentScreen.popMessage(msg.getPopText());
				break;
			default:
				break;
			}
		}
		awaitResponse = false; // important. magic line.
	}

	/**
	 * Sends a message from the client to the server and waits for a response.
	 *
	 * @param msg The message to send.
	 */
	public void handleMessageFromClientUI(Msg msg) {
		try {
			awaitResponse = true;
			sendToServer(msg); // send the msg to server.
			// wait for response
			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not send message to server: Terminating client." + e);
		}
	}

	/**
	 * Retrieves the AbstractController object associated with the given screen name.
	 *
	 * @param screenName The name of the screen.
	 * @return The AbstractController object associated with the screen name, or null if not found.
	 */
	public static AbstractController getScreen(String screenName) {
		return screens.get(screenName);
	}

	/**
	 * Resets the currently logged-in user to null.
	 */
	public static void resetUser() {
		user = null;
	}
}