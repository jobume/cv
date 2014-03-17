package se.sogeti.umea.cvconverter.application.impl.cvparser;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
import org.junit.After;
import org.junit.Test;
import org.xml.sax.SAXException;

import se.sogeti.umea.cvconverter.application.ContentLanguage;
import se.sogeti.umea.cvconverter.application.impl.cvparser.model.CurriculumVitaeImpl;
import se.sogeti.umea.cvconverter.application.impl.cvparser.model.JobImpl;
import se.sogeti.umea.cvconverter.application.impl.fopwrapper.FopWrapperFactory;
import testutil.FileReader;

public class ParserIntegrationTest {
	
	private final static String FILE_NAME_INPUT = "ParserIntegrationTest_Input.rtf";
	
	private final static String FILE_NAME_OUTPUT = "ParserIntegrationTest_Output.pdf";

	/**
	 * Change this to preserve the output file on disk.
	 */
	private boolean deleteFileOnExit = true;
	
	@After
	public void cleanUpFiles() {
		File file = new File(FILE_NAME_OUTPUT);
		if(file.exists() && deleteFileOnExit) {
			file.delete();
		} 
	}
	
	@Test
	public void canBeCreated() throws Exception {
		Parser p = new Parser(null, null, null, null);
		assertNotNull(p);
	}

	@Test
	public void writePdfFromRtf() throws FOPException, ConfigurationException,
			TransformerException, SAXException, IOException {
		String fileAsString = FileReader.readFile(FILE_NAME_INPUT,
				this.getClass());

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

		((JobImpl) TobiasAsCv.getEngagements().get(1))
				.setImportant(Boolean.TRUE);			

		XMLOutputFactory xmlof = XMLOutputFactory.newInstance();
		XmlGenerator xmlGenerator = new XmlGenerator(xmlof);
		String cvXml = null;
		try {
			cvXml = xmlGenerator.generateXml(TobiasAsCv, "UTF-8");
		} catch (XMLStreamException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

		File outputFile = new File(FILE_NAME_OUTPUT);		
		try (FileOutputStream fileOutput = new FileOutputStream(outputFile);
				ByteArrayOutputStream out = new ByteArrayOutputStream(10000)) {

			String cvXsl = FileReader
					.readFile("src\\main\\webapp\\WEB-INF\\data\\cv-generator.xsl");

			// Generate PDF
			Source xmlData = new StreamSource(new StringReader(cvXml));
			Source xslStylesheetData = new StreamSource(new StringReader(cvXsl));

			FopWrapperFactory.getFopWrapper().transform(xmlData,
					xslStylesheetData, "application/pdf", out);

			fileOutput.write(out.toByteArray());			
		}
		
		Assert.assertTrue(outputFile.exists());
	}

}
