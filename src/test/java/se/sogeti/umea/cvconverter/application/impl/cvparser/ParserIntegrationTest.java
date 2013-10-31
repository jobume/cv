package se.sogeti.umea.cvconverter.application.impl.cvparser;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;

public class ParserIntegrationTest {
	
	@Test
	public void canBeCreated() throws Exception {
		Parser p = new Parser(null, null, null, null);
		assertNotNull(p);
	}
	
	@Ignore
	@Test
	public void canParseRtfToXml() throws Exception {
		
//		Injector injector = Guice.createInjector(new ParserModule());
//		Parser parser = injector.getInstance(Parser.class);
//		
//		String file = FileReader.readFile("test/eng-test-all-chapters.rtf");
//		
//		String xml = parser.parse(file, "ISO-8859-1");
//		
//		assertTrue(xml.startsWith("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"));
//		assertTrue(xml.endsWith("University</Location></Acquisition></Educations></CurriculumVitae>"));
	}
}
