package se.sogeti.umea.cvconverter.adapter.client.http.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
import se.sogeti.umea.cvconverter.application.ConverterService;
import se.sogeti.umea.cvconverter.application.CurriculumVitae;
import se.sogeti.umea.cvconverter.application.CvOverview;
import se.sogeti.umea.cvconverter.application.JsonCvRepository;
import se.sogeti.umea.cvconverter.application.Repository;
import se.sogeti.umea.cvconverter.application.TagCloud;
import se.sogeti.umea.cvconverter.application.UserService;

import com.sun.jersey.multipart.FormDataMultiPart;

@Path("jsoncv")
public class CvResource extends Resource {

	private final static Logger LOG = LoggerFactory.getLogger(CvResource.class);

	@Inject
	private UserService userService;

	@Inject
	private PortraitResource portraitResource;

	@Inject
	private CoverImageResource coverImageResource;

	@Inject
	@Repository
	private JsonCvRepository cvRepository;	

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response parseRtfToJson(FormDataMultiPart multiPartRequest,
			@Context HttpServletRequest req) throws IllegalArgumentException,
			IOException {

		String userName = req.getRemoteUser();
		String office;
		if (userName != null) {
			office = userService.getOfficeForUser(userName);
		} else {
			throw new WebApplicationException(Response
					.status(Status.UNAUTHORIZED).entity("No user in request!")
					.build());
		}
		InputStream uploadedInputStream = multiPartRequest
				.getField("rtfCvFile").getValueAs(InputStream.class);
		String rtfCv = StreamUtil.readStreamToString(uploadedInputStream,
				"ISO-8859-1");

		CurriculumVitae cv = null;
		try {
			cv = service.parseRtf(rtfCv);
			cv.setOffice(office);
			int id = cvRepository.createCv(cv);
			cv.setId(id);
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

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/json")
	public Response createCv(String jsonCv) {		

		ObjectMapper mapper = new ObjectMapper();
		CurriculumVitaeImpl cv = null;
		try {
			cv = mapper.readValue(jsonCv, CurriculumVitaeImpl.class);

			cv = setCoverImageIfExists(cv);

			int id = cvRepository.createCv(cv);
			cv.setId(id);

			jsonCv = mapper.writer().writeValueAsString(cv);
		} catch (IOException e) {
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}

		return Response.ok(jsonCv).build();
	}

	public void deleteUnusedPortraitFiles(int portraitId) {
		if (portraitIsUnused(portraitId)) {
			portraitResource.deleteImage(portraitId);
		}
	}

	private boolean portraitIsUnused(int portraitId) {
		return portraitId > 0 && cvRepository.countPortraitIds(portraitId) == 0;
	}

	private CurriculumVitaeImpl setCoverImageIfExists(CurriculumVitaeImpl cv)
			throws IOException {
		if (cv != null && cv.getCoverImage() != null
				&& cv.getCoverImage().getId() > 0) {
			cv.setCoverImage(coverImageResource.getCoverImage(cv
					.getCoverImage().getId()));
		}
		return cv;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CvOverview> getCvs(@Context HttpServletRequest req) {
		String userName = req.getRemoteUser();
		if (userName == null) {
			LOG.info("No user with getRemoteUser()");
			throw new WebApplicationException(Response
					.status(Status.UNAUTHORIZED).entity("No user in request!")
					.build());
		}
		String office = userService.getOfficeForUser(userName);
		try {
			return cvRepository.listCvs(office);
		} catch (IOException io) {
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(io.getMessage()).build());
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCv(@PathParam(value = "id") int id) {

		String jsonCv;
		try {
			CurriculumVitaeImpl cv = getCvOrThrowNotFound(id);
			jsonCv = new ObjectMapper().writer().writeValueAsString(cv);
		} catch (IOException e) {
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}

		return Response.ok(jsonCv).build();
	}

	private CurriculumVitaeImpl getCvOrThrowNotFound(int id) throws IOException {
		CurriculumVitaeImpl cv = (CurriculumVitaeImpl) cvRepository.getCv(id);
		if (cv == null) {
			throw new WebApplicationException(Response.status(Status.NOT_FOUND)
					.entity("The CV with id " + id + " does not exist.").build());			
		} else {
			return cv;
		}
	}

	@PUT
	@Path("/partial/{id}")
	public Response updateName(@PathParam(value = "id") int id, String name) {
		CurriculumVitaeImpl cv;
		try {
			cv = (CurriculumVitaeImpl) cvRepository.getCv(id);
			cv.setName(name);
			cvRepository.updateCv(cv);
		} catch (IOException e) {
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}

		return Response.ok().build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response updateCv(@PathParam(value = "id") int id, String jsonCv) {		

		ObjectMapper mapper = new ObjectMapper();
		CurriculumVitaeImpl oldCv;
		CurriculumVitaeImpl newCv;
		try {
			oldCv = getCvOrThrowNotFound(id);
			newCv = mapper.readValue(jsonCv, CurriculumVitaeImpl.class);
			newCv = setCoverImageIfExists(newCv);

			int oldPortraitId = hasPortraitId(oldCv);
			int newPortraitId = hasPortraitId(newCv);

			cvRepository.updateCv(newCv);
			if (oldPortraitId != newPortraitId) {
				deleteUnusedPortraitFiles(oldPortraitId);
			}

			return Response.ok(mapper.writeValueAsString(newCv)).build();
			
		} catch (IOException e) {
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}

		
	}

	private int hasPortraitId(CurriculumVitaeImpl cv) {
		if (cv.getProfile() != null && cv.getProfile().getPortrait() != null
				&& cv.getProfile().getPortrait().getId() > 0) {
			return cv.getProfile().getPortrait().getId();
		}
		return -1;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response generateTagCloud(String jsonCv) {

		ObjectMapper mapper = new ObjectMapper();
		CurriculumVitaeImpl cv;

		try {
			cv = mapper.readValue(jsonCv, CurriculumVitaeImpl.class);
			cv = setCoverImageIfExists(cv);
			createTagCloud(cv);
			cvRepository.updateCv(cv);
			jsonCv = mapper.writer().writeValueAsString(cv);
		} catch (IOException e) {
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}

		return Response.ok(jsonCv).build();
	}

	private CurriculumVitaeImpl createTagCloud(CurriculumVitaeImpl cv) {
		try {
			TagCloud cloud = new TagCloud(cv);
			cloud.generateTags();
			cv.setTags(cloud.getTags());

		} catch (Throwable t) {
			LOG.error("Unknown error updating cv with tag cloud", t);
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(t.getMessage()).build());
		}
		return cv;
	}

	@DELETE
	@Path("/{id}")
	public Response deleteCv(@PathParam(value = "id") int id) {
		try {
			CurriculumVitaeImpl cvToDelete = getCvOrThrowNotFound(id);

			cvRepository.deleteCv(id);

			int portraitId = hasPortraitId(cvToDelete);
			deleteUnusedPortraitFiles(portraitId);

		} catch (IllegalArgumentException | NoSuchElementException e) {
			LOG.error("Error deleting cv (id=" + id + ").", e);
			throw new WebApplicationException(Response.status(Status.NOT_FOUND)
					.entity(e.getMessage()).build());
		} catch (IOException e) {
			LOG.error("Error deleting cv (id=" + id + ").", e);
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}

		return Response.ok().build();
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setConverterService(ConverterService service) {
		this.service = service;
	}

	public void setPortraitResource(PortraitResource portraitResource) {
		this.portraitResource = portraitResource;
	}

	public void setCvRepository(JsonCvRepository cvRepository) {
		this.cvRepository = cvRepository;
	}

}