package se.sogeti.umea.cvconverter.application.impl.cvparser;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

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

import se.sogeti.umea.cvconverter.application.ContentLanguage;
import se.sogeti.umea.cvconverter.application.impl.cvparser.model.CurriculumVitaeImpl;
import se.sogeti.umea.cvconverter.application.impl.cvparser.model.JobImpl;
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
	public void canParseFile() throws IOException, FOPException,
			ConfigurationException, TransformerException, SAXException {

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
	}

	@Test
	@Ignore
	public void writePdfFromRtf() throws FOPException, ConfigurationException,
			TransformerException, SAXException, IOException {
		String fileAsString = FileReader.readFile(FILE_NAME_TOBIAS, "UTF-8");

		StringBuilder dd = new DocumentParser().parseDocument(new RtfParser(
				new RTFEditorKit()).parseRtf(fileAsString));

		CurriculumVitaeImpl TobiasAsCv = new StringParser().parseString(
				ContentLanguage.SWEDISH, "", "", dd);

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

		((JobImpl) TobiasAsCv.getEngagements().get(1)).setImportant(Boolean.TRUE);;
		
		
		XMLOutputFactory xmlof = XMLOutputFactory.newInstance();
		XmlGenerator xmlGenerator = new XmlGenerator(xmlof);
		String cvXml = null;
		try {
			cvXml = xmlGenerator.generateXml(TobiasAsCv, "UTF-8");
		} catch (XMLStreamException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

		String filename = "C:\\Users\\joparo\\Documents\\PDFs\\test_tobias.pdf";
		try (FileOutputStream fileOutput = new FileOutputStream(filename);
				ByteArrayOutputStream out = new ByteArrayOutputStream(10000)) {

			String cvXsl = FileReader
					.readFile("src\\main\\webapp\\WEB-INF\\data\\cv-generator.xsl");

			// Generate PDF
			Source xmlData = new StreamSource(new StringReader(cvXml));
			Source xslStylesheetData = new StreamSource(new StringReader(cvXsl));

			FopWrapperFactory.getFopWrapper().transform(xmlData,
					xslStylesheetData, "application/pdf", out);

			fileOutput.write(out.toByteArray());
			System.out.println("File written!");
		}
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
