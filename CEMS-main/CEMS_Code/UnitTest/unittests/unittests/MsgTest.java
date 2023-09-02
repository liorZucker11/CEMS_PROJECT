package unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import communication.Msg;
import communication.MsgType;

public class MsgTest {
	
	private static Msg msgBeingTested;
	private static ArrayList<ArrayList<Object>> data;
	private static MyObject myObj1;
	private static MyObject myObj2;
	private static Integer int1;
	private static Integer int2;
	private static String str1;
	private static String str2;
	private static ArrayList<Object> tmpLst;
	private static ArrayList<MyObject> expected;
	private static ArrayList<MyObject> result;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		myObj1 = new MyObject(50, "name1");
		myObj2 = new MyObject(80, "name2");
		int1 = 50;
		int2 = 80;
		str1 = new String("name1");
		str2 = new String("name2");
	}
	
	@BeforeEach
	void setUpBeforeTest() throws Exception {
		msgBeingTested = new Msg(MsgType.data);
		msgBeingTested.setData(data);
		expected = new ArrayList<MyObject>();
		result = null;
	}
	
    // Define the DataObject class for testing on
    public static class MyObject {

		private Integer intVal;
        private String stringVal;
        
        // constructor 1:
        public MyObject() {
        }
        
        // constructor 2:
        public MyObject(Integer intVal) {
            this.intVal = intVal;
        }
        
        // constructor 3:
        public MyObject(String stringVal) {
            this.stringVal = stringVal;
        }

        // constructor 4:
        public MyObject(Integer intVal, String stringVal) {
            this.intVal = intVal;
            this.stringVal = stringVal;
        }
        
        // equals method:
		@Override
		public boolean equals(Object obj) {
			if (obj == null) return false;
			if (getClass() != obj.getClass()) return false;
			MyObject mo = (MyObject) obj;
			return (  (intVal.equals(mo.intVal)) && (stringVal.equals(mo.stringVal))  );
		}
        
        // toString method:
        public String toString() {
        	return "MyObject["+intVal.toString()+", "+stringVal+"]";
        }        
    }
    
    // Description: Test conversion when there is a matching constructor.
    // Input: data = {MyObject= {50, "name1"}}.
    // Expected Result: list in size of one of MyObject with the value of the input.
    @Test
    void ConvertDataTest_OneMatchingConstructor() {
        data = new ArrayList<ArrayList<Object>>();
        tmpLst = new ArrayList<Object>();
        tmpLst.add(int1);
        tmpLst.add(str1);
        data.add(tmpLst);
        expected.add(myObj1);
        msgBeingTested.setData(data);
        result = msgBeingTested.convertData(MyObject.class);
        assertEquals(expected, result);
    }
    
    // Description: Test conversion with multiple data rows.
    // Input: list of list {{int1, str1}, {int2, str2}}.
    // Expected Result: list of MyObject {{int1, str1}, {int2, str2}}.
    @Test
    void ConvertDataTest_MultipleDataRows() {
        data = new ArrayList<ArrayList<Object>>();
        tmpLst = new ArrayList<Object>();
        tmpLst.add(int1);
        tmpLst.add(str1);
        data.add(tmpLst);
        tmpLst = new ArrayList<Object>();
        tmpLst.add(int2);
        tmpLst.add(str2);
        data.add(tmpLst);
        expected.add(myObj1);
        expected.add(myObj2);
        msgBeingTested.setData(data);
        ArrayList<MyObject> result = msgBeingTested.convertData(MyObject.class);
        assertEquals(expected, result);
    }
	
    // Description: Test conversion of empty data list.
    // Input: msgBeingTested{type=data, empty data list}.
    // Expected Result: empty list of MyObject.
    @Test
    void ConvertDataTest_EmptyDataList() {
        data = new ArrayList<ArrayList<Object>>();
        msgBeingTested.setData(data);
        try{ result = msgBeingTested.convertData(MyObject.class);
        }catch(Exception e) {fail("exception.");}
        assertEquals(0, result.size());
    }

    // Description: Test conversion with multiple matching constructors.
    // Input: list {integer 50}.
    // Expected Result: same list as input.
    @Test
    void ConvertDataTest_MultipleMatchingConstructors() {
        data = new ArrayList<ArrayList<Object>>();
        tmpLst = new ArrayList<Object>();
        tmpLst.add(int1);
        data.add(tmpLst);
        expected.add(new MyObject(int1));
        msgBeingTested.setData(data);
        try{ result = msgBeingTested.convertData(MyObject.class);
        assertEquals(expected, result);
        }catch(Exception e) {fail("exception.");}
        assertTrue(true);
    }
    
    // Description: Test conversion with no matching constructors.
    // Input: list {String "name1"}.
    // Expected Result: empty list.
    @Test
    void ConvertDataTest_NoMatchingConstructor() {
        data = new ArrayList<ArrayList<Object>>();
        tmpLst = new ArrayList<Object>();
        tmpLst.add(str1);
        data.add(tmpLst);
        msgBeingTested.setData(data);
        result = msgBeingTested.convertData(MyObject.class);
        assertEquals(0, result.size());
    }
    
    // Description: Test conversion with invalid data type in data row.
    // Input: Invalid data type Integer list.
    // Expected Result: empty list.
    @Test
    void ConvertDataTest_InvalidDataType() {
        data = new ArrayList<ArrayList<Object>>();
        tmpLst = new ArrayList<Object>();
        tmpLst.add("invalid"); // Invalid data type, should be Integer
        data.add(tmpLst);
        msgBeingTested.setData(data);
        result = msgBeingTested.convertData(MyObject.class);
        assertEquals(0, result.size());
    }
    
    // Description: Test conversion with null data row.
    // Input: data list of null list.
    // Expected Result: empty list.
    @Test
    void ConvertDataTest_NullDataRow() {
    	try{
        data = new ArrayList<ArrayList<Object>>();
        ArrayList<Object> tmp = new ArrayList<Object>();
        data.add(tmp);
        msgBeingTested.setData(data);
        result = msgBeingTested.convertData(MyObject.class);
        assertEquals(0, result.size());
        }catch(Exception e) {fail("exception.");}
        assertTrue(true);
    }
    
    // Description: Test conversion with null parameter types.
    // Input: list of list{MyObject{int1, str1}}, use the method on general Object type.
    // Expected Result:empty list
    @Test
    void ConvertDataTest_NullParameterTypes() {
        data = new ArrayList<ArrayList<Object>>();
        tmpLst = new ArrayList<Object>();
        tmpLst.add(int1);
        tmpLst.add(str1);
        data.add(tmpLst);
        msgBeingTested.setData(data);
        try{ ArrayList<Object> result = msgBeingTested.convertData(Object.class);
        assertEquals(0, result.size());
        }catch(Exception e) {fail("exception.");} 
        assertTrue(true);
    }
}