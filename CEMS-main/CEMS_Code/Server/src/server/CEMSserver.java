package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import JDBC.DB_controller;
import JDBC.DataBaseConnector;
import communication.Msg;
import communication.MsgType;
import controllers.CemsFileController;
import enteties.User;
import gui.ServerController;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * The CEMSserver class is responsible for handling communication between the server and the clients.
 */
public class CEMSserver extends AbstractServer {

	Connection conn;
	public static ServerController serverController;
	
	/**
	 * map to maintain the clients logged in to users. 
	 */
	private static Map<User, ConnectionToClient> user_client = new HashMap<>();

	private CemsFileController cemsFileController = new CemsFileController();

	/**
	 * Constructs an instance of the CEMSserver.
	 *
	 * @param port The port number to connect on.
	 * @param sc   The server controller.
	 */
	public CEMSserver(int port, ServerController sc) {
		super(port);
		CEMSserver.serverController = sc;
	}

	InetAddress clientAddress;
	String clientHostname;

	/**
	 * Constructs a CEMSserver object with the specified port.
	 *
	 * @param port The port number on which the server will listen.
	 */
	public CEMSserver(int port) {
		super(port);
	}
	
	/**
	 * Called when a client successfully connects to the server.
	 * This method is responsible for handling the actions to be taken
	 * when a client establishes a connection.
	 *
	 * @param client The connection to the client that just connected.
	 */
	@Override
	protected void clientConnected(ConnectionToClient client) {
		// Retrieve the client's IP address and hostname
		InetAddress clientAddress = client.getInetAddress();
		String clientHostname = client.getInetAddress().getHostAddress();
		try {
			//serverController.addConnected(clientAddress);
			serverController.addConnected(client);
		} catch (Throwable t) {
			System.out.println("Error in clientConnected");
			serverController.addConsole("Error in clientConnected.\n");
		}
		;
		// Print the client's IP address and hostname
		serverController
				.addConsole("Client connected from " + clientAddress.getHostAddress() + " (" + clientHostname + ").\n");
		System.out.println("Client connected from " + clientAddress.getHostAddress() + " (" + clientHostname + ")");
	}

	/**
	 * This method handles any messages received from the client.
	 * 
	 * @param msgObj    The message received from the client.
	 * @param client The connection from which the message originated.
	 */
	public void handleMessageFromClient(Object msgObj, ConnectionToClient client) {
		Msg msg = (Msg) msgObj;
		java.sql.Statement stmt = null;
		String queryStr;
		ResultSet rs;
		serverController.addConsole("Message received: " + msg + " from " + client + ".\n");
		System.out.println("Message received: " + msg + " from " + client);
		try {
			switch (msg.getType()) {
			case select:
				stmt = conn.createStatement();
				queryStr = DB_controller.createSELECTquery(msg.getSelect(), msg.getFrom(), msg.getWhere(),
						msg.getWhereCol());
				//serverController.addConsole("query: ->" + queryStr + ".\n");
				System.out.println("query: ->" + queryStr);
				rs = stmt.executeQuery(queryStr);
				MsgType type = (msg.getFrom().get(0).equals("user")) ? MsgType.user : MsgType.data;
				Msg tmpMsg = DB_controller.createDataMsg(type, rs);
				if (tmpMsg.getData() == null || tmpMsg.getData().isEmpty())
					tmpMsg = new Msg(MsgType.empty);
				sendToClient(tmpMsg, client);
				break;
			case update:
				handleUpdateMsg(client, msg);
				break;
			case disconnect:
				serverController.addConsole("clientDisconnected" + client + ".\n");
				System.out.println("clientDisconnected" + client);
				serverController.removeConnected(client);
				sendToClient(new Msg(MsgType.bye), client);
				break;
			case manyMessages:
				for (Msg act : msg.getMsgLst())
					handleMessageFromClient(act, client);
				sendToClient(new Msg(MsgType.succeededAll), client);
				break;
			case insert:
				stmt = conn.createStatement();
				queryStr = DB_controller.createINSERTquery(msg.getTableToUpdate(), msg.getColNames(), msg.getValues());					
				//serverController.addConsole("query: ->" + queryStr + ".\n");
				System.out.println("query: ->" + queryStr);
				try{stmt.executeUpdate(queryStr);
				}catch(SQLException e) {System.out.println("insert faild"); sendToClient(new Msg(MsgType.insertFail), client); break;}
				sendToClient(new Msg(MsgType.insertSucceeded), client);
				break;
			case lockTest:
				//serverController.addConsole("client "+client+ " asked to lock test with test code " + msg.getTestCode()+".\n");
				System.out.println("client "+client+ " asked to lock test with test code " + msg.getTestCode()+".");
				sendToAllClients(msg);
				break;
			case file:
				Msg msg1 = new Msg(MsgType.filePopMsg);
				try {String LocalfilePath = serverController.getFolderTxt();
				msg.setPathFile(LocalfilePath);
				cemsFileController.saveFile(msg);
				msg1.setPopText("Successfully upload file to server");
				sendToClient(msg1, client);
				}catch (Throwable t) {
					msg1.setPopText("Error upload file to server");
					sendToClient(msg1, client);
				}
				break;
			case fileToSend:
				try {String LocalfilePath = serverController.getFolderTxt() + "/algebraTest.docx";
				Msg msgToCleint = cemsFileController.createMsgWithFile(LocalfilePath);
				msgToCleint.setPathFile(msg.getPathFile());
				sendToClient(msgToCleint, client);
				}catch (Throwable t) {
					Msg msg2 = new Msg(MsgType.filePopMsg);
					msg2.setPopText("Error getting file from server");
					sendToClient(msg2, client);
				}
				break;
			case updatePlusOne:
				stmt = conn.createStatement();
				queryStr = DB_controller.createUPDATEPlusOnequery(msg.getTableToUpdate(), msg.getSet(), msg.getWhere());
				//serverController.addConsole("query: ->" + queryStr + ".\n");
				System.out.println("query: ->" + queryStr);
				stmt.executeUpdate(queryStr);
				sendToClient(new Msg(MsgType.succeeded), client);
				break;			
			case delete:
				stmt = conn.createStatement();
				queryStr = DB_controller.createDELETEquery(msg.getDeleteFrom(), msg.getWhere());
				//serverController.addConsole("query: ->" + queryStr + ".\n");
				System.out.println("query: ->" + queryStr);
				stmt.executeUpdate(queryStr);
				sendToClient(new Msg(MsgType.succeeded), client);
				break;
			case login:
				handleUpdateMsg(client, msg);
				user_client.put(msg.getUser(), client);
				break;
			case logout:
				handleUpdateMsg(client, msg);
				user_client.remove(msg.getUser());
				break;
			case pop:
				if(!user_client.containsKey(msg.getUser())) return;
				sendToClient(msg, user_client.get(msg.getUser()));
				break;
			case changeDuration:
				serverController.addConsole("duration changed for code " + msg.getTestCode()+".\n");
				System.out.println("duration changed for code " + msg.getTestCode()+".");
				sendToAllClients(msg);
				break;
			default:
				break;
			}
		} catch (SQLException ex) {
			/* handle any errors */}
		return;
	}

