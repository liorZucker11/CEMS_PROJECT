package JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import communication.Msg;
import communication.MsgType;

/**
 * The DB_controller class provides methods for generating SQL queries for database operations.
 * it supports creating UPDATE, SELECT, and INSERT queries.
 */
public class DB_controller {
	
	public static String dbName = "cems.";

    /**
     * Creates an UPDATE query that increments the specified columns by one.
     *
     * @param tableToUpdate The ArrayList containing the name of the table to update.
     * @param set           The HashMap containing the columns and their corresponding values to increment.
     * @param where         The HashMap containing the conditions for the WHERE clause.
     * @return The generated SQL UPDATE query.
     */
	public static String createUPDATEPlusOnequery(ArrayList<String> tableToUpdate, HashMap<String, Object> set, HashMap<String, Object> where) {
		if (tableToUpdate == null || set == null)
			return "";
		StringBuilder query = new StringBuilder("UPDATE ");
		query.append(dbName + tableToUpdate.get(0)); // append the name of table wanted to be updated.
		query.append(" SET ");
		query.append(buildConditionPlusOnePartWithComma(set)); // append the parameters to be updated.
		if (where == null)
			return query.toString() + ";";
		query.append(" WHERE ");
		query.append(buildConditionPartWithAnd(where, true));
		return query.toString() + ";";
	}
	
    /**
     * Builds the SET clause of an UPDATE query to increment the specified columns by one.
     *
     * @param condition The HashMap containing the columns and their corresponding values to increment.
     * @return The generated SET clause of the UPDATE query.
     */
	public static String buildConditionPlusOnePartWithComma(HashMap<String, Object> condition) {
		if (condition == null)
			return "";
		StringBuilder res = new StringBuilder();
		for (Map.Entry<String, Object> entry : condition.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			res.append(key + "=" + key+"+"+ value.toString() + ",");
		}
		System.out.println("res is : "+res);
		res.deleteCharAt(res.length() - 1); // remove last comma.
		return res.toString();
	}
	
    /**
     * Creates a SELECT query with the specified parameters.
     *
     * @param select    The ArrayList containing the columns to select.
     * @param from      The ArrayList containing the tables to select from.
     * @param where     The HashMap containing the conditions for the WHERE clause.
     * @param whereCol  The HashMap containing the conditions for the WHERE clause (additional columns).
     * @return The generated SQL SELECT query.
     */
	public static String createSELECTquery(ArrayList<String> select, ArrayList<String> from, HashMap<String, Object> where, HashMap<String, Object> whereCol) {
		StringBuilder query = new StringBuilder("SELECT ");
		query.append(separateWithComma(select, false));
		query.append(" FROM ");
		query.append(separateWithComma(from, true));
		if (where == null)
			return query.toString() + ";";
		// get here if and only if there is WHRE to add:
		query.append(" WHERE ");
		query.append(buildConditionPartWithAnd(where, true));
		if(whereCol!=null && where!=null) query.append(" AND ");
		query.append(buildConditionPartWithAnd(whereCol, false));
		return query.toString() + ";";
	}

    /**
     * Creates an UPDATE query with the specified parameters.
     *
     * @param tableToUpdate The ArrayList containing the name of the table to update.
     * @param set           The HashMap containing the columns and their corresponding values to update.
     * @param where         The HashMap containing the conditions for the WHERE clause.
     * @return The generated SQL UPDATE query.
     */
	public static String createUPDATEquery(ArrayList<String> tableToUpdate, HashMap<String, Object> set, HashMap<String, Object> where) {
		if (tableToUpdate == null || set == null)
			return "";
		StringBuilder query = new StringBuilder("UPDATE ");
		query.append(dbName + tableToUpdate.get(0)); // append the name of table wanted to be updated.
		query.append(" SET ");
		query.append(buildConditionPartWithComma(set)); // append the parameters to be updated.
		if (where == null)
			return query.toString() + ";";
		query.append(" WHERE ");
		query.append(buildConditionPartWithAnd(where, true));
		return query.toString() + ";";
	}

    /**
     * Creates an INSERT query with the specified parameters.
     *
     * @param tableToUpdate The ArrayList containing the name of the table to insert into.
     * @param colNames      The ArrayList containing the column names for insertion.
     * @param values        The ArrayList containing the values to insert.
     * @return The generated SQL INSERT query.
     */
	public static String createINSERTquery(ArrayList<String> tableToUpdate, ArrayList<String> colNames, ArrayList<ArrayList<Object>> values) {
		if (tableToUpdate == null || colNames == null || values == null || colNames.size() != values.size())
			return "";
		StringBuilder query = new StringBuilder("INSERT INTO ");
		query.append(dbName + tableToUpdate.get(0)); // append the name of table wanted to be insert to.
		query.append(" (");
		query.append(separateWithComma(colNames, false)); // append the columns names to be updated.
		query.append(") VALUES ");
		query.append(separateValuesWithComma(values)); // append the values to insert.
		return query.toString() + ";";
	}
	
