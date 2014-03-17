package se.sogeti.umea.cvconverter.adapter.persistence.file;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.sogeti.umea.cvconverter.application.Repository;

/**
 * Repository for storing uploaded files.
 * 
 * The file repository stores the file binary data (locally or remote) and a
 * record of the stored file.
 * 
 * @author joparo
 * 
 */
public class FileRepository {

	private final static Logger LOG = LoggerFactory
			.getLogger(FileRepository.class);

	@Inject
	@Repository
	private FileRecordRepository recordRepository;

	@Inject
	@Repository
	private FileBinaryRepository binaryRepository;

	/**
	 * Creates a file from file upload.
	 * 
	 * @param fileInputStream
	 *            the file upload input stream.
	 * @param name
	 *            the name of the file.
	 * @param foreignKey
	 *            a foreignKey to this file record.
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
	public void deleteFile(int id) {
		FileRecord fr = recordRepository.getFileRecord(id);
		if (fr != null) {
			recordRepository.deleteFileRecord(id);
			LOG.debug("Deleted file record " + fr + ". Checking if the binary file is unused ...");
			deleteBinaryFileIfUnused(fr);
		} else {
			LOG.warn("Trying to delete non-existent file record with id: " + id);
		}
	}

	private void deleteBinaryFileIfUnused(FileRecord fr) {
		List<FileRecord> fileRecords = recordRepository.listFileRecords(null);

		for (FileRecord record : fileRecords) {
			if (record.getUrl().equals(fr.getUrl())) {
				LOG.debug("Found a file record with matching url: " + record + " The binary will not be deleted.");
				return;
			}
		}
		binaryRepository.deleteFile(fr.getUrl(), fr.getType());

	}

	/**
	 * Gets a file record with the specified id.
	 * 
	 * @param id
	 *            the id of the file record.
	 * @return the complete file record.
	 */
	public FileRecord getFile(int id) {
		return recordRepository.getFileRecord(id);
	}

}
