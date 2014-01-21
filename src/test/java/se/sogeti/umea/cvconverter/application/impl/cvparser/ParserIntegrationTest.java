package se.sogeti.umea.cvconverter.application.impl.cvparser;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.swing.text.rtf.RTFEditorKit;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;

import junit.framework.Assert;

import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.fop.apps.FOPException;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import se.sogeti.umea.cvconverter.adapter.client.http.resource.CvDecorator;
import se.sogeti.umea.cvconverter.application.ContentLanguage;
import se.sogeti.umea.cvconverter.application.Tag;
import se.sogeti.umea.cvconverter.application.impl.cvparser.model.CurriculumVitaeImpl;
import se.sogeti.umea.cvconverter.application.impl.fopwrapper.FopWrapperFactory;
import testutil.FileReader;

public class ParserIntegrationTest {

	private static final String FILE_NAME_XSL = "src\\main\\webapp\\WEB-INF\\data\\standard-stylesheet-v1.xsl";

	private final static String FILE_NAME_THOMAS = "C:\\Users\\joparo\\Projects\\cv-converter-origin\\cv-converter\\rtf-samples\\Thomas Hämälä.rtf";

	private final static String FILE_NAME_ROBERT = "C:\\Users\\joparo\\Projects\\cv-converter-origin\\cv-converter\\rtf-samples\\Robert Olsson.rtf";

	private final static String FILE_NAME_RICHARD = "C:\\Users\\joparo\\Projects\\cv-converter-origin\\cv-converter\\rtf-samples\\Richard Ekblom.rtf";

	private final static String FILE_NAME_TOBIAS = "C:\\Users\\joparo\\Projects\\cv-converter-origin\\cv-converter\\rtf-samples\\Tobias Ohlsson.rtf";	

	@Test
	public void canBeCreated() throws Exception {
		Parser p = new Parser(null, null, null, null);
		assertNotNull(p);
	}

	@Test
	@Ignore
	public void canParseFile() throws IOException {

		CurriculumVitaeImpl RichardAsCv = new StringParser().parseString(
				ContentLanguage.ENGLISH, null, null, new DocumentParser()
						.parseDocument(new RtfParser(new RTFEditorKit())
								.parseRtf(FileReader
										.readFile(FILE_NAME_RICHARD))));

		Assert.assertEquals("Richard Ekblom", RichardAsCv.getProfile()
				.getName());

		CurriculumVitaeImpl RobertAsCv = new StringParser().parseString(
				ContentLanguage.SWEDISH, null, null,
				new DocumentParser().parseDocument(new RtfParser(
						new RTFEditorKit()).parseRtf(FileReader
						.readFile(FILE_NAME_ROBERT))));

		Assert.assertEquals("Robert Olsson", RobertAsCv.getProfile().getName());

		CurriculumVitaeImpl ThomasAsCv = new StringParser().parseString(
				ContentLanguage.SWEDISH, null, null,
				new DocumentParser().parseDocument(new RtfParser(
						new RTFEditorKit()).parseRtf(FileReader
						.readFile(FILE_NAME_THOMAS))));

		Assert.assertEquals("Thomas Hämälä", ThomasAsCv.getProfile().getName());

		String fileAsString = FileReader.readFile(FILE_NAME_TOBIAS, "UTF-8");

		StringBuilder dd = new DocumentParser().parseDocument(new RtfParser(
				new RTFEditorKit()).parseRtf(fileAsString));

		CurriculumVitaeImpl TobiasAsCv = new StringParser().parseString(
				ContentLanguage.SWEDISH, null, null, dd);

		Assert.assertEquals("Tobias Ohlsson", TobiasAsCv.getProfile().getName());

		String expectedDescription = "Testledare och testare."
				+ (System.getProperty("line.separator"))
				+ "Projektets syfte var att lyfta kundens Microsoft-baserade servermiljö och därtill hörande systemlösningar till senaste aktuella versioner."
				+ (System.getProperty("line.separator"))
				+ "Rollen som testledare innebar att ansvara för teststrategier, kvalitetssäkring samt processer för ändrings- och felhantering och handledning av tre andra testare. Arbetet skedde i nära samarbete med kunden för att kvalitetssäkra hela vägen till acceptanstest och leverans. Testmetodiken TMap NEXT användes och bl.a. genomfördes en produktriskanalys för en effektivare testprocess. Utifrån produktriskanalysen genomfördes utforskande regressionstester."
				+ (System.getProperty("line.separator"))
				+ "Verktyg/miljöer: MS TFS, MS Office.";

		Assert.assertEquals(expectedDescription, TobiasAsCv.getEngagements()
				.get(1).getDescription());
		Assert.assertEquals(expectedDescription, TobiasAsCv.getEngagements()
				.size(), 8);

	}

