package se.sogeti.umea.cvconverter.adapter.client.http.resource;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import se.sogeti.umea.cvconverter.adapter.client.http.json.CurriculumVitaeImpl;
import se.sogeti.umea.cvconverter.adapter.client.http.json.ProfileImpl;
import se.sogeti.umea.cvconverter.application.ConverterService;
import se.sogeti.umea.cvconverter.application.Image;
import se.sogeti.umea.cvconverter.application.JsonCvRepository;
import se.sogeti.umea.cvconverter.application.Profile;
import se.sogeti.umea.cvconverter.application.UserService;

public class CvResourceTest {

	@Mock
	PortraitResource portraitResourceMock;

	@Mock
	ConverterService serviceMock;

	@Mock
	JsonCvRepository jsonRepoMock;

	@Mock
	UserService userServiceMock;

	CvResource cvResource;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		cvResource = new CvResource();
		cvResource.setUserService(userServiceMock);
		cvResource.setConverterService(serviceMock);
		cvResource.setPortraitResource(portraitResourceMock);
		cvResource.setCvRepository(jsonRepoMock);
	}

	@Test
	public void testDeleteShouldDeleteUnusedPortrait() throws IOException {

		int cvPortraitId = 36;
		int cvId = 42;

		CurriculumVitaeImpl cv = createCvWithPortrait(cvPortraitId, cvId);

		setPortraitIdToUnused(cvPortraitId);
		Mockito.when(jsonRepoMock.getCv(cvId)).thenReturn(cv);

		cvResource.deleteCv(cvId);

		Mockito.verify(jsonRepoMock, Mockito.times(1)).countPortraitIds(
				cvPortraitId);
		Mockito.verify(portraitResourceMock, Mockito.times(1)).deleteImage(
				cvPortraitId);

	}

	@Test
	public void testDeleteShouldNotDeletePortraitIfUsedByOther()
			throws IOException {

		int cvPortraitId = 36;
		int cvId = 42;

		CurriculumVitaeImpl cv = createCvWithPortrait(cvPortraitId, cvId);

		setPortraitIdToUsed(cvPortraitId);
		Mockito.when(jsonRepoMock.getCv(cvId)).thenReturn(cv);

		cvResource.deleteCv(cvId);

		Mockito.verify(jsonRepoMock, Mockito.times(1)).countPortraitIds(
				cvPortraitId);
		Mockito.verify(portraitResourceMock, Mockito.times(0)).deleteImage(
				cvPortraitId);

	}

	@Test
	public void testUpdateShouldDeleteUnusedPortrait()
			throws JsonGenerationException, JsonMappingException, IOException {

		int cvPortraitId = 36;
		int cvId = 42;

		ObjectMapper mapper = new ObjectMapper();
		CurriculumVitaeImpl oldCv = createCvWithPortrait(cvPortraitId, cvId);

		setPortraitIdToUnused(cvPortraitId);

		Mockito.when(jsonRepoMock.getCv(cvId)).thenReturn(oldCv);

		CurriculumVitaeImpl newCv = createCvWithPortrait(235, cvId);

		cvResource.updateCv(cvId, mapper.writer().writeValueAsString(newCv));

		Mockito.verify(jsonRepoMock, Mockito.times(1)).countPortraitIds(
				cvPortraitId);
		Mockito.verify(portraitResourceMock, Mockito.times(1)).deleteImage(
				cvPortraitId);
	}

	@Test
	public void testUpdateShouldNotDeletePortraitIfUsedByOther()
			throws JsonGenerationException, JsonMappingException, IOException {
		int cvPortraitId = 36;
		int cvId = 42;

		ObjectMapper mapper = new ObjectMapper();
		CurriculumVitaeImpl oldCv = createCvWithPortrait(cvPortraitId, cvId);
		setPortraitIdToUsed(cvPortraitId);
		Mockito.when(jsonRepoMock.getCv(cvId)).thenReturn(oldCv);
		CurriculumVitaeImpl newCv = createCvWithPortrait(235, cvId);

		cvResource.updateCv(cvId, mapper.writer().writeValueAsString(newCv));

		Mockito.verify(jsonRepoMock, Mockito.times(1)).countPortraitIds(
				cvPortraitId);
		Mockito.verify(portraitResourceMock, Mockito.times(0)).deleteImage(
				cvPortraitId);
	}

	private CurriculumVitaeImpl createCvWithPortrait(int cvPortraitId, int cvId) {
		CurriculumVitaeImpl cv = new CurriculumVitaeImpl();
		cv.setId(cvId);
		Profile profile = new ProfileImpl();
		Image portrait = new Image(cvPortraitId, "john_doe",
				"http://localhost:8080/cv/image/cv_portait_42.png");
		profile.setPortrait(portrait);
		cv.setProfile(profile);
		return cv;
	}

	private void setPortraitIdToUnused(int portraitId) {
		Mockito.when(jsonRepoMock.countPortraitIds(portraitId)).thenReturn(0);
	}

	private void setPortraitIdToUsed(int portraitId) {
		Mockito.when(jsonRepoMock.countPortraitIds(portraitId)).thenReturn(1);
	}
}
