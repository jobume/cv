package se.sogeti.umea.cvconverter.adapter.client.http.resource;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import se.sogeti.umea.cvconverter.adapter.client.http.json.CurriculumVitaeImpl;
import se.sogeti.umea.cvconverter.adapter.client.http.json.ProfileImpl;
import se.sogeti.umea.cvconverter.adapter.persistence.file.FileRecord;
import se.sogeti.umea.cvconverter.adapter.persistence.file.FileRepository;
import se.sogeti.umea.cvconverter.application.ConverterService;
import se.sogeti.umea.cvconverter.application.Image;
import se.sogeti.umea.cvconverter.application.JsonCvRepository;
import se.sogeti.umea.cvconverter.application.Profile;

import com.sun.jersey.core.header.FormDataContentDisposition;

public class PortraitResourceTest {

	@Mock
	FormDataContentDisposition fileDetail;
	@Mock
	InputStream uploadedInputStream;
	@Mock
	FileRepository mockRepo;

	@Mock
	ConverterService mockService;

	@Mock
	CvResource mockCvResource;
	
	@Mock
	JsonCvRepository mockCvRepository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldSetPortraitUrlToRandomString()
			throws JsonGenerationException, JsonMappingException, IOException {
		PortraitResource res = new PortraitResource();
		res.fileRepo = mockRepo;
		res.service = mockService;
		res.cvResource = mockCvResource;
		res.cvRepository = mockCvRepository;

		int cvId = 42;
		ObjectMapper mapper = new ObjectMapper();
		CurriculumVitaeImpl cv = new CurriculumVitaeImpl();
		cv.setId(cvId);
		String jsonCv = mapper.writer().writeValueAsString(cv);

		when(mockCvRepository.getCv(cvId)).thenReturn(jsonCv);

		String fileName = setMockFileDetailToReturnFilename();

		String randomUrl = setMockRepoToReturnFilerecordWithNewIdAndRandomUrl();

		Image image = res.createImage(uploadedInputStream, fileDetail, cvId);
		assertEquals(fileName, image.getName());
		assertEquals(randomUrl, image.getUrl());
	}

	@Test
	public void shouldUpdateCvWhenPortraitIsUpdated()
			throws JsonGenerationException, JsonMappingException, IOException {
		PortraitResource res = new PortraitResource();
		res.fileRepo = mockRepo;
		res.service = mockService;
		res.cvResource = mockCvResource;
		res.cvRepository = mockCvRepository;

		setMockRepoToReturnFilerecordWithNewIdAndRandomUrl();

		String fileName = setMockFileDetailToReturnFilename();

		int cvId = 42;

		ObjectMapper mapper = new ObjectMapper();

		CurriculumVitaeImpl cv = cvWithExistingPortraitImage(cvId);

		String jsonCv = mapper.writer().writeValueAsString(cv);

		when(mockCvRepository.getCv(cvId)).thenReturn(jsonCv);

		res.createImage(uploadedInputStream, fileDetail, cvId);

		setCvPortraitIdToExpectedUpdatedValue(cv, fileName);

		Mockito.verify(mockCvResource, Mockito.times(1)).updateCv(42,
				mapper.writer().writeValueAsString(cv));
	}

	@Test
	public void shouldUpdateCvWhenPortraitIsCreated()
			throws JsonGenerationException, JsonMappingException, IOException {
		PortraitResource res = new PortraitResource();
		res.fileRepo = mockRepo;
		res.service = mockService;
		res.cvResource = mockCvResource;
		res.cvRepository = mockCvRepository;

		setMockRepoToReturnFilerecordWithNewIdAndRandomUrl();

		String fileName = setMockFileDetailToReturnFilename();

		int cvId = 42;

		ObjectMapper mapper = new ObjectMapper();

		CurriculumVitaeImpl cv = cvWithoutExistingPortraitImage(cvId);

		String jsonCv = mapper.writer().writeValueAsString(cv);

		when(mockCvRepository.getCv(cvId)).thenReturn(jsonCv);

		res.createImage(uploadedInputStream, fileDetail, cvId);

		setCvPortraitIdToExpectedUpdatedValue(cv, fileName);

		Mockito.verify(mockCvResource, Mockito.times(1)).updateCv(42,
				mapper.writer().writeValueAsString(cv));
	}

	private CurriculumVitaeImpl cvWithoutExistingPortraitImage(int cvId) {
		CurriculumVitaeImpl cv = new CurriculumVitaeImpl();
		cv.setId(cvId);
		Profile profile = new ProfileImpl();
		cv.setProfile(profile);
		return cv;
	}

	private void setCvPortraitIdToExpectedUpdatedValue(CurriculumVitaeImpl cv,
			String fileName) {
		cv.getProfile().setPortrait(
				new Image(111, fileName,
						"http://localhost:8080/cv/images/XYZ.png"));
	}

	private CurriculumVitaeImpl cvWithExistingPortraitImage(int cvId) {
		CurriculumVitaeImpl cv = new CurriculumVitaeImpl();
		cv.setId(cvId);
		Profile profile = new ProfileImpl();
		Image portrait = new Image(36, "john_doe",
				"http://localhost:8080/cv/image/cv_portait_42.png");
		profile.setPortrait(portrait);
		cv.setProfile(profile);
		return cv;
	}

	private String setMockFileDetailToReturnFilename() {
		String fileName = "Jonas Paro Highres.png";
		when(fileDetail.getFileName()).thenReturn(fileName);
		return fileName;
	}

	private String setMockRepoToReturnFilerecordWithNewIdAndRandomUrl() {
		final String randomUrl = "http://localhost:8080/cv/images/XYZ.png";
		when(
				mockRepo.createFile(Mockito.any(InputStream.class),
						anyString(), anyString())).thenAnswer(
				new Answer<FileRecord>() {
					public FileRecord answer(InvocationOnMock invocation) {
						Object[] args = invocation.getArguments();
						return new FileRecord(111, (String) args[1],
								"portrait", randomUrl);
					}
				});
		return randomUrl;
	}

}
