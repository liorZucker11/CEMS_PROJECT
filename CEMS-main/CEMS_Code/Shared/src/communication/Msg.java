package communication;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import enteties.CemsFile;
import enteties.User;

/**
 * The {@code Msg} class represents a message object used for communication between the client and the server.
 * It encapsulates various components of a database query, such as SELECT, FROM, WHERE, UPDATE, and INSERT INTO clauses,
 * as well as additional data and metadata related to the message.
 * The class provides methods for setting and retrieving different parts of the message, such as the message type, query components,
 * data, file objects, and user information.
 * It also includes utility methods for converting the data stored in the message to a list of objects of a specified type.
 */
public class Msg implements Serializable{
	
	private static final long serialVersionUID = 1L; //default serial
	
	private MsgType type;
	private ArrayList<String> select = null; // for the SELECT part of query.
	private ArrayList<String> from = null; // for the FROM part of query.
	private HashMap<String, Object> where = null; // for the WHERE part of query.
	private HashMap<String, Object> whereCol = null; // for the WHERE part of query between two coloums.
	private ArrayList<String> tableToUpdate = null; // for the UPDATE part of query.
	private HashMap<String, Object> set = null; // for the SET part of query.
	private String deleteFrom = null; // for the Delete From part of query.
	private ArrayList<Msg> msgLst = null; //used to send a bunch of messages all at once.
	private ArrayList<ArrayList<Object>> data = null; //data from DB to client.
	private ArrayList<String> colNames = null; //for the INSERT INTO query.
	private ArrayList<ArrayList<Object>> values = null;  //for the INSERT INTO query.
	private CemsFile file =null;//for send file to student , and get file from student.
	private Integer testCode = null;
	private String pathFile = null;
	private User user = null;
	private String popText = null;
	private Integer duration = null;

	
	/**
	 * Constructs an empty {@code Msg} object.
	 */
	public Msg() {}
	
	/**
	 * Constructs a {@code Msg} object with the specified message type.
	 *
	 * @param type the message type
	 */
	public Msg(MsgType type) {
		this.type = type;
	}
	
	/**
	 * Retrieves the SELECT part of the query.
	 *
	 * @return the SELECT part as an {@link ArrayList} of strings
	 */
	public ArrayList<String> getSelect() {
		return select;
	}
	
	/**
	 * Retrieves the FROM part of the query.
	 *
	 * @return the FROM part as an {@link ArrayList} of strings
	 */
	public ArrayList<String> getFrom() {
		return from;
	}
	
	/**
	 * Retrieves the message type.
	 *
	 * @return the message type as a {@link MsgType} object
	 */
	public MsgType getType() {
		return type;
	}
	
	/**
	 * Sets the message type.
	 *
	 * @param type the message type to set
	 */
	public void setType(MsgType type) {
		this.type = type;
	}
	
	/**
	 * Retrieves the WHERE part of the query.
	 *
	 * @return the WHERE part as a {@link HashMap} of string keys and object values
	 */
	public HashMap<String, Object> getWhere() {
		return where;
	}
	
	/**
	 * Retrieves the WHERE part of the query between two columns.
	 *
	 * @return the WHERE part as a {@link HashMap} of string keys and object values representing column pairs
	 */
	public HashMap<String, Object> getWhereCol() {
		return whereCol;
	}

	/**
	 * Retrieves the SET part of the query.
	 *
	 * @return the SET part as a {@link HashMap} of string keys and object values
	 */
	public HashMap<String, Object> getSet() {
		return set;
	}
	
	/**
	 * Retrieves the tables to update in the query.
	 *
	 * @return the tables to update as an {@link ArrayList} of strings
	 */
	public ArrayList<String> getTableToUpdate() {
		return tableToUpdate;
	}
	
	/**
	 * Retrieves a list of messages.
	 *
	 * @return the list of messages as an {@link ArrayList} of {@link Msg} objects
	 */
	public ArrayList<Msg> getMsgLst() {
		return msgLst;
	}

	/**
	 * Sets a single message to the list of messages.
	 * If the message list is null, it creates a new list.
	 *
	 * @param msg the message to add to the list
	 */
	public void setMsgLst(Msg msg) {
		if(msgLst==null) msgLst = new ArrayList<Msg>();
		msgLst.add(msg);
	}
	
	/**
	 * Sets a list of messages.
	 *
	 * @param msgLst the list of messages to set
	 */
	public void setMsgLst(ArrayList<Msg> msgLst) {
		this.msgLst = msgLst;
	}
	
	/**
	 * Retrieves the data from the database to the client.
	 *
	 * @return the data as an {@link ArrayList} of {@link ArrayList} of objects
	 */
	public ArrayList<ArrayList<Object>> getData() {
		return data;
	}

	/**
	 * Sets the data from the database to the client.
	 *
	 * @param data the data to set
	 */
	public void setData(ArrayList<ArrayList<Object>> data) {
		this.data = data;
	}	
	
	/**
	 * Adds a SELECT part to the query.
	 * If the SELECT part is null, it creates a new list.
	 *
	 * @param sel the SELECT part to add
	 */
	public void setSelect(String sel) {
		if(select==null) select=new ArrayList<String>();
		select.add(sel);
	}
	
	/**
	 * Adds a FROM part to the query.
	 * If the FROM part is null, it creates a new list.
	 *
	 * @param fr the FROM part to add
	 */
	public void setFrom(String fr) {
		if(from==null) from=new ArrayList<String>();
		from.add(fr);
	}
	
