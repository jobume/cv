package se.sogeti.umea.tagcloud;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import se.sogeti.umea.cvconverter.application.Acquisition;
import se.sogeti.umea.cvconverter.application.ContentLanguage;
import se.sogeti.umea.cvconverter.application.Image;
import se.sogeti.umea.cvconverter.application.CurriculumVitae;
import se.sogeti.umea.cvconverter.application.Job;
import se.sogeti.umea.cvconverter.application.Language;
import se.sogeti.umea.cvconverter.application.Profile;
import se.sogeti.umea.cvconverter.application.Skill;
import se.sogeti.umea.cvconverter.application.Tag;
import se.sogeti.umea.cvconverter.application.TagCloud;
import se.sogeti.umea.cvconverter.application.Tag.Size;
import se.sogeti.umea.cvconverter.application.impl.cvparser.model.AcquisitionImpl;
import se.sogeti.umea.cvconverter.application.impl.cvparser.model.JobImpl;
import se.sogeti.umea.cvconverter.application.impl.cvparser.model.LanguageImpl;
import se.sogeti.umea.cvconverter.application.impl.cvparser.model.SkillImpl;

@RunWith(MockitoJUnitRunner.class)
public class TagCloudTest {

	@Mock
	private CurriculumVitae mockCv;
	@Mock
	private Profile mockProfile;
	
	@Mock
	private Image mockImage;

