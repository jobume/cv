package se.sogeti.umea.cvconverter.adapter.client.http.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.multipart.FormDataMultiPart;

import se.sogeti.umea.cvconverter.adapter.client.http.json.LayoutOverviewJson;
import se.sogeti.umea.cvconverter.adapter.client.http.streamutil.StreamUtil;
import se.sogeti.umea.cvconverter.application.LayoutOverview;

@Path("/layouts")
public class LayoutsResource extends Resource {

	private final static Logger LOG = LoggerFactory
			.getLogger(LayoutsResource.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLayouts() {
		
		List<LayoutOverview> layouts = null;
		try {
			layouts = service.getLayouts();
		} catch (IOException e) {
			LOG.error("Error listing layouts", e);
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
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
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}

		String requestUri = uriInfo.getRequestUri().toString();
		URI uri = null;
		try {
			uri = new URI(requestUri + "/" + createdId);
		} catch (URISyntaxException e) {
			LOG.error("Error creating uri (requestUri=" + requestUri + ", createdId=" + createdId + ").");
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}

		return Response.created(uri).build();
	}

}