	/**
	 * Adds a WHERE condition to the query.
	 * If the WHERE part is null, it creates a new {@link HashMap}.
	 *
	 * @param whereCol   the column name of the WHERE condition
	 * @param whereValue the value of the WHERE condition
	 */
	public void setWhere(String whereCol, Object whereValue) {
		if(where==null) where=new HashMap<String, Object>();
		where.put(whereCol, whereValue);
	}
	
	/**
	 * Adds a WHERE condition between two columns to the query.
	 * If the WHERE part is null, it creates a new {@link HashMap}.
	 *
	 * @param whereCol1  the first column name of the WHERE condition
	 * @param whereValue the second column name of the WHERE condition
	 */
	public void setWhereCol(String whereCol1, String whereValue) {
		if(whereCol==null) whereCol=new HashMap<String, Object>();
		whereCol.put(whereCol1, whereValue);
	}
	
	/**
	 * Adds a table name to update in the query.
	 * If the tableToUpdate part is null, it creates a new list.
	 *
	 * @param table the table name to update
	 */
	public void setTableToUpdate(String table) {
		if(tableToUpdate==null) tableToUpdate=new ArrayList<String>();
		tableToUpdate.add(table);
	}
	
	/**
	 * Adds a SET part to the query.
	 * If the SET part is null, it creates a new {@link HashMap}.
	 *
	 * @param setCol   the column name of the SET part
	 * @param setValue the value of the SET part
	 */
	public void setSet(String setCol, Object setValue) {
		if(set==null) set=new HashMap<String, Object>();
		set.put(setCol, setValue);
	}

	/**
	 * Generates a string representation of the message.
	 *
	 * @return a string representation of the message
	 */
	@Override
	public String toString() {
		return "Msg [type=" + type +"]";
	}
	
	/**
	 * Retrieves the column names for the INSERT INTO query.
	 *
	 * @return the column names as an {@link ArrayList} of strings
	 */
	public ArrayList<String> getColNames() {
		return colNames;
	}

	/**
	 * Sets the column names for the INSERT INTO query.
	 *
	 * @param colNames the column names to set
	 */
	public void setColNames(ArrayList<String> colNames) {
		this.colNames = colNames;
	}
	
	/**
	 * Adds a column name to the list of column names for the INSERT INTO query.
	 * If the column names list is null, it creates a new list.
	 *
	 * @param name the column name to add
	 */
	public void setColNames(String name) {
		if(colNames==null) colNames=new ArrayList<String>();
		colNames.add(name);
	}

	/**
	 * Retrieves the values for the INSERT INTO query.
	 *
	 * @return the values as an {@link ArrayList} of {@link ArrayList} of objects
	 */
	public ArrayList<ArrayList<Object>> getValues() {
		return values;
	}
	
	/**
	 * Sets the values for the INSERT INTO query.
	 *
	 * @param lst the values to set
	 */
	public void setValues(ArrayList<Object> lst) {
		if(values==null) values=new ArrayList<ArrayList<Object>>();
		values.add(lst);
	}

	/**
	 * Retrieves the test code associated with the message.
	 *
	 * @return the test code as an {@link Integer}
	 */
	public Integer getTestCode() {
		return testCode;
	}

	/**
	 * Sets the test code associated with the message.
	 *
	 * @param testCode the test code to set
	 */
	public void setTestCode(Integer testCode) {
		this.testCode = testCode;
	}

	/**
	 * Converts the data stored in the {@code data} field to a list of objects of the specified type.
	 *
	 * @param type the class representing the desired type of the converted objects
	 * @param <T> the generic type of the converted objects
	 * @return an {@link java.util.ArrayList} containing the converted objects
	 */
	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> convertData(Class<T> type) {
	    ArrayList<T> converted = new ArrayList<>();
	    try {
	        for (List<Object> dataRow : data) {
	            Constructor<?>[] constructors = type.getConstructors();
	            for (Constructor<?> constructor : constructors) {
	                Class<?>[] parameterTypes = constructor.getParameterTypes();
	                if (parameterTypes.length == dataRow.size()) {
	                    boolean match = true;
	                    for (int i = 0; i < parameterTypes.length; i++) {
	                        if (!parameterTypes[i].isInstance(dataRow.get(i))) {
	                            match = false;
	                            break;
	                        }
	                    }
	                    if (match) {
	                        converted.add((T) constructor.newInstance(dataRow.toArray()));
	                        break;
	                    }
	                }
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return converted;
	}

	/**
	 * Retrieves the file object associated with the message.
	 *
	 * @return the file object as a {@link CemsFile}
	 */
	public CemsFile getCemsFile() {
		return file;
	}
	
	/**
	 * Sets the file object associated with the message.
	 *
	 * @param file the file object to set
	 */
	public void setCemsFile(CemsFile file) {
		this.file = file;
	}
	/**
	 *get the path of the file.
	 * @return String
	 */
	public String getPathFile() {
		return pathFile;
	}
	/**
	 * set the path of the file.
	 * @param pathFile
	 */
	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the deleteFrom
	 */
	public String getDeleteFrom() {
		return deleteFrom;
	}

	/**
	 * @param deleteFrom the deleteFrom to set
	 */
	public void setDeleteFrom(String deleteFrom) {
		this.deleteFrom = deleteFrom;
	}

	/**
	 * @return the popText
	 */
	public String getPopText() {
		return popText;
	}

	/**
	 * @param popText the popText to set
	 */
	public void setPopText(String popText) {
		this.popText = popText;
	}

	/**
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
}