	@Ignore
	@Test
	public void canWritePdf() throws IllegalArgumentException, IOException,
			XMLStreamException, FOPException, ConfigurationException,
			TransformerException, SAXException {

		String outFileName = "C:\\Users\\joparo\\Documents\\PDFs\\test_fonts_pof12.pdf";
		try (ByteArrayOutputStream out = new ByteArrayOutputStream(10000);
				FileOutputStream fileOutput = new FileOutputStream(outFileName)) {

			CurriculumVitaeImpl RichardAsCv = new StringParser().parseString(
					ContentLanguage.ENGLISH, "print date",
					"http://portraiturl.com", new DocumentParser()
							.parseDocument(new RtfParser(new RTFEditorKit())
									.parseRtf(FileReader
											.readFile(FILE_NAME_RICHARD))));

			List<Tag> tags = RichardAsCv.getTags();

			tags.add(new Tag("Expert", false, false, false, Tag.Size.MEDIUM,
					CvDecorator.Font.MYRIAD.getName()));
			tags.add(new Tag("EPiServer.NET", false, false, false,
					Tag.Size.MEDIUM, CvDecorator.Font.MYRIAD.getName()));
			tags.add(new Tag("Architect", false, false, false, Tag.Size.MEDIUM,
					CvDecorator.Font.MYRIAD.getName()));
			tags.add(new Tag("Microsoft Biztalk Server", false, false, false,
					Tag.Size.MEDIUM, CvDecorator.Font.MYRIAD.getName()));
			tags.add(new Tag("Passionate", false, false, false,
					Tag.Size.MEDIUM, CvDecorator.Font.MYRIAD.getName()));
			tags.add(new Tag("Framework 3.5", false, false, false,
					Tag.Size.MEDIUM, CvDecorator.Font.MYRIAD.getName()));
			tags.add(new Tag("Sharepoint 2007", false, false, false,
					Tag.Size.MEDIUM, CvDecorator.Font.MYRIAD.getName()));
			tags.add(new Tag("Ambitious", false, false, false, Tag.Size.MEDIUM,
					CvDecorator.Font.MYRIAD.getName()));

			new CvDecorator().decorateTags(tags);

			tags.add(new Tag("Myriad Regular", false, false, false,
					Tag.Size.MEDIUM, CvDecorator.Font.MYRIAD.getName()));
			tags.add(new Tag("Myriad Bold", true, false, false,
					Tag.Size.MEDIUM, CvDecorator.Font.MYRIAD.getName()));
			tags.add(new Tag("Myriad BoldIt", true, false, true,
					Tag.Size.MEDIUM, CvDecorator.Font.MYRIAD.getName()));
			tags.add(new Tag("Myriad It", false, false, true, Tag.Size.MEDIUM,
					CvDecorator.Font.MYRIAD.getName()));
			tags.add(new Tag("Minion Regular", false, false, false,
					Tag.Size.MEDIUM, CvDecorator.Font.MINION.getName()));
			tags.add(new Tag("Minion Bold", true, false, false,
					Tag.Size.MEDIUM, CvDecorator.Font.MINION.getName()));
			tags.add(new Tag("Minion BoldIt", true, false, true,
					Tag.Size.MEDIUM, CvDecorator.Font.MINION.getName()));
			tags.add(new Tag("Minion It", false, false, true, Tag.Size.MEDIUM,
					CvDecorator.Font.MINION.getName()));
			tags.add(new Tag("Trebuchet", false, false, false, Tag.Size.MEDIUM,
					CvDecorator.Font.TREBUCHET.getName()));

			Source xslStylesheetData = new StreamSource(new StringReader(
					FileReader.readFile(FILE_NAME_XSL)));

			FopWrapperFactory.getFopWrapper().transform(
					new StreamSource(new StringReader(new XmlGenerator(
							XMLOutputFactory.newInstance()).generateXml(
							RichardAsCv, "UTF-8"))), xslStylesheetData,
					"application/pdf", out);

			fileOutput.write(out.toByteArray());
			System.out.println("File written!");
		}

	}

	@Ignore
	@Test
	public void canParseRtfToXml() throws Exception {

		// Injector injector = Guice.createInjector(new ParserModule());
		// Parser parser = injector.getInstance(Parser.class);
		//
		// String file = FileReader.readFile("test/eng-test-all-chapters.rtf");
		//
		// String xml = parser.parse(file, "ISO-8859-1");
		//
		// assertTrue(xml.startsWith("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>"));
		// assertTrue(xml.endsWith("University</Location></Acquisition></Educations></CurriculumVitae>"));
	}
}
