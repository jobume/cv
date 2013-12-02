package se.sogeti.umea.cvconverter.adapter.client.http.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;

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

import se.sogeti.umea.cvconverter.application.Image;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/coverimage")
public class CoverImageResource extends Resource {

	private final static Logger LOG = LoggerFactory
			.getLogger(CoverImageResource.class);

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

		try {
			return service.createCoverImage(uploadedInputStream, fileDetail
					.getFileName().replaceAll(" ", "_"));
		} catch (IOException e) {
			LOG.error("Error creating cover image!", e);
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}
	}

	@GET
	@Produces("application/json")
	public List<Image> readImages() {
		try {
			return service.getCoverImages();
		} catch (IllegalArgumentException | NoSuchElementException
				| IOException e) {
			LOG.error("Error getting images.", e);
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}
	}

	@DELETE
	@Path("/{id}")
	public void deleteImage(@PathParam("id") String name) {
		try {
			LOG.debug("Trying to delete cover image: " + name);
			service.deleteCoverImage(name);
		} catch (IllegalArgumentException | NoSuchElementException
				| IOException e) {
			LOG.error("Error deleting cover image with name: " + name);
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}

	}
}