	/**
	 * the actions to perform in the handleMessageFromClient, in update type.
	 * 
	 * @param client
	 * @param msg
	 * @throws SQLException
	 */
	public void handleUpdateMsg(ConnectionToClient client, Msg msg) throws SQLException {
		java.sql.Statement stmt;
		String queryStr;
		stmt = conn.createStatement();
		queryStr = DB_controller.createUPDATEquery(msg.getTableToUpdate(), msg.getSet(), msg.getWhere());
		serverController.addConsole("query: ->" + queryStr + ".\n");
		System.out.println("query: ->" + queryStr);
		stmt.executeUpdate(queryStr);
		sendToClient(new Msg(MsgType.succeeded), client);
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	public void serverStarted() {
		serverController.addConsole("Server listening for connections on port " + getPort() + ".\n");
		System.out.println("Server listening for connections on port " + getPort());
		if (new DataBaseConnector().connectionToDataBase(this)) {
			serverController.addConsole("SQL connection succeed.\n");
			System.out.println("SQL connection succeed");
			serverController.setConnectDisable(true);
			serverController.setDisconnectDisable(false);
		}

		else {
			serverController.addConsole("SQL connection fail");
			System.out.println("SQL connection fail");
			try {
				this.close();
				serverController.addConsole("Server closed.");
				System.out.println("Server closed.");
			} catch (IOException e) {
				serverController.addConsole("Cant close server.");
				System.out.println("Cant close server.");
			}
		}
	}

    /**
     * Retrieves the connection object.
     *
     * @return The connection object.
     */
	public Connection getConn() {
		return conn;
	}

    /**
     * Sets the connection object.
     *
     * @param conn The connection object to set.
     */
	public void setConn(Connection conn) {
		this.conn = conn;
	}

    /**
     * Sends a message to a client.
     *
     * @param msg    The message to send.
     * @param client The client connection object.
     */
	private void sendToClient(Object msg, ConnectionToClient client) {
		try {
			client.sendToClient(msg);
		} catch (Exception ex) {
			serverController.addConsole("error in sending msg to client.");
			System.out.println("error in sending msg to client.");
		}
	}

	/**
	* Utility import method.
	* Imports users users.txt  to the DB by executing Update queries.
	* @param string 
	 * @return 
	* @throws SQLException 
	*/
	public boolean importUsers(String string) throws SQLException {
		if (conn == null)
			return false;
		java.sql.Statement stmt= conn.createStatement();
		String queryStr;
		String filePath = string;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        	String line;
            while ((line = br.readLine()) != null) {
            	String[] words = line.split(",");
            	Msg msg1 = new Msg(MsgType.insert);
            	msg1.setTableToUpdate("user");
            	msg1.setColNames("id, name, username, password, premission, loggedin");
            	ArrayList<Object> temp = new ArrayList<Object>();
            	for (int i =0 ; i< 6;i++) {
            		temp.add(words[i]);
            		System.out.println("words[i] : " + i + " "+words[i]);
            	}		
            	msg1.setValues(temp);
            	System.out.println("words : " + words);
            	System.out.println("temp : " + temp);
            	queryStr = DB_controller.createINSERTquery(msg1.getTableToUpdate(), msg1.getColNames(), msg1.getValues());					
				//serverController.addConsole("query: ->" + queryStr + ".\n");
				System.out.println("query: ->" + queryStr);
				stmt.executeUpdate(queryStr);
            }}
        	catch (IOException e) {
                return false;
            } catch (SQLException e1) {
                return false;
            }
		return true;
	}
}