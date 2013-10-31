package se.sogeti.umea.cvconverter.adapter.client.http.resource;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
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

import com.sun.jersey.multipart.FormDataMultiPart;

@Path("rtfparser")
public class RtfParserResource extends Resource {

	private final static Logger LOG = LoggerFactory.getLogger(RtfParserResource.class);	
	
	@POST
	@Produces("application/json")
	public Response parseRtfToJson(FormDataMultiPart multiPartRequest)
			throws IllegalArgumentException, IOException {

		InputStream uploadedInputStream = multiPartRequest
				.getField("rtfCvFile").getValueAs(InputStream.class);
		String rtfCv = StreamUtil.readStreamToString(uploadedInputStream);

		CurriculumVitae cv = null;
		try {
			cv = service.parseRtf(rtfCv);			
		} catch (IllegalArgumentException | IOException e) {
			LOG.error("Error parsing RTF to JSON.", e);
			return Response.serverError().build();
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
			
			TagCloud cloud = new TagCloud(cv);
			cloud.generateTags();
			cv.setTags(cloud.getTags());
			
			cvJson = mapper.writer().writeValueAsString(cv);
			
		} catch (IOException e) {
			LOG.error("Error updating cv with tag cloud", e);
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}
		LOG.debug("Returning cv with word cloud");
		
		return Response.ok(cvJson).build();
	}

}
