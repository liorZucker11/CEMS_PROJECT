package unittests;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import JDBC.DB_controller;

public class DB_controllerTest {
	
	//for select:
    private ArrayList<String> select;
    private ArrayList<String> from;
    private HashMap<String, Object> where;
    private HashMap<String, Object> whereCol;
    
    //for insert:
    private ArrayList<String> tableToUpdate;
    private ArrayList<String> colNames;
    private ArrayList<ArrayList<Object>> values;
    
	@BeforeEach
	void setUpBeforeTest() throws Exception {
        // Initialize the test data before each test case:
        select = new ArrayList<>();
        from = new ArrayList<>();
        where = new HashMap<>();
        whereCol = new HashMap<>();
        tableToUpdate = new ArrayList<>();
        colNames = new ArrayList<>();
        values = new ArrayList<>();
	}
	
    @Test
    // Description: Test createSELECTquery with non-empty parameters.
    // Input: select=["column1", "column2"], from=["table1", "table2"], where={"condition1": "value1"}, whereCol=null.
    // Expected result: "SELECT column1,column2 FROM cems.table1,cems.table2 WHERE condition1='value1' ;".
    public void createSELECTqueryTest_NonEmptyParameters() {
        select.add("column1");
        select.add("column2");
        from.add("table1");
        from.add("table2");
        where.put("condition1", "value1");
        String expectedQuery = "SELECT column1,column2 FROM cems.table1,cems.table2 WHERE condition1='value1' ;";
        String actualQuery = DB_controller.createSELECTquery(select, from, where, null);
        assertEquals(expectedQuery, actualQuery);
    }
    
    @Test
    // Description: Test createSELECTquery with empty parameters.
    // Input: Empty select, from, where, and whereCol.
    // Expected result: null.
    public void createSELECTqueryTest_EmptyParameters() {
        String actualQuery = null;
        try{ actualQuery = DB_controller.createSELECTquery(select, from, where, whereCol);
        }catch(Exception e) {fail("exception.");}
        assertNull(actualQuery);
    }
    
    @Test
    // Description: Test createSELECTquery with null where and whereCol parameters.
    // Input: select=["column1", "column2"], from=["table1", "table2"], where=null, whereCol=null.
    // Expected result: "SELECT column1,column2 FROM cems.table1,cems.table2;".
    public void createSELECTqueryTest_NullWhereAndWhereCol() {
        select.add("column1");
        select.add("column2");
        from.add("table1");
        from.add("table2");
        String expectedQuery = "SELECT column1,column2 FROM cems.table1,cems.table2;";
        String actualQuery = DB_controller.createSELECTquery(select, from, null, null);
        assertEquals(expectedQuery, actualQuery);
    }
    
    @Test
    // Description: Test createSELECTquery with additional columns in whereCol parameter.
    // Input: select=["column1", "column2"], from=["table1", "table2"], where={"condition1": "value1"}, whereCol={"column3": "value3", "column4": "value4"}.
    // Expected result: "SELECT column1,column2 FROM cems.table1,cems.table2 WHERE condition1='value1'  AND column4=value4 AND column3=value3 ;".
    public void createSELECTqueryTest_WithWhereCol() {
        select.add("column1");
        select.add("column2");
        from.add("table1");
        from.add("table2");
        where.put("condition1", "value1");
        whereCol.put("column3", "value3");
        whereCol.put("column4", "value4");
        String expectedQuery = "SELECT column1,column2 FROM cems.table1,cems.table2 WHERE condition1='value1'  AND column4=value4 AND column3=value3 ;";
        String actualQuery = DB_controller.createSELECTquery(select, from, where, whereCol);
        assertEquals(expectedQuery, actualQuery);
    }
    
    @Test
    // Description: Test createSELECTquery with empty select and from parameters.
    // Input: Empty select, from=["table1", "table2"], where={"condition1": "value1"}, whereCol=null.
    // Expected result: null
    public void createSELECTqueryTest_EmptySelectAndFrom() {
        from.add("table1");
        from.add("table2");
        where.put("condition1", "value1");
        String actualQuery = null;
        try{ actualQuery = DB_controller.createSELECTquery(select, from, where, whereCol);
        } catch(Exception e) {fail("exception.");}
        assertNull(actualQuery);
    }
    
