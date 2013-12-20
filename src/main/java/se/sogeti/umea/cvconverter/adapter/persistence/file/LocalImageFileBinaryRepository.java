package se.sogeti.umea.cvconverter.adapter.persistence.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.sogeti.umea.configuration.InjectedConfiguration;
import se.sogeti.umea.cvconverter.application.Repository;

@Repository
public class LocalImageFileBinaryRepository implements FileBinaryRepository {

	private static final Logger LOG = LoggerFactory.getLogger(LocalImageFileBinaryRepository.class);
	
	@Inject
	@InjectedConfiguration(key = "file.location", defaultValue = "/opt/jetty/home/images")
	private String fileLocationOnDisk;

	@Inject
	@InjectedConfiguration(key = "file.path", defaultValue = "images")
	private String publicFilePath;

	@Inject
	@InjectedConfiguration(key = "host.name", defaultValue = "http://localhost:8080")
	private String hostName;

	@Override
	public String createFile(InputStream fileInputStream, String name,
			String type) {
		saveToFile(fileInputStream, fileLocationOnDisk + "/" + name);
		return hostName + "/" + publicFilePath + "/" + name;
	}

	@Override
	public void deleteFile(String name, String type) {
		File file = new File(fileLocationOnDisk + "/" + name);
		file.delete();
	}

	private void saveToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {
		LOG.debug("Saving file to disk at : " + uploadedFileLocation);
		try (OutputStream out = new FileOutputStream(new File(
				uploadedFileLocation))) {
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
		} catch (IOException e) {
			LOG.error("Error writing file.", e);
		}
	}

	public String getFileLocationOnDisk() {
		return fileLocationOnDisk;
	}
	
}
