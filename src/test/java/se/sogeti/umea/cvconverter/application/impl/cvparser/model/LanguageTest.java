package se.sogeti.umea.cvconverter.application.impl.cvparser.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class LanguageTest {
	@Test
	public void canBeCreated() {
		assertNotNull(new LanguageImpl(null, null));
	}

//	@Test
//	public void canCreateMotherTongueLanguageLevelByString() throws Exception {
//		Language.Level expected = Language.Level.MOTHER_TONGUE;
//		LanguageImpl lang = new LanguageImpl();
//		Language.Level acutal = Language.("Mother Tongue");
//		assertEquals(expected, acutal);
//	}
//
//	@Test
//	public void canCreateMotherTongueLanguageLevelBySwedishName() throws Exception {
//		Language.Level expected = Language.Level.MOTHER_TONGUE;
//		Language.Level acutal = Language.Level.get("Modersmål");
//		assertEquals(expected, acutal);
//	}
}
