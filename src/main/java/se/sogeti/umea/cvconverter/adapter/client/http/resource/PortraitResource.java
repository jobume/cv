package se.sogeti.umea.cvconverter.adapter.client.http.resource;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.sogeti.umea.cvconverter.adapter.persistence.file.FileRecord;
import se.sogeti.umea.cvconverter.adapter.persistence.file.FileRepository;
import se.sogeti.umea.cvconverter.application.Image;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/portrait")
public class PortraitResource extends Resource {

	private final static Logger LOG = LoggerFactory
			.getLogger(CoverImageResource.class);

	private final static String TYPE_NAME = "portrait";

	// @Inject
	// @Repository
	// FileBinaryRepository binaryRepo;

	@Inject
	FileRepository fileRepo;

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Image createImage(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("fileName") String cvName) {

		String fileNameAndType = fileDetail.getFileName();
		String fileType = getFileType(fileNameAndType);
		String fileName = createSafeFileName(cvName, fileType);

		// String url = binaryRepo.createFile(uploadedInputStream, fileName,
		// TYPE_NAME);
		FileRecord record = fileRepo.createFile(uploadedInputStream, fileName,
				TYPE_NAME);
		String url = record.getUrl();

		LOG.debug("File " + fileNameAndType + " saved as " + fileName
				+ " with url: " + url);

		return new Image(fileName, url);
	}

	@GET
	@Produces("application/json")
	public List<Image> listImages() {
		List<FileRecord> records = fileRepo.listFiles(TYPE_NAME);
		List<Image> images = new ArrayList<>();
		for (FileRecord record : records) {
			images.add(new Image(record.getName(), record.getUrl()));
		}
		return images;
	}

	@DELETE
	@Path("/{id}")
	public void deleteImage(@PathParam("id") String name) {
		try {
			fileRepo.deleteFile(name, TYPE_NAME);
		} catch (Throwable e) {
			LOG.error("Error deleting portrait image with name: " + name);
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}
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
					.status(Status.BAD_REQUEST).entity(errorMessage).build());
		}
		String fileType = fileNameAndTypeArray[1];
		return fileType;
	}

	private String createSafeFileName(String cvName, String fileType) {
		try {
			return new URI(null, cvName, null).toASCIIString().replaceAll("%",
					"")
					+ "." + fileType;
		} catch (URISyntaxException e) {
			LOG.error(e.getMessage());
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}
	}

}
