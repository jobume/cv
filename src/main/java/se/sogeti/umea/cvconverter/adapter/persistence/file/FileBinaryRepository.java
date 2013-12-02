package se.sogeti.umea.cvconverter.adapter.persistence.file;

import java.io.InputStream;

public interface FileBinaryRepository {

	/**
	 * Saves a file (locally or remote). Returns the url to the file.
	 * 
	 * @param fileInputStream
	 *            the file data to save.
	 * @param name
	 *            the name of the file.
	 * @param type
	 *            the type name of the file.
	 * 
	 * @return the url to the saved file resource.
	 */
	public String createFile(InputStream fileInputStream, String name,
			String type);

	/**
	 * Deletes the specified file.
	 * 
	 * @param name
	 *            the file name to delete.
	 * @param type
	 *            the type name of the file to delete.
	 */
	public void deleteFile(String name, String type);

}
