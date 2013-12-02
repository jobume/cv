package se.sogeti.umea.cvconverter.adapter.persistence.file;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import se.sogeti.umea.cvconverter.application.Repository;

/**
 * Repository for storing uploaded files.
 * 
 * The file repository stores the file binary data (locally or remote) and a record of the stored file.
 * 
 * @author joparo
 * 
 */
public class FileRepository {

	@Inject @Repository
	private FileRecordRepository recordRepository;
	
	@Inject @Repository
	private FileBinaryRepository binaryRepository;
	

	/**
	 * Creates a file from file upload.
	 * 
	 * @param fileInputStream
	 *            the file upload input stream.
	 * @param name
	 *            the name of the file.
	 * @param type
	 *            the type name of the file.
	 * 
	 * @return the url for the saved file resource.
	 */
	public FileRecord createFile(InputStream fileInputStream, String name,
			String type) {
		String url = binaryRepository.createFile(fileInputStream, name, type);
		return recordRepository.createFileRecord(name, type, url);		
	}


	/**
	 * Lists all file names for a specified type.
	 * 
	 * @param type
	 *            the type name of the file.
	 * @return a list of all file resources for the specified type.
	 */
	public List<FileRecord> listFiles(String type) {
		return recordRepository.listFileRecords(type);
	}

	/**
	 * Deletes a file with the specified name and type.
	 * 
	 * @param name
	 *            the name of the file to be deleted.
	 * 
	 * @param type
	 *            the type name of the file to be deleted.
	 */
	public void deleteFile(String name, String type) {
		binaryRepository.deleteFile(name, type);
		recordRepository.deleteFileRecord(name, type);
	}

}
