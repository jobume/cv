package se.sogeti.umea.cvconverter.application.impl.fopwrapper;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.StringReader;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.junit.Ignore;
import org.junit.Test;

import testutil.FileReader;
import testutil.FontLoader;

public class FopWrapperTest {
	
	private static final String CONVERTER_RESULT_MEDIA_TYPE = "application/pdf";
	private static final String TEST_XML_FILE = "src\\test\\java\\test.xml";
	private static final String TEST_XSL_FILE = "src\\test\\java\\xsl-stylesheet.xml";

	@Test
	@Ignore
	public void testFopWrapper() throws Exception {
		String cvXml = FileReader.readFile(TEST_XML_FILE);
		String cvXsl = FileReader.readFile(TEST_XSL_FILE);

		// Generate PDF
		Source xmlData = new StreamSource(new StringReader(cvXml));
		Source xslStylesheetData = new StreamSource(new StringReader(cvXsl));
		ByteArrayOutputStream out = new ByteArrayOutputStream(10000);
		FopWrapperFactory.getFopWrapper().transform(xmlData, xslStylesheetData,
				CONVERTER_RESULT_MEDIA_TYPE, out);
		out.close();
	}

	@Test
	// @Ignore
	public void testWritePdf() throws Exception {

		FileOutputStream fileOutput = null;
		ByteArrayOutputStream out = null;
		try {
			String cvXml = FileReader.readFile("src\\test\\java\\test_daniel.xml");			
			String cvXsl = FileReader.readFile("src\\test\\java\\xsl-stylesheet-cover.xml");

			// Generate PDF
			Source xmlData = new StreamSource(new StringReader(cvXml));
			Source xslStylesheetData = new StreamSource(new StringReader(cvXsl));
			out = new ByteArrayOutputStream(10000);
			
			FopWrapperFactory.getFopWrapper().transform(xmlData,
					xslStylesheetData, CONVERTER_RESULT_MEDIA_TYPE, out);

			String filename = "C:\\Users\\joparo\\Documents\\test_fonts_pof1.pdf";
			fileOutput = new FileOutputStream(filename);
			fileOutput.write(out.toByteArray());
			System.out.println("File written!");
		} finally {
			try {
				fileOutput.close();
			} catch (Exception ignore) {
			}
			try {
				out.close();
			} catch (Exception ignore) {
			}
		}
	}

}