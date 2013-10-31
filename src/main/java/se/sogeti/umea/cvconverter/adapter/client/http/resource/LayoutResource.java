package se.sogeti.umea.cvconverter.adapter.client.http.resource;

import java.io.IOException;
import java.io.OutputStream;
import java.util.NoSuchElementException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.sogeti.umea.cvconverter.adapter.client.http.json.CurriculumVitaeImpl;
import se.sogeti.umea.cvconverter.adapter.client.http.json.LayoutJson;
import se.sogeti.umea.cvconverter.application.ConversionException;
import se.sogeti.umea.cvconverter.application.Layout;

import com.sun.jersey.multipart.FormDataMultiPart;

@Path("/layouts/{id}")
public class LayoutResource extends Resource {

	private final static Logger LOG = LoggerFactory
			.getLogger(LayoutResource.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLayout(@PathParam("id") long id) {

		Layout layout;
		try {
			layout = service.getLayout(id);
		} catch (IllegalArgumentException | NoSuchElementException e) {
			LOG.error("Error getting layout with id=" + id + ".", e);
			throw new WebApplicationException(Status.NOT_FOUND);
		} catch (IOException e) {
			LOG.error("Error getting layout (id=" + id + ").", e);
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}

		return Response.ok().entity(layout).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateLayout(@PathParam("id") long id, LayoutJson layout) {
		layout.setId(id);
		try {
			service.updateLayout(layout);
		} catch (IllegalArgumentException | NoSuchElementException e) {
			LOG.error("Error updating layout (id=" + id + ") with layout (id="
					+ (layout != null ? layout.getId() : "null") + ").", e);
			throw new WebApplicationException(Status.NOT_FOUND);
		} catch (IOException e) {
			LOG.error("Error updating layout (id=" + id + ") with layout (id="
					+ (layout != null ? layout.getId() : "null") + ").", e);
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}

		return Response.ok().build();
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({ "application/pdf" })
	@Deprecated
	public Response convertCv(@PathParam("id") final long id,
			FormDataMultiPart multiPartRequest) {

		// TODO Should "@FormParam("modifiedCv") String cvJson" be used instead?
		// @Consumes(MediaType.APPLICATION_FORM_URLENCODED)

		final String cvJson = multiPartRequest.getField("modifiedCv")
				.getValue();

		StreamingOutput stream = new StreamingOutput() {
			public void write(OutputStream output) throws IOException,
					WebApplicationException {
				byte[] convert = null;
				try {

					ObjectMapper mapper = new ObjectMapper();

					CurriculumVitaeImpl cv = mapper.readValue(cvJson,
							CurriculumVitaeImpl.class);

					convert = service.convert(id, cv);
				} catch (NoSuchElementException | IllegalArgumentException e) {
					LOG.error("Error converting cv (id=" + id + ").", e);
					throw new WebApplicationException(Status.NOT_FOUND);
				} catch (ConversionException | IOException e) {
					LOG.error(
							"Error converting cv (id="
									+ id
									+ ", cvJson_length="
									+ (cvJson != null ? cvJson.length()
											: "null") + ").", e);
					// throw new WebApplicationException(Response
					// .status(Status.INTERNAL_SERVER_ERROR)
					// .entity("System error message: " +
					// e.getMessage()).build());
					throw new WebApplicationException(
							Status.INTERNAL_SERVER_ERROR);
				}
				output.write(convert);
			}
		};

		// String filename = "Genererad-CV";
		// return Response
		// .ok()
		// .entity(stream)
		// .header("Content-Disposition",
		// "attachment; filename='" + filename + "'")
		// .header("Content-Type", "application/pdf").build();

		// TODO the output stream could be put
		
		return Response.ok().entity(stream).build();
	} 
	
	@DELETE
	public Response deleteCvConverter(@PathParam("id") long id) {
		try {
			service.deleteLayout(id);
		} catch (IllegalArgumentException | NoSuchElementException e) {
			LOG.error("Error deleting layout (id=" + id + ").", e);
			throw new WebApplicationException(Status.NOT_FOUND);
		} catch (IOException e) {
			LOG.error("Error deleting layout (id=" + id + ").", e);
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}

		return Response.ok().build();
	}
}