package se.sogeti.umea.cvconverter.adapter.client.http.resource;

import java.io.IOException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.sogeti.umea.cvconverter.adapter.client.http.json.CurriculumVitaeImpl;
import se.sogeti.umea.cvconverter.application.ConverterService;
import se.sogeti.umea.cvconverter.application.CvOverview;
import se.sogeti.umea.cvconverter.application.JsonCvRepository;
import se.sogeti.umea.cvconverter.application.Repository;
import se.sogeti.umea.cvconverter.application.TagCloud;
import se.sogeti.umea.cvconverter.application.UserService;

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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCv(String jsonCv) {		

		ObjectMapper mapper = new ObjectMapper();
		CurriculumVitaeImpl cv = null;
		try {
			cv = mapper.readValue(jsonCv, CurriculumVitaeImpl.class);

			cv = setCoverImageIfExists(cv);

			int id = cvRepository.createCv(cv.getName(), cv.getOffice(),
					hasPortraitId(cv), mapper.writer().writeValueAsString(cv));
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
			jsonCv = cvRepository.getCv(id);
		} catch (IOException e) {
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}

		return Response.ok(jsonCv).build();
	}

	@PUT
	@Path("/partial/{id}")
	public Response updateName(@PathParam(value = "id") int id, String name) {
		ObjectMapper mapper = new ObjectMapper();
		CurriculumVitaeImpl cv;
		try {
			String jsonCv = cvRepository.getCv(id);
			cv = mapper.readValue(jsonCv, CurriculumVitaeImpl.class);
			cv.setName(name);
			jsonCv = mapper.writer().writeValueAsString(cv);
			cvRepository.updateCv(id, cv.getName(), hasPortraitId(cv), jsonCv);
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
			oldCv = mapper.readValue(cvRepository.getCv(id),
					CurriculumVitaeImpl.class);
			newCv = mapper.readValue(jsonCv, CurriculumVitaeImpl.class);
			newCv = setCoverImageIfExists(newCv);
			
			int oldPortraitId = hasPortraitId(oldCv);
			int newPortraitId = hasPortraitId(newCv);

			jsonCv = mapper.writer().writeValueAsString(newCv);

			cvRepository.updateCv(id, newCv.getName(), newPortraitId, jsonCv);
			if (oldPortraitId != newPortraitId) {
				deleteUnusedPortraitFiles(oldPortraitId);
			}

		} catch (IOException e) {
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}

		return Response.ok(jsonCv).build();
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
			String jsonCv = cvRepository.getCv(id);

			if (jsonCv == null) {
				throw new WebApplicationException(Response
						.status(Status.NOT_FOUND).entity("No CV for id: " + id)
						.build());
			}

			ObjectMapper mapper = new ObjectMapper();
			CurriculumVitaeImpl deletedCv = mapper.readValue(jsonCv,
					CurriculumVitaeImpl.class);
			cvRepository.deleteCv(id);

			int portraitId = hasPortraitId(deletedCv);
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