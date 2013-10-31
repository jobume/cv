package se.sogeti.umea.cvconverter.adapter.client.http.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.sogeti.umea.cvconverter.application.Image;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@RequestScoped
public class ImageResource extends Resource {

	private final static Logger LOG = LoggerFactory
			.getLogger(ImageResource.class);

	@Inject
	protected ImageDecorator imageDecorator;

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Image createImage(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {

		if (fileDetail.getFileName() == null) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}

		Image image = new Image(fileDetail.getFileName());
		imageDecorator.setImageUrls(image);
		
		String saveFileLocation = imageDecorator.getImageLocation(fileDetail
				.getFileName());
		LOG.debug("Saving file at: " + saveFileLocation);

		LOG.debug("Public file at: " + image.getUrl());
		LOG.debug("Local file at: " + image.getLocalUrl());
		LOG.debug("Image name: " + image.getName());

		// save it
		saveToFile(uploadedInputStream, saveFileLocation);

		return image;
	}

	private void saveToFile(InputStream uploadedInputStream,
			String uploadedFileLocation) {

		// OutputStream out = null;
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
}