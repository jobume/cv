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
import se.sogeti.umea.cvconverter.application.CvOverview;
import se.sogeti.umea.cvconverter.application.TagCloud;
import se.sogeti.umea.cvconverter.application.UserService;

@Path("jsoncv")
public class CvResource extends Resource {

	private final static Logger LOG = LoggerFactory.getLogger(CvResource.class);
	
	@Inject
	private UserService userService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCv(String jsonCv) {
		LOG.debug("Creating (saving) jsonCv ... ");

		ObjectMapper mapper = new ObjectMapper();
		CurriculumVitaeImpl cv = null;
		try {
			cv = mapper.readValue(jsonCv, CurriculumVitaeImpl.class);
			int id = service.createCv(cv.getName(), cv.getOffice(), jsonCv);
			cv.setId(id);
			LOG.debug("Saved CV " + cv.getName() + " with id " + cv.getId());
			jsonCv = mapper.writer().writeValueAsString(cv);
		} catch (IOException e) {
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}

		return Response.ok(jsonCv).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CvOverview> getCvs(@Context HttpServletRequest req) {
		String userName = req.getRemoteUser();
		if(userName==null) {
			LOG.info("No user with getRemoteUser()");
			throw new WebApplicationException(Response
					.status(Status.UNAUTHORIZED)
					.entity("No user in request!").build());
		}
		String office = userService.getOfficeForUser(userName);
		try {
			LOG.debug("Listing CV:s for office: " + office);
			return service.listCvs(office);
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
			jsonCv = service.getCv(id);
		} catch (IOException e) {
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}

		return Response.ok(jsonCv).build();
	}

	@PUT
	@Path("/partial/{id}")
	public Response updateName(@PathParam(value= "id") int id, String name) {
		ObjectMapper mapper = new ObjectMapper();
		CurriculumVitaeImpl cv;
		try {
			String jsonCv = service.getCv(id);
			cv = mapper.readValue(jsonCv, CurriculumVitaeImpl.class);
			cv.setName(name);
			jsonCv = mapper.writer().writeValueAsString(cv);
			service.updateCv(id, cv.getName(), jsonCv);
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
		CurriculumVitaeImpl cv;
		try {
			cv = mapper.readValue(jsonCv, CurriculumVitaeImpl.class);

			LOG.debug("Updating jsonCv ... ");

			jsonCv = mapper.writer().writeValueAsString(cv);

			service.updateCv(id, cv.getName(), jsonCv);
		} catch (IOException e) {
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}

		return Response.ok(jsonCv).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response generateTagCloud(String jsonCv) {

		LOG.debug("Generating tag cloud ... ");

		ObjectMapper mapper = new ObjectMapper();
		CurriculumVitaeImpl cv;

		try {
			cv = mapper.readValue(jsonCv, CurriculumVitaeImpl.class);
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
		LOG.debug("Generating tag cloud ... ");

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
		LOG.debug("Returning cv with word cloud");
		return cv;
	}

	@DELETE
	@Path("/{id}")
	public Response deleteCv(@PathParam(value = "id") int id) {
		try {
			service.deleteCv(id);
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
}