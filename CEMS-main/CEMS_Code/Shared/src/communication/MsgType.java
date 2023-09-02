package communication;

/**
 * The {@code MsgType} enum represents the different types of messages exchanged between the client and server
 * in the JDBC application.
 */
public enum MsgType {
	
	disconnect, // client ---> server. | indicates that the client want to disconnect from server.
	
	select, // client ---> server. | select query.
	
	update, // client ---> server. | update query.
	
	updatePlusOne,// client ---> server. | updatePlusOne query.
	
	insert, // client ---> server. | insert query.
	
	manyMessages, // client ---> server. | Msg of list of messages. (probably each msg is a query).
	
	data, // server ---> client. | data from DB, returned by a select query.
	
	user, // server ---> client. | user from DB, returned by a select query.
	
	succeeded, // server ---> client. | indicate that a query executed successful (update / insert).
	
	succeededAll, // server ---> client. | indicate that list of queries executed successful.
	
	bye, // server ---> client. | indicate that the server got the 'disconnect' msg from client and acted properly.
	
	empty, // server ---> client. | indicates that the select query found no data.
	
	insertFail, // server ---> client. | indicates that the insert query failed.
	
	insertSucceeded, // server ---> client. | indicates that the insert query succeeded.
	
	lockTest, // server <---> client. | indicates that a test got locked.
	
	file, // client ---> server indicates that a student upload file.
	
	fileToSend, // server--->client. | indicates that a student download file.

	delete, // client ---> server | delete query.
	
	login, // client ---> server. | user login.
	
	logout, // client ---> server. | user logout.
	
	pop, // server <---> client. | pop message from client to client.
	
	changeDuration, // server <---> client. | indicates that a test duration got changed.
	
	filePopMsg
}
