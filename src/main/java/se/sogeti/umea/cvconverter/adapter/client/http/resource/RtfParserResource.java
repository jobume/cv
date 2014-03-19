package se.sogeti.umea.cvconverter.adapter.client.http.resource;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.sogeti.umea.cvconverter.adapter.client.http.streamutil.StreamUtil;
import se.sogeti.umea.cvconverter.application.CurriculumVitae;
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
		InputStream uploadedInputStream = multiPartRequest
				.getField("rtfCvFile").getValueAs(InputStream.class);		
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

}
