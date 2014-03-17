package se.sogeti.umea.cvconverter.adapter.persistence.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.SecureRandom;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.sogeti.umea.configuration.InjectedConfiguration;
import se.sogeti.umea.cvconverter.application.Repository;

@Repository
public class LocalImageFileBinaryRepository implements FileBinaryRepository {

	public final static class FileNameGenerator {
		private SecureRandom random = new SecureRandom();

		public String generateFileName(String name) {
			String fileNameAndType = name;
			String fileType = getFileType(fileNameAndType);
			return new BigInteger(130, random).toString(32) + "." + fileType;
		}

		private String getFileType(String fileNameAndType) {
			if (fileNameAndType == null) {
				throw new WebApplicationException(Response
						.status(Status.BAD_REQUEST)
						.entity("Missing file name from request!").build());
			}
			String[] fileNameAndTypeArray = fileNameAndType.split("\\.");
			if (fileNameAndTypeArray.length < 2) {
				String errorMessage = "Could not get file type from "
						+ fileNameAndType + " Got array length "
						+ fileNameAndTypeArray.length;
				LOG.error(errorMessage);
				throw new WebApplicationException(Response
						.status(Status.BAD_REQUEST).entity(errorMessage)
						.build());
			}
			String fileType = fileNameAndTypeArray[1];
			return fileType;
		}
	}

	private static final Logger LOG = LoggerFactory
			.getLogger(LocalImageFileBinaryRepository.class);

	private final static FileNameGenerator fileNameGenerator = new FileNameGenerator();

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
		String fileName = fileNameGenerator.generateFileName(name);
		saveToFile(fileInputStream, fileLocationOnDisk + "/" + fileName);
		return hostName + "/" + publicFilePath + "/" + fileName;
	}

	@Override
	public void deleteFile(String url, String type) {		
		String name = getFileNameFromUrl(url);		
		File file = new File(fileLocationOnDisk + "/" + name);
		if (file.exists()) {
			file.delete();
		} else {			
			LOG.warn("File " + fileLocationOnDisk + "/" + name + " not found.");
		}
	}

	public String getFileNameFromUrl(String url) {
		return url.substring( url.lastIndexOf('/')+1, url.length() );		
	}

	public File getFile(String name) throws FileNotFoundException,
			UnsupportedEncodingException {
		File image = new File(this.getFileLocationOnDisk(), URLDecoder.decode(
				name, "UTF-8"));
		if (!image.exists()) {
			throw new FileNotFoundException("Did not find file: "
					+ image.getAbsolutePath());
		}
		return image;
	}

	private void saveToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {		
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
