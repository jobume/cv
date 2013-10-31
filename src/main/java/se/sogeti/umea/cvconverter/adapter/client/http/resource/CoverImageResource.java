package se.sogeti.umea.cvconverter.adapter.client.http.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.sogeti.umea.cvconverter.application.Image;

import com.sun.jersey.core.header.FormDataContentDisposition;

@Path("/coverimage")
public class CoverImageResource extends ImageResource {

	private final static Logger LOG = LoggerFactory
			.getLogger(ImageResource.class);

	@Override
	public Image createImage(InputStream uploadedInputStream,
			FormDataContentDisposition fileDetail) {

		try {
			service.createCoverImage(fileDetail.getFileName());
		} catch (IOException e) {
			LOG.error("Error creating cover image!", e);
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}

		return super.createImage(uploadedInputStream, fileDetail);
	}

	@GET
	@Produces("application/json")
	public List<Image> readImages() {
		List<Image> coverImages;
		try {
			coverImages = service.getCoverImages();

			if (coverImages != null) {
				for (Image img : coverImages) {
					img.setUrl(imageDecorator.getImageUrl(img.getName()));
				}
			}

			return coverImages;

		} catch (IllegalArgumentException | NoSuchElementException
				| IOException e) {
			LOG.error("Error getting images.", e);
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
	}
}