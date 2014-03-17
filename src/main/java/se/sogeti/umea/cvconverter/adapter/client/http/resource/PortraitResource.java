package se.sogeti.umea.cvconverter.adapter.client.http.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.sogeti.umea.cvconverter.adapter.client.http.json.CurriculumVitaeImpl;
import se.sogeti.umea.cvconverter.adapter.persistence.file.FileRecord;
import se.sogeti.umea.cvconverter.adapter.persistence.file.FileRepository;
import se.sogeti.umea.cvconverter.application.ConverterService;
import se.sogeti.umea.cvconverter.application.CurriculumVitae;
import se.sogeti.umea.cvconverter.application.Image;
import se.sogeti.umea.cvconverter.application.JsonCvRepository;
import se.sogeti.umea.cvconverter.application.Repository;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/portrait")
public class PortraitResource extends Resource {

	private final static Logger LOG = LoggerFactory
			.getLogger(CoverImageResource.class);

	private final static String TYPE_NAME = "portrait";	

	@Inject
	FileRepository fileRepo;

	@Inject
	ConverterService service;

	@Inject
	CvResource cvResource;
	
	@Inject
	@Repository
	JsonCvRepository cvRepository;	

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Image createImage(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("cvId") int cvId) throws JsonGenerationException,
			JsonMappingException, IOException {

		CurriculumVitae cv = getCv(cvId);
		
		FileRecord record = fileRepo.createFile(uploadedInputStream,
				fileDetail.getFileName(), TYPE_NAME);
		Image portrait = new Image(record.getId(), record.getName(),
				record.getUrl());

		if (cv.getProfile() != null) {
			cv.getProfile().setPortrait(portrait);
		}
		cvResource.updateCv(cvId, new ObjectMapper().writeValueAsString(cv));

		String url = record.getUrl();

		LOG.debug("File " + fileDetail.getFileName() + " saved with url: " + url);

		return portrait;
	}

	private CurriculumVitae getCv(int id) throws WebApplicationException {
		try {
			String jsonCv = cvRepository.getCv(id);
			if (jsonCv != null) {
				ObjectMapper mapper = new ObjectMapper();
				return mapper.readValue(jsonCv, CurriculumVitaeImpl.class);
			} else {
				throw new WebApplicationException(Response
						.status(Status.NOT_FOUND)
						.entity("Could not find cv with id : " + id).build());
			}
		} catch (IOException i) {
			throw new RuntimeException("Error getting cv with id: " + id);
		}

	}

	@GET
	@Produces("application/json")
	public List<Image> listImages() {
		List<FileRecord> records = fileRepo.listFiles(TYPE_NAME);
		List<Image> images = new ArrayList<>();
		for (FileRecord record : records) {
			images.add(new Image(record.getId(), record.getName(), record
					.getUrl()));
		}
		return images;
	}

	@DELETE
	@Path("/{id}")
	public void deleteImage(@PathParam("id") int id) {
		try {
			fileRepo.deleteFile(id);
		} catch (Throwable e) {
			LOG.error("Error deleting portrait image with id: " + id
					+ ". Error: " + e.getMessage());
			throw new WebApplicationException(Response
					.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build());
		}
	}

}
