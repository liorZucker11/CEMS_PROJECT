package enteties;

import java.io.Serializable;

/**
 * The CemsFile class represents a file entity in the CEMS system.
 * It implements the Serializable interface for object serialization.
 */
public class CemsFile implements Serializable {

	private static final long serialVersionUID = 1L;
	private String Description = null;
	private String fileName = null;
	private int size = 0;
	public byte[] mybytearray;

	/**
	 * init mybytearray in size of the path
	 * 
	 * @param size size of path
	 */
	public void initArray(int size) {
		mybytearray = new byte[size];
	}

	/**
	 * Constructor
	 * 
	 * @param fileName path of file.
	 */
	public CemsFile(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * geter for file name.
	 * 
	 * @return fileName the name of the file
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * seter for file name.
	 * 
	 * @param fileName the name of the file.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * geter for Size of the path.
	 * 
	 * @return size of the path.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * seter for Size of the path.
	 * 
	 * @param size of the path.
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * geter for the array.
	 * 
	 * @return mybytearray array in lange of the path.
	 */
	public byte[] getMybytearray() {
		return mybytearray;
	}

	/**
	 * return the byte in the i location.
	 * 
	 * @param i
	 * @return byte
	 */
	public byte getMybytearray(int i) {
		return mybytearray[i];
	}

	/**
	 * set the mybytearray.
	 * 
	 * @param mybytearray
	 */
	public void setMybytearray(byte[] mybytearray) {

		for (int i = 0; i < mybytearray.length; i++)
			this.mybytearray[i] = mybytearray[i];
	}

	/**
	 * get the description.
	 * 
	 * @return Description
	 */
	public String getDescription() {
		return Description;
	}

	/**
	 * set the description.
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		Description = description;
	}
}