package se.sogeti.umea.cvconverter.adapter.client.http.resource;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.sogeti.umea.cvconverter.adapter.client.http.json.CurriculumVitaeImpl;
import se.sogeti.umea.cvconverter.adapter.client.http.streamutil.StreamUtil;
import se.sogeti.umea.cvconverter.application.CurriculumVitae;
import se.sogeti.umea.cvconverter.application.TagCloud;
import se.sogeti.umea.cvconverter.application.UserService;

import com.sun.jersey.multipart.FormDataMultiPart;

@Path("rtfparser")
public class RtfParserResource extends Resource {

	private final static Logger LOG = LoggerFactory
			.getLogger(RtfParserResource.class);

	@Inject
	private UserService userService;
	
	@POST
	@Produces("application/json")
	public Response parseRtfToJson(FormDataMultiPart multiPartRequest,
			@Context HttpServletRequest req)
			throws IllegalArgumentException, IOException {
		String userName = req.getRemoteUser();
		String office;
		if(userName != null) {
			office = userService.getOfficeForUser(userName);
		} else {
			throw new WebApplicationException(Response
					.status(Status.UNAUTHORIZED)
					.entity("No user in request!").build());
		}
		LOG.debug("Creating CV for user: " + userName + "");
		InputStream uploadedInputStream = multiPartRequest
				.getField("rtfCvFile").getValueAs(InputStream.class);
		LOG.debug("System file.encoding is: "
				+ System.getProperty("file.encoding"));
		LOG.debug("Reading file with: " + "ISO-8859-1");
		String rtfCv = StreamUtil.readStreamToString(uploadedInputStream,
				"ISO-8859-1");

		CurriculumVitae cv = null;
		try {
			cv = service.parseRtf(rtfCv);
			cv.setOffice(office);
		} catch (IllegalArgumentException | IOException e) {
			LOG.error("Error parsing RTF to JSON.", e);
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		} catch (Throwable t) {
			LOG.error("Unknown error parsing RTF to JSON.", t);
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(t.getMessage()).build());
		}

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.INDENT_OUTPUT, true);

		String cvJson = mapper.writer().writeValueAsString(cv);

		return Response.ok(cvJson).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response generateTagCloud(String jsonCv) {
		LOG.debug("Generating tag cloud ... ");

		ObjectMapper mapper = new ObjectMapper();

		CurriculumVitaeImpl cv;
		String cvJson;
		try {
			cv = mapper.readValue(jsonCv, CurriculumVitaeImpl.class);

			boolean noTagCloud = false;
			if (cv.getTags() == null) {
				noTagCloud = true;
			} else if (cv.getTags().size() == 0) {
				noTagCloud = true;
			}
			if (noTagCloud) {
				LOG.debug("CV did not have a word cloud. Generating cloud.");
				TagCloud cloud = new TagCloud(cv);
				cloud.generateTags();
				cv.setTags(cloud.getTags());
			} else {
				LOG.debug("CV had a word cloud. Not updating.");
			}

			cvJson = mapper.writer().writeValueAsString(cv);

		} catch (IOException e) {
			LOG.error("Error updating cv with tag cloud", e);
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		} catch (Throwable t) {
			LOG.error("Unknown error updating cv with tag cloud", t);
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(t.getMessage()).build());
		}
		LOG.debug("Returning cv with word cloud");

		return Response.ok(cvJson).build();
	}

}
