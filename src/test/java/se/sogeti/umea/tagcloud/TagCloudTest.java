package se.sogeti.umea.tagcloud;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import se.sogeti.umea.cvconverter.application.CurriculumVitae;
import se.sogeti.umea.cvconverter.application.Image;
import se.sogeti.umea.cvconverter.application.Profile;
import se.sogeti.umea.cvconverter.application.Skill;
import se.sogeti.umea.cvconverter.application.TagCloud;
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

		List<String> personalQualities = new ArrayList<>();
		personalQualities.add("Passionate");
		personalQualities.add("Ambitious");
		personalQualities.add("Driven");
		personalQualities.add("Outgoing");
		personalQualities.add("Service Minded");
		when(mockCv.getPersonalQualities()).thenReturn(personalQualities);
		
		TagCloud cloud = new TagCloud(mockCv);
		cloud.generateTags();
		Assert.assertEquals(8, cloud.getTags().size());
	}
}