	/**
	 * Creates a message object with data from a ResultSet.
	 *
	 * @param type The type of the message.
	 * @param rs   The ResultSet containing the data.
	 * @return The created message object.
	 * @throws SQLException If an error occurs while accessing the ResultSet.
	 */
	public static Msg createDataMsg(MsgType type, ResultSet rs) throws SQLException {
		ArrayList<ArrayList<Object>> dataToClient = new ArrayList<>();
		int colunmCount = rs.getMetaData().getColumnCount();
		while (rs.next()) {
			ArrayList<Object> rowTemp = new ArrayList<>(colunmCount);
			for (int i = 1; i < colunmCount + 1; i++)
				rowTemp.add(rs.getObject(i));
			dataToClient.add(rowTemp);
		}
		Msg tmpMsg = new Msg(type);
		tmpMsg.setData(dataToClient);
		return tmpMsg;
	}

    /**
     * Separates the elements in the given ArrayList with commas.
     *
     * @param lst The ArrayList containing the elements to separate.
     * @return A string with the elements separated by commas.
     */
	public static String separateWithComma(ArrayList<String> lst, boolean withDbName) {
		if (lst == null || lst.size() == 0)
			return "";
		StringBuilder res = new StringBuilder();
		for (String str : lst)
			res.append((withDbName) ? (dbName + str + ",") : (str + ","));
		res.deleteCharAt(res.length() - 1);
		return res.toString();
	}

    /**
     * Separates the values in the given ArrayList of ArrayLists with commas and parentheses.
     *
     * @param lists The ArrayList of ArrayLists containing the values to separate.
     * @return A string with the values separated by commas and parentheses.
     */
	public static String separateValuesWithComma(ArrayList<ArrayList<Object>> lists) {
		if (lists == null || lists.size() == 0)	return "";
		StringBuilder res = new StringBuilder();
		for (ArrayList<Object> lst : lists) {
			res.append("(");
			for (Object val : lst) {
				if (val instanceof String)
					res.append("'" + val + "',");
				else
					res.append(val + ",");
			}
			res.deleteCharAt(res.length() - 1);
			res.append("),");
		}
		res.deleteCharAt(res.length() - 1);
		return res.toString();
	}

    /**
     * Builds the condition part of a query with commas between parameters.
     *
     * @param condition The HashMap containing the conditions.
     * @return The generated condition part of the query.
     */
	public static String buildConditionPartWithComma(HashMap<String, Object> condition) {
		if (condition == null)
			return "";
		StringBuilder res = new StringBuilder();

		for (Map.Entry<String, Object> entry : condition.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof String)
				res.append(key + "='" + value.toString() + "',");
			else
				res.append(key + "=" + value.toString() + ",");
		}
		res.deleteCharAt(res.length() - 1); // remove last comma.
		return res.toString();
	}

    /**
     * Builds the condition part of a query with "AND" between parameters.
     *
     * @param condition  The HashMap containing the conditions.
     * @param needGeresh A flag indicating if single quotes are needed for string values.
     * @return The generated condition part of the query.
     */
	public static String buildConditionPartWithAnd(HashMap<String, Object> condition, boolean needGeresh) {
		if (condition == null)
			return "";
		StringBuilder res = new StringBuilder();

		for (Map.Entry<String, Object> entry : condition.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (needGeresh && value instanceof String)
				res.append(key + "='" + value.toString() + "' AND ");
			else
				res.append(key + "=" + value.toString() + " AND ");
		}
		res.deleteCharAt(res.length() - 1); // remove last comma.
		res.deleteCharAt(res.length() - 1); // remove last comma.
		res.deleteCharAt(res.length() - 1); // remove last comma.
		res.deleteCharAt(res.length() - 1); // remove last comma.
		return res.toString();
	}

	/**
	 * Creates a DELETE query with the specified parameters.
	 * @param tableToDeleteFrom The name of the table to delete from.
	 * @param where The HashMap containing the conditions for the WHERE clause.
	 * @return The generated SQL DELETE query.
	*/
	public static String createDELETEquery(String tableToDeleteFrom, HashMap<String, Object> where) {
		if (tableToDeleteFrom == null)
			return "";
		StringBuilder query = new StringBuilder("DELETE FROM ");
		query.append(dbName + tableToDeleteFrom); // append the name of table wanted to be deleted.;
		if (where == null)
			return query.toString() + ";";
		// get here if and only if there is WHERE to add:
		query.append(" WHERE ");
		query.append(buildConditionPartWithAnd(where, true));
		return query.toString() + ";";
	}
}