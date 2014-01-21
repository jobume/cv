package se.sogeti.umea.cvconverter.application.impl.cvparser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import se.sogeti.umea.cvconverter.application.Acquisition;
import se.sogeti.umea.cvconverter.application.ContentLanguage;
import se.sogeti.umea.cvconverter.application.CurriculumVitae;
import se.sogeti.umea.cvconverter.application.Job;
import se.sogeti.umea.cvconverter.application.Language;
import se.sogeti.umea.cvconverter.application.Profile;
import se.sogeti.umea.cvconverter.application.Skill;
import testutil.FileReader;

// TODO verify that methods are in correct order

// TODO make more tests (see old testfile)
// cvAllChapterEng
// cvAllChapterSv
// cvMissingChaptersEng
// cvMissingChaptersSv
// ...?

public class StringParserTest {
	private static CurriculumVitae cvAllChaptersEng;

	private final static String FILE_NAME = "src\\test\\java\\cv-text-eng.txt";

	@BeforeClass
	public static void setUp() throws Exception {
		String fileText = FileReader.readFile(FILE_NAME); // non maven path:
															// "test\\cv-text-eng.txt"
		StringBuilder cvEngText = new StringBuilder(fileText);

		StringParser p = new StringParser();
		cvAllChaptersEng = p.parseString(ContentLanguage.ENGLISH, null, null,
				cvEngText);
	}

	@Test
	public void canBeCreated() {
		StringParser p = new StringParser();
		assertNotNull(p);
	}

	@Test
	public void canParseProfile() throws Exception {
		Profile profile = cvAllChaptersEng.getProfile();

		assertEquals("Richard Ekblom", profile.getName());
		assertEquals("Consultant", profile.getTitle());
		assertEquals("07-08-1974", profile.getDateOfBirth());
	}

	@Test
	public void canParseDescription() throws Exception {
		assertTrue(cvAllChaptersEng.getDescription().startsWith("Richard is a"));
		assertTrue(cvAllChaptersEng.getDescription().endsWith(
				"his assignments."));
	}

	/**
	 * Engagements 2012-08 - 2012-12 Tieto Sweden 2009-01 - 2011-04 Anchor
	 * Management Consulting
	 */
	@Test
	public void canParseEngagements() throws Exception {
		List<Job> engagements = cvAllChaptersEng.getEngagements();
		assertTrue(engagements.size() > 0);
		assertEquals("2012", engagements.get(0).getDate());
		assertEquals("2009 - 2011", engagements.get(1).getDate());
		assertEquals(4, engagements.get(0).getDuration());
		assertEquals(27, engagements.get(1).getDuration());
	}

	@Test
	public void canParseProfessionalKnowledge() throws Exception {
		assertFalse(cvAllChaptersEng.getProfessionalKnowledge().isEmpty());

		Skill firstSkill = cvAllChaptersEng.getProfessionalKnowledge().get(0);
		assertEquals("Test Driven Development", firstSkill.getName());
		assertEquals("Knowledgeable", firstSkill.getEnglishLevelName());

		Skill lastSkill = cvAllChaptersEng.getProfessionalKnowledge().get(2);
		assertEquals("Git", lastSkill.getName());
		assertEquals("Experienced", lastSkill.getEnglishLevelName());

		assertEquals(3, cvAllChaptersEng.getProfessionalKnowledge().size());
	}

	@Test
	public void canParseTechnology() throws Exception {
		assertFalse(cvAllChaptersEng.getTechnologies().isEmpty());

		Skill firstSkill = cvAllChaptersEng.getTechnologies().get(0);
		assertEquals("PostgreSql", firstSkill.getName());
		assertEquals("Knowledgeable", firstSkill.getEnglishLevelName());

		Skill lastSkill = cvAllChaptersEng.getTechnologies().get(4);
		assertEquals("Java", lastSkill.getName());
		assertEquals("Experienced", lastSkill.getEnglishLevelName());

		assertEquals(5, cvAllChaptersEng.getTechnologies().size());
	}

	@Test
	public void canParseIndustryKnowledge() throws Exception {
		assertFalse(cvAllChaptersEng.getIndustryKnowledge().isEmpty());

		Skill firstSkill = cvAllChaptersEng.getIndustryKnowledge().get(0);
		assertEquals("LTE/4G", firstSkill.getName());
		assertEquals("Knowledgeable", firstSkill.getEnglishLevelName());

		Skill lastSkill = cvAllChaptersEng.getIndustryKnowledge().get(2);
		assertEquals("GSM", lastSkill.getName());
		assertEquals("Knowledgeable", lastSkill.getEnglishLevelName());

		assertEquals(3, cvAllChaptersEng.getIndustryKnowledge().size());
	}

	@Test
	public void canParseCertifications() throws Exception {
		assertFalse(cvAllChaptersEng.getCertifications().isEmpty());

		Skill firstSkill = cvAllChaptersEng.getCertifications().get(0);
		assertEquals("SQS Prov", firstSkill.getName());
		assertEquals("Certified", firstSkill.getEnglishLevelName());

		Skill lastSkill = cvAllChaptersEng.getCertifications().get(2);
		assertEquals("70-320 Developing XML Web Services & Server Components",
				lastSkill.getName());
		assertEquals("Certified", lastSkill.getEnglishLevelName());

		assertEquals(3, cvAllChaptersEng.getCertifications().size());
	}

	@Test
	public void canParseForeignLanguages() throws Exception {
		assertFalse(cvAllChaptersEng.getForeignLanguages().isEmpty());

		Language firstLanguage = cvAllChaptersEng.getForeignLanguages().get(0);
		assertEquals("English", firstLanguage.getName());
		assertEquals("Good Command", firstLanguage.getEnglishLevelName());

		Language lastLanguage = cvAllChaptersEng.getForeignLanguages().get(1);
		assertEquals("Swedish", lastLanguage.getName());
		assertEquals("Mother Tongue", lastLanguage.getEnglishLevelName());

		assertEquals(2, cvAllChaptersEng.getForeignLanguages().size());
	}

	@Test
	public void canParseCourses() throws Exception {
		assertFalse(cvAllChaptersEng.getCourses().isEmpty());

		Acquisition firstCourse = cvAllChaptersEng.getCourses().get(0);
		assertEquals("1996-12", firstCourse.getDate());
		assertEquals("Windows System Architecture", firstCourse.getName());

		Acquisition lastCourse = cvAllChaptersEng.getCourses().get(3);
		assertEquals("1998-01 - 1998-12", lastCourse.getDate());
		assertEquals("IT-arkitekt utbildning", lastCourse.getName());

		assertEquals(4, cvAllChaptersEng.getCourses().size());
	}

	@Test
	public void canParseOrganisations() throws Exception {
		assertFalse(cvAllChaptersEng.getOrganisations().isEmpty());

		String firstOrganisation = cvAllChaptersEng.getOrganisations().get(0);
		assertEquals("Sogeti Competence Network Microsoft", firstOrganisation);

		assertEquals(1, cvAllChaptersEng.getOrganisations().size());
	}

	@Test
	public void canParseEmployements() throws Exception {
		assertFalse(cvAllChaptersEng.getEmployments().isEmpty());

		Job firstEmployment = cvAllChaptersEng.getEmployments().get(0);
		assertEquals("2011", firstEmployment.getDate());
		assertEquals("Sogeti Sverige AB", firstEmployment.getName());

		Job lastEmployment = cvAllChaptersEng.getEmployments().get(3);
		assertEquals("2003 - 2005", lastEmployment.getDate());
		assertEquals("Studentcentrum", lastEmployment.getName());

		assertEquals(4, cvAllChaptersEng.getEmployments().size());
	}

	@Test
	public void canParseEducations() throws Exception {
		assertFalse(cvAllChaptersEng.getEducations().isEmpty());

		Acquisition firstEducation = cvAllChaptersEng.getEducations().get(0);
		assertEquals("", firstEducation.getDate());
		assertEquals("Bachelor of Science in Computer Science",
				firstEducation.getName());
		assertEquals("Umeå University", firstEducation.getLocation());

		Acquisition lastEducation = cvAllChaptersEng.getEducations().get(2);
		assertEquals("1987-01 - 1988-12", lastEducation.getDate());
		assertEquals("English (40 p)", lastEducation.getName());
		assertEquals("Umeå University", lastEducation.getLocation());

		assertEquals(3, cvAllChaptersEng.getEducations().size());
	}

}
