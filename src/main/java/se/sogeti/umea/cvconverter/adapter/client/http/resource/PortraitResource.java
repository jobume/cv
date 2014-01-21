package se.sogeti.umea.cvconverter.adapter.client.http.resource;

import java.io.InputStream;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.sogeti.umea.cvconverter.adapter.persistence.file.FileBinaryRepository;
import se.sogeti.umea.cvconverter.application.Image;
import se.sogeti.umea.cvconverter.application.Repository;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/portrait")
public class PortraitResource extends Resource{

	private final static Logger LOG = LoggerFactory
			.getLogger(CoverImageResource.class);
	
	private final static String TYPE_NAME = "portrait";
	
	@Inject @Repository
	FileBinaryRepository binaryRepo;
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Image createImage(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail) {

		if (fileDetail.getFileName() == null) {
			throw new WebApplicationException(Response
					.status(Status.BAD_REQUEST)
					.entity("Missing file name from request!").build());
		}
		
		String fileName = fileDetail.getFileName().replaceAll(" ","_");
		
		String url = binaryRepo.createFile(uploadedInputStream, fileName, TYPE_NAME);
		LOG.debug("File created with url: " + url);
		
		return new Image(fileName, url, url);
	}
	
}
