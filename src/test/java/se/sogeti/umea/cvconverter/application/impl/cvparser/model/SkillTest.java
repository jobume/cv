package se.sogeti.umea.cvconverter.application.impl.cvparser.model;

import static org.junit.Assert.*;
import org.junit.Test;


public class SkillTest {
	@Test
	public void canBeCreated() throws Exception {
		assertNotNull(new SkillImpl(null, null));
	}

//	@Test
//	public void canCreateExpertSkillLevelByString() throws Exception {
//		Skill.Level expected = Skill.Level.EXPERT;
//		Skill.Level acutal = Skill.Level.get("Expert");
//		assertEquals(expected, acutal);
//	}
//
//	@Test
//	public void canCreateSkillLevelBySwedishName() throws Exception {
//		Skill.Level expected = Skill.Level.KNOWLEDGEABLE;
//		Skill.Level acutal = Skill.Level.get("Baskunskap");
//		assertEquals(expected, acutal);
//
//		expected = Skill.Level.CERTIFIED;
//		acutal = Skill.Level.get("Certifierad");
//		assertEquals(expected, acutal);
//	}
}
