package se.sogeti.umea.cvconverter.application.impl.fopwrapper;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.junit.Test;

import testutil.FileReader;

public class FopWrapperTest {
	
	private static final String CONVERTER_RESULT_MEDIA_TYPE = "application/pdf";
	private static final String TEST_XML_FILE = "FopWrapperTest_Input.xml";
	private static final String TEST_XSL_FILE = "xsl-stylesheet.xml";

	@Test	
	public void testFopWrapper() throws Exception {
		String cvXml = FileReader.readFile(TEST_XML_FILE, this.getClass());
		String cvXsl = FileReader.readFile(TEST_XSL_FILE, this.getClass());

		// Generate PDF
		Source xmlData = new StreamSource(new StringReader(cvXml));
		Source xslStylesheetData = new StreamSource(new StringReader(cvXsl));
		ByteArrayOutputStream out = new ByteArrayOutputStream(10000);
		FopWrapperFactory.getFopWrapper().transform(xmlData, xslStylesheetData,
				CONVERTER_RESULT_MEDIA_TYPE, out);
		out.close();
	}

}