	@Test
	public void testTagCloud() {
		

		// Mock CV language
		when(mockCv.getContentLanguage()).thenReturn(ContentLanguage.ENGLISH);

		// Mock print date
		when(mockCv.getPrintDate()).thenReturn("2013-01-07");

			
		
		// Mock profile
		when(mockImage.getUrl()).thenReturn("http://www.abc.com/url/to/picture/portrait.jpg");
		
		when(mockCv.getProfile()).thenReturn(mockProfile);
		when(mockProfile.getName()).thenReturn("Jonas Paro");
		when(mockProfile.getTitle()).thenReturn("Consultant");
		when(mockProfile.getDateOfBirth()).thenReturn("01-02-2013");
		when(mockProfile.getPortrait()).thenReturn(mockImage);

		// Mock description
		when(mockCv.getDescription()).thenReturn("This is the description.");

		// Mock engagements
		List<Job> engagements = new ArrayList<>();

		JobImpl job = new JobImpl();
		job.setDate("2007-03 - 2007-04");
		job.setName("Umeå Energi");
		job.setDescription("");
		job.setImportant(false);
		job.setDate("2007-03 - 2007-04");
		job.setName("Umeå Energi");
		job.setDescription("");
		job.setImportant(false);
		engagements.add(job);

		JobImpl job2 = new JobImpl();
		job2.setDate("2003-04 - 2003-05");
		job2.setName("SDC");
		job2.setDescription("A study to present pro's and con's with an object oriented approach and the use of Java for development of new software.");
		job2.setImportant(false);
		engagements.add(job2);
		when(mockCv.getEngagements()).thenReturn(engagements);

		// Mock professional knowledge
		List<Skill> knowledge = new ArrayList<>();

		Skill skill = new SkillImpl();
		skill.setName("Infrastructure: Systems Administration (UNIX)");
		skill.setLevel(Skill.Level.EXPERT);
		knowledge.add(skill);

		Skill skill2 = new SkillImpl();
		skill2.setName("Business Process Management");
		skill2.setLevel(Skill.Level.KNOWLEDGEABLE);
		knowledge.add(skill2);

		when(mockCv.getProfessionalKnowledge()).thenReturn(knowledge);

		// Mock technology
		List<Skill> technologies = new ArrayList<>();

		Skill tech = new SkillImpl();
		tech.setName("Middleware: Apache Tomcat");
		tech.setLevel(Skill.Level.EXPERIENCED);
		tech.setImportant(Boolean.TRUE);
		technologies.add(tech);
		

		Skill tech2 = new SkillImpl();
		tech2.setName("Programming Language: Scala");
		tech2.setLevel(Skill.Level.KNOWLEDGEABLE);
		tech2.setImportant(Boolean.TRUE);
		technologies.add(tech2);
		
		Skill tech3 = new SkillImpl();
		tech3.setName("Programming Language:");
		tech3.setLevel(Skill.Level.KNOWLEDGEABLE);
		tech3.setImportant(Boolean.TRUE);
		technologies.add(tech3);
		
		Skill tech4 = new SkillImpl();
		tech4.setName("Hey! I'm not important!");
		tech4.setLevel(Skill.Level.KNOWLEDGEABLE);
		tech4.setImportant(Boolean.FALSE);
		technologies.add(tech4);

		when(mockCv.getTechnologies()).thenReturn(technologies);

		// Mock industry knowledge
		List<Skill> industryKnowledge = new ArrayList<>();

		SkillImpl ind = new SkillImpl();
		ind.setName("Telecommunication: LTE/4G");
		ind.setLevel(Skill.Level.KNOWLEDGEABLE);
		industryKnowledge.add(ind);

		SkillImpl ind2 = new SkillImpl();
		ind2.setName("Telecommunications: 3G");
		ind2.setLevel(Skill.Level.KNOWLEDGEABLE);
		industryKnowledge.add(ind2);

		when(mockCv.getIndustryKnowledge()).thenReturn(industryKnowledge);

		// Mock certifications
		List<Skill> certifications = new ArrayList<>();

		SkillImpl cert = new SkillImpl();
		cert.setName("70-305 Web apps with MS Visual Basic.NET & VS.NET");
		cert.setLevel(Skill.Level.CERTIFIED);
		certifications.add(cert);

		SkillImpl cert2 = new SkillImpl();
		cert2.setName("70-320 Developing XML Web Services & Server Components");
		cert2.setLevel(Skill.Level.CERTIFIED);
		certifications.add(cert2);

		when(mockCv.getCertifications()).thenReturn(certifications);

		// Mock foreign languages
		List<Language> foreignLanguages = new ArrayList<>();

		LanguageImpl lang = new LanguageImpl();
		lang.setName("English");
		lang.setLevel(Language.Level.GOOD_COMMAND);
		foreignLanguages.add(lang);

		LanguageImpl lang2 = new LanguageImpl();
		lang2.setName("Swedish");
		lang2.setLevel(Language.Level.MOTHER_TONGUE);
		foreignLanguages.add(lang2);

		when(mockCv.getForeignLanguages()).thenReturn(foreignLanguages);

		// Mock courses
		List<Acquisition> courses = new ArrayList<>();

		courses.add(new AcquisitionImpl("1998-01 - 1998-12",
				"IT-arkitekt utbildning", ""));
		courses.add(new AcquisitionImpl("2000-10", "AD-IS Information Systems",
				"Umeå"));
		when(mockCv.getCourses()).thenReturn(courses);

		// Mock organisations
		List<String> organisations = new ArrayList<>();
		organisations.add("Sogeti Competence Network Microsoft");
		organisations.add("Dataföreningen");
		when(mockCv.getOrganisations()).thenReturn(organisations);

		// Mock employments
		List<Job> employments = new ArrayList<>();

		JobImpl emp = new JobImpl();
		emp.setDate("2002-01");
		emp.setName("Sogeti Sverige AB");
		emp.setDescription("Abcdef");
		emp.setImportant(false);
		employments.add(emp);

		JobImpl emp2 = new JobImpl();
		emp2.setDate("1995-08 - 2001-12");
		emp2.setName("Cap Gemini");
		emp2.setDescription("ghijkl");
		emp2.setImportant(false);
		employments.add(emp2);

		when(mockCv.getEmployments()).thenReturn(employments);

		// Mock educations
		List<Acquisition> educations = new ArrayList<>();
		educations.add(new AcquisitionImpl("1986-01 - 1988-01",
				"Computer studies (60 p)", "Umeå University"));
		educations.add(new AcquisitionImpl("1987-01 - 1988-12",
				"English (40 p)", ""));
		when(mockCv.getEducations()).thenReturn(educations);

		Image image = new Image("pl.PNG");
		image.setUrl("http://localhost:8080/images/pl.PNG");
		when(mockCv.getCoverImage()).thenReturn(image);

		List<Tag> tags = new ArrayList<>();
		tags.add(new Tag("Expert", true, true, false, Size.LARGE, "trebuchet"));
		tags.add(new Tag("Expert", true, false, true, Size.SMALL, "trebuchet"));
		tags.add(new Tag("Expert", false, true, true, Size.MEDIUM, "minion"));
		tags.add(new Tag("Expert", true, false, true, Size.LARGE, "myriad"));
		when(mockCv.getTags()).thenReturn(tags);

		List<String> personalQualities = new ArrayList<>();
		personalQualities.add("Passionate");
		personalQualities.add("Ambitious");
		personalQualities.add("Driven");
		personalQualities.add("Outgoing");
		personalQualities.add("Service Minded");
		when(mockCv.getPersonalQualities()).thenReturn(personalQualities);
		
		TagCloud cloud = new TagCloud(mockCv);
		cloud.generateTags();
		for(Tag tag : cloud.getTags()) {
			System.out.println(tag);
		}
	}
}