    @Test
    // Description: Test createSELECTquery with empty where and whereCol parameters.
    // Input: select=["column1", "column2"], from=["table1", "table2"], empty where, empty whereCol.
    // Expected result: "SELECT column1,column2 FROM cems.table1, cems.table2 ;".
    public void createSELECTqueryTest_EmptyWhereAndWhereCol() {
        select.add("column1");
        select.add("column2");
        from.add("table1");
        from.add("table2");
        String expectedQuery = "SELECT column1,column2 FROM cems.table1, cems.table2 ;";
        String actualQuery = null;
        try { actualQuery = DB_controller.createSELECTquery(select, from, new HashMap<>(), new HashMap<>());
    	} catch(Exception e) {fail("exception.");}
        assertEquals(expectedQuery, actualQuery);
    }
    
    @Test
    // Description: Test createSELECTquery with empty select and from parameters, null where and whereCol parameters
    // Input: Empty select, from, where=null, whereCol=null
    // Expected result: "SELECT  FROM ;"
    public void createSELECTqueryTest_EmptySelectAndFromNullWhereAndWhereCol() {
        String expectedQuery = "SELECT  FROM ;";
        String actualQuery = null;
        try { actualQuery = DB_controller.createSELECTquery(new ArrayList<>(), new ArrayList<>(), null, null);
    	} catch(Exception e) {fail("exception.");}
        assertEquals(expectedQuery, actualQuery);
    }
    
    @Test
    // Description: Test createSELECTquery with select and from containing special characters
    // Input: select=["col'1", "col\"2"], from=["tab'le1", "tab\"le2"], where=null, whereCol=null
    // Expected result: "SELECT col'1, col\"2 FROM tab'le1, tab\"le2;"
    public void createSELECTqueryTest_SelectAndFromWithSpecialCharacters() {
        select.add("col'1");
        select.add("col\"2");
        from.add("tab'le1");
        from.add("tab\"le2");
        String expectedQuery = "SELECT col'1,col\"2 FROM cems.tab'le1,cems.tab\"le2;";
        String actualQuery = DB_controller.createSELECTquery(select, from, null, null);
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    // Description: Test createINSERTquery with valid parameters.
    // Input: tableToUpdate=["table1"], colNames=["column1", "column2"], values=[["value1", 2], ["value3", 4]].
    // Expected result: "INSERT INTO cems.table1 (column1,column2) VALUES ('value1',2),('value3',4);".
    public void createINSERTqueryTest_ValidParameters() {
        tableToUpdate.add("table1");
        colNames.add("column1");
        colNames.add("column2");
        ArrayList<Object> values1 = new ArrayList<>();
        values1.add("value1");
        values1.add(2);
        ArrayList<Object> values2 = new ArrayList<>();
        values2.add("value3");
        values2.add(4);
        values.add(values1);
        values.add(values2);
        String expectedQuery = "INSERT INTO cems.table1 (column1,column2) VALUES ('value1',2),('value3',4);";
        String actualQuery = DB_controller.createINSERTquery(tableToUpdate, colNames, values);
        assertEquals(expectedQuery, actualQuery);
    }
    
    @Test
    // Description: Test createINSERTquery with empty tableToUpdate, colNames, and values parameters.
    // Input: Empty tableToUpdate, colNames, values.
    // Expected result: "".
    public void createINSERTqueryTest_EmptyParameters() {
        String expectedQuery = "";
        String actualQuery = null;
        try{ actualQuery = DB_controller.createINSERTquery(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    	} catch(Exception e) {fail("exception.");}
        assertEquals(expectedQuery, actualQuery);
    }
    
    @Test
    // Description: Test createINSERTquery with null parameters.
    // Input: tableToUpdate=null, colNames=null, values=null.
    // Expected result: null.
    public void createINSERTqueryTest_NullParameters() {
        String actualQuery = null;
        try{ actualQuery = DB_controller.createINSERTquery(null, null, null);
    	} catch(Exception e) {fail("exception.");}
        assertNull(actualQuery);
    }
    
    @Test
    // Description: Test createINSERTquery with colNames and values having different sizes.
    // Input: tableToUpdate=["table1"], colNames=["column1", "column2"], values=[["value1"]].
    // Expected result: null.
    public void createINSERTqueryTest_ColNamesValuesSizeMismatch() {
        tableToUpdate.add("table1");
        colNames.add("column1");
        colNames.add("column2");
        ArrayList<Object> values1 = new ArrayList<>();
        values1.add("value1");
        values.add(values1);
        String actualQuery = null;
        try{ actualQuery = DB_controller.createINSERTquery(tableToUpdate, colNames, values);
		} catch(Exception e) {fail("exception.");}
	    assertNull(actualQuery);
    }
    
    @Test
    // Description: Test createINSERTquery with empty colNames and values parameters.
    // Input: tableToUpdate=["table1"], empty colNames, empty values.
    // Expected result: null.
    public void createINSERTqueryTest_EmptyColNamesAndValues() {
        tableToUpdate.add("table1");
        String actualQuery = null;
        try{ actualQuery = DB_controller.createINSERTquery(tableToUpdate, new ArrayList<>(), new ArrayList<>());
        } catch(Exception e) {fail("exception.");}
	    assertNull(actualQuery);
    }
    
    @Test
    // Description: Test createINSERTquery with empty tableToUpdate parameter.
    // Input: Empty tableToUpdate, colNames=["column1", "column2"], values=[["value1", 2], ["value3", 4]].
    // Expected result: null.
    public void createINSERTqueryTest_EmptyTableToUpdate() {
        colNames.add("column1");
        colNames.add("column2");
        ArrayList<Object> values1 = new ArrayList<>();
        values1.add("value1");
        values1.add(2);
        ArrayList<Object> values2 = new ArrayList<>();
        values2.add("value3");
        values2.add(4);
        values.add(values1);
        values.add(values2);
        String actualQuery = null;
        try{ actualQuery = DB_controller.createINSERTquery(new ArrayList<>(), colNames, values);
	    } catch(Exception e) {fail("exception.");}
	    assertNull(actualQuery);
    }
    
    @Test
    // Description: Test createINSERTquery with null colNames and values parameters.
    // Input: tableToUpdate=["table1"], colNames=null, values=null.
    // Expected result: "".
    public void createINSERTqueryTest_NullColNamesAndValues() {
        tableToUpdate.add("table1");
        String expectedQuery = "";
        String actualQuery = DB_controller.createINSERTquery(tableToUpdate, null, null);
        assertEquals(expectedQuery, actualQuery);
    }
    
    @Test
    // Description: Test createINSERTquery with special characters in colNames and values.
    // Input: tableToUpdate=["table1"], colNames=["col'1", "col\"2"], values=[["val'1", "val\"2"], ["val3", "val4"]].
    // Expected result: "INSERT INTO dbName.table1 (col'1, col\"2) VALUES ('val'1','val\"2'),('val3','val4');".
    public void createINSERTqueryTest_ColNamesAndValuesWithSpecialCharacters() {
        tableToUpdate.add("table1");
        colNames.add("col'1");
        colNames.add("col\"2");
        ArrayList<Object> values1 = new ArrayList<>();
        values1.add("val'1");
        values1.add("val\"2");
        ArrayList<Object> values2 = new ArrayList<>();
        values2.add("val3");
        values2.add("val4");
        values.add(values1);
        values.add(values2);
        String expectedQuery = "INSERT INTO cems.table1 (col'1,col\"2) VALUES ('val'1','val\"2'),('val3','val4');";
        String actualQuery = DB_controller.createINSERTquery(tableToUpdate, colNames, values);
        assertEquals(expectedQuery, actualQuery);
    }
    
	// description: verify the behavior of the separateWithComma() method in the DB_controller class when the input list is null.
	// input: list=null, withDbName=true.
	// Expected Result: empty string.
	@Test
	void separateWithCommaTest_lstIsNull() {
		String result;
		result=DB_controller.separateWithComma(null,true);
		assertEquals("",result);
	}
	
	// description: verify the behavior of the separateWithComma() method in the DB_controller class when the input list is in size 0.
	// input: list=list with no elements, withDbName=true.
	// Expected Result: empty string.
	@Test
	void separateWithCommaTest_lstSizeIs0() {
		String result;
		ArrayList<String>lst=new ArrayList<String>() ;
		result=DB_controller.separateWithComma(lst,true);
		assertEquals("",result);
	}
	
	// description: verify the behavior of the separateWithComma() method in the DB_controller class when valid arguments and true.
	// input: list=["check"] ,withDbName=true.
	// Expected Result: "cems.check".
	@Test
	void separateWithCommaTest_ValidLstDbTrue() {
		String result;
		ArrayList<String>lst=new ArrayList<String>() ;
		lst.add("check");
		result=DB_controller.separateWithComma(lst,true);
		assertEquals("cems.check",result);
	}
	
	// description: verify the behavior of the separateWithComma() method in the DB_controller class when valid arguments and false.
	// input: list=["check"] ,withDbName=false.
	// Expected Result: "check".
	@Test
	void separateWithCommaTest_ValidLstDbFalse() {
		String result;
		ArrayList<String>lst=new ArrayList<String>() ;
		lst.add("check");
		result=DB_controller.separateWithComma(lst,false);
		assertEquals("check",result);
	}
	
	// description: verify the behavior of the buildConditionPartWithAnd() method in the DB_controller class when the input hashmap is null.
	// input: list=null, withDbName=true.
	// Expected Result: empty string.
	@Test
	void buildConditionPartWithAndTest_NullHashMap() {
		String result;
		result=DB_controller.buildConditionPartWithAnd(null,true);
		assertEquals("",result);
	}
	
	// description: verify the behavior of the buildConditionPartWithAnd() method in the DB_controller class when the hashmap value is a string and the "needGaresh" parameter is true.
	// input: hashmap with a single key-value pair ("id" - "123456"), and the "withDbName" parameter is true.
	// Expected Result: string "id='123456' ".
	@Test
	void buildConditionPartWithAndTest_ValueHashMapIsStringNeedGareshIsTrue() {
		String result;
		HashMap<String, Object> condition=new HashMap<String, Object>() ;
		condition.put("id","123456");
		result=DB_controller.buildConditionPartWithAnd(condition,true);
		assertEquals("id='123456' ",result);
	}

	// Description: verify the behavior of the buildConditionPartWithAnd() method in the DB_controller class when the hashmap key is an integer. The "withDbName" parameter is set to true.
	// Input: HashMap key is "id" with value 123456, needGaresh=true.
	// Expected Result: The string "id=123456 ".
	@Test
	void buildConditionPartWithAndTest_KeyHashMapIsIntegerAndNeedGareshIsTrue() {
		String result;
		HashMap<String, Object> condition=new HashMap<String, Object>() ;
		condition.put("id",123456);
		result=DB_controller.buildConditionPartWithAnd(condition,true);
		assertEquals("id=123456 ",result);
	}

	// Description: verify the behavior of the buildConditionPartWithAnd() method in the DB_controller class when the hashmap key is an string. The "withDbName" parameter is set to true.
	// Input: HashMap key is "id" with value "123456", needGaresh=true.
	// Expected Result: The string "id=123456 ".
	@Test
	void buildConditionPartWithAndTest_ValueHashMapIsStringNeedGareshIsFalse() {
		String result;
		HashMap<String, Object> condition=new HashMap<String, Object>() ;
		condition.put("id","123456");
		result=DB_controller.buildConditionPartWithAnd(condition,false);
		assertEquals("id=123456 ",result);
	}

	// description: verify the behavior of the buildConditionPartWithComma() method in the DB_controller class when the input hashmap is null.
	// input: list=null.
	// Expected Result: empty string.
	@Test
	void buildConditionPartWithCommaTest_NullHashMap() {
		String result;
		result=DB_controller.buildConditionPartWithComma(null);
		assertEquals("",result);
	}
	
	// Description: verify the behavior of the buildConditionPartWithComma() method in the DB_controller class when the hashmap key is an integer.
	// Input: HashMap key is "id" with value 123456.
	// Expected Result: The string "id=123456".
	@Test
	void buildConditionPartWithCommaTest_KeyHashMapIsInteger() {
		String result;
		HashMap<String, Object> condition=new HashMap<String, Object>() ;
		condition.put("id",123456);
		result=DB_controller.buildConditionPartWithComma(condition);
		assertEquals("id=123456",result);
	}

	// Description: verify the behavior of the buildConditionPartWithComma() method in the DB_controller class when the hashmap key is an string.
	// Input: HashMap key is "id" with value "123456".
	// Expected Result: The string "id=123456".
	@Test
	void buildConditionPartWithCommaTest_ValueHashMapIsString() {
		String result;
		HashMap<String, Object> condition=new HashMap<String, Object>() ;
		condition.put("id","123456");
		result=DB_controller.buildConditionPartWithComma(condition);
		assertEquals("id='123456'",result);
	}
}