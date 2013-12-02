package se.sogeti.umea.cvconverter.adapter.client.http.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.multipart.FormDataMultiPart;

import se.sogeti.umea.cvconverter.adapter.client.http.json.LayoutJson;
import se.sogeti.umea.cvconverter.adapter.client.http.json.LayoutOverviewJson;
import se.sogeti.umea.cvconverter.adapter.client.http.streamutil.StreamUtil;
import se.sogeti.umea.cvconverter.application.Layout;
import se.sogeti.umea.cvconverter.application.LayoutOverview;

@Path("/layouts")
public class LayoutResource extends Resource {

	private final static Logger LOG = LoggerFactory
			.getLogger(LayoutResource.class);

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLayout(@PathParam("id") long id) {

		Layout layout;
		try {
			layout = service.getLayout(id);
		} catch (IllegalArgumentException | NoSuchElementException e) {
			LOG.error("Error getting layout with id=" + id + ".", e);
			throw new WebApplicationException(Response.status(Status.NOT_FOUND)
					.entity(e.getMessage()).build());
		} catch (IOException e) {
			LOG.error("Error getting layout (id=" + id + ").", e);
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}

		return Response.ok().entity(layout).build();
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateLayout(@PathParam("id") long id, LayoutJson layout) {
		layout.setId(id);
		try {
			service.updateLayout(layout);
		} catch (IllegalArgumentException | NoSuchElementException e) {
			LOG.error("Error updating layout (id=" + id + ") with layout (id="
					+ (layout != null ? layout.getId() : "null") + ").", e);
			throw new WebApplicationException(Response.status(Status.NOT_FOUND)
					.entity(e.getMessage()).build());
		} catch (IOException e) {
			LOG.error("Error updating layout (id=" + id + ") with layout (id="
					+ (layout != null ? layout.getId() : "null") + ").", e);
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}

		return Response.ok().build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteCvConverter(@PathParam("id") long id) {
		try {
			service.deleteLayout(id);
		} catch (IllegalArgumentException | NoSuchElementException e) {
			LOG.error("Error deleting layout (id=" + id + ").", e);
			throw new WebApplicationException(Response.status(Status.NOT_FOUND)
					.entity(e.getMessage()).build());
		} catch (IOException e) {
			LOG.error("Error deleting layout (id=" + id + ").", e);
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}

		return Response.ok().build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLayouts() {

		List<LayoutOverview> layouts = null;
		try {
			layouts = service.getLayouts();
		} catch (IOException e) {
			LOG.error("Error listing layouts", e);
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}

		String requestUri = uriInfo.getRequestUri().toString();

		List<LayoutOverviewJson> jsonList = new ArrayList<>();
		for (LayoutOverview layout : layouts) {
			LayoutOverviewJson l = new LayoutOverviewJson();
			l.setId(layout.getId());
			l.setName(layout.getName());
			l.setHref(requestUri + "/" + layout.getId());
			jsonList.add(l);
		}

		return Response.ok().entity(jsonList).build();
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response createLayout(FormDataMultiPart multiPartRequest) {
		String name = multiPartRequest.getField("name").getValue();
		InputStream uploadedInputStream = multiPartRequest.getField(
				"xslStylesheet").getValueAs(InputStream.class);

		String xslStylesheet = StreamUtil
				.readStreamToString(uploadedInputStream);

		long createdId = 0;
		try {
			createdId = service.createLayout(name, xslStylesheet);
		} catch (IOException e) {
			LOG.error("Error creating layout (name="
					+ name
					+ ", xsl_content_length="
					+ (xslStylesheet != null ? xslStylesheet.length() + ""
							: "null") + ").");
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}

		String requestUri = uriInfo.getRequestUri().toString();
		URI uri = null;
		try {
			uri = new URI(requestUri + "/" + createdId);
		} catch (URISyntaxException e) {
			LOG.error("Error creating uri (requestUri=" + requestUri
					+ ", createdId=" + createdId + ").");
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}

		return Response.created(uri).build();
	}
}