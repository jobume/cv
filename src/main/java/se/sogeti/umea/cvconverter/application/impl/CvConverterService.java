package se.sogeti.umea.cvconverter.application.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.NoSuchElementException;

import javax.inject.Inject;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.sogeti.umea.cvconverter.application.ConversionException;
import se.sogeti.umea.cvconverter.application.ConverterService;
import se.sogeti.umea.cvconverter.application.CurriculumVitae;
import se.sogeti.umea.cvconverter.application.Layout;
import se.sogeti.umea.cvconverter.application.LayoutRepository;
import se.sogeti.umea.cvconverter.application.Repository;
import se.sogeti.umea.cvconverter.application.impl.cvparser.Parser;
import se.sogeti.umea.cvconverter.application.impl.cvparser.XmlGenerator;
import se.sogeti.umea.cvconverter.application.impl.fopwrapper.FopWrapper;

public class CvConverterService implements ConverterService {

	private final static Logger LOG = LoggerFactory
			.getLogger(CvConverterService.class);
	private static final String ENCODING = "UTF-8";
	private static final String CONVERTER_RESULT_MEDIA_TYPE = "application/pdf";

	private LayoutRepository repo;
	private Parser parser;
	private FopWrapper fopWrapper;
	private XmlGenerator xmlGenerator;

	@Inject
	public CvConverterService(@Repository LayoutRepository repo, Parser parser,
			XmlGenerator xmlGenerator, FopWrapper fopWrapper) {
		this.repo = repo;
		this.parser = parser;
		this.xmlGenerator = xmlGenerator;
		this.fopWrapper = fopWrapper;
	}

	@Override
	public byte[] convert(long layoutId, CurriculumVitae cv)
			throws IllegalArgumentException, NoSuchElementException,
			IOException, ConversionException {

		// Get XSL stylesheet
		Layout layout = null;
		if (layoutId > 0) {
			LOG.debug("Getting layout: " + layout);
			layout = repo.getLayout(layoutId);
		} else {
			LOG.debug("Getting default layout.");
			layout = repo.getDefaultLayout();
		}

		String xmlCv = convertCvToXml(cv);

		ByteArrayOutputStream out = convertXmlCvToPdf(xmlCv, layout);

		return out.toByteArray();
	}

	@Override
	public CurriculumVitae parseRtf(String rtf)
			throws IllegalArgumentException, IOException {
		CurriculumVitae cv = null;

		cv = parser.parseToModel(rtf, ENCODING);

		return cv;
	}

	private String convertCvToXml(CurriculumVitae cv)
			throws UnsupportedEncodingException, IOException {
		String xmlCv;
		try {
			xmlCv = xmlGenerator.generateXml(cv, ENCODING);
		} catch (XMLStreamException e) {
			throw new IOException("Unable to generate XML!");
		}
		return xmlCv;
	}

	private ByteArrayOutputStream convertXmlCvToPdf(String cvXml, Layout layout)
			throws ConversionException {
		Source xmlData = new StreamSource(new StringReader(cvXml));
		Source xslStylesheetData = new StreamSource(new StringReader(
				layout.getXslStylesheet()));
		ByteArrayOutputStream out = new ByteArrayOutputStream(10000);
		try {
			fopWrapper.transform(xmlData, xslStylesheetData,
					CONVERTER_RESULT_MEDIA_TYPE, out);
		} catch (FOPException | TransformerException e) {
			throw new ConversionException(e);
		}
		return out;
	}

	/*
	 * @Override public long createLayout(String name, String xslStylesheet)
	 * throws IllegalArgumentException, IOException { return
	 * repo.createLayout(name, xslStylesheet); }
	 * 
	 * @Override public Layout getLayout(long layoutId) throws
	 * IllegalArgumentException, NoSuchElementException, IOException { return
	 * repo.getLayout(layoutId); }
	 * 
	 * @Override public List<LayoutOverview> getLayouts() throws IOException {
	 * return repo.getLayouts(); }
	 * 
	 * @Override public void updateLayout(Layout layout) throws
	 * IllegalArgumentException, NoSuchElementException, IOException {
	 * repo.updateLayout(layout); }
	 * 
	 * @Override public void deleteLayout(long layoutId) throws
	 * IllegalArgumentException, NoSuchElementException, IOException {
	 * repo.deleteLayout(layoutId); }
	 * 
	 * @Override public CurriculumVitae parseRtf(String rtf) throws
	 * IllegalArgumentException, IOException { CurriculumVitae cv = null;
	 * 
	 * cv = parser.parseToModel(rtf, ENCODING);
	 * 
	 * return cv; }
	 * 
	 * @Override public byte[] convert(long layoutId, CurriculumVitae cv) throws
	 * IllegalArgumentException, NoSuchElementException, IOException,
	 * ConversionException {
	 * 
	 * // Get XSL stylesheet Layout layout = null; if(layoutId > 0) {
	 * LOG.debug("Getting layout: " + layout); layout =
	 * repo.getLayout(layoutId); } else { LOG.debug("Getting default layout.");
	 * layout = repo.getDefaultLayout(); }
	 * 
	 * String xmlCv = convertCvToXml(cv);
	 * 
	 * ByteArrayOutputStream out = convertXmlCvToPdf(xmlCv, layout);
	 * 
	 * return out.toByteArray(); }
	 * 
	 * private String convertCvToXml(CurriculumVitae cv) throws
	 * UnsupportedEncodingException, IOException { String xmlCv; try { xmlCv =
	 * xmlGenerator.generateXml(cv, ENCODING); } catch (XMLStreamException e) {
	 * throw new IOException("Unable to generate XML!"); } return xmlCv; }
	 * 
	 * private ByteArrayOutputStream convertXmlCvToPdf(String cvXml, Layout
	 * layout) throws ConversionException { Source xmlData = new
	 * StreamSource(new StringReader(cvXml)); Source xslStylesheetData = new
	 * StreamSource(new StringReader( layout.getXslStylesheet()));
	 * ByteArrayOutputStream out = new ByteArrayOutputStream(10000); try {
	 * fopWrapper.transform(xmlData, xslStylesheetData,
	 * CONVERTER_RESULT_MEDIA_TYPE, out); } catch (FOPException |
	 * TransformerException e) { throw new ConversionException(e); } return out;
	 * }
	 * 
	 * @Override public Image createCoverImage(InputStream imgStream, String
	 * name) throws IOException { return
	 * coverImageRepo.createCoverImage(imgStream, name); }
	 * 
	 * @Override public List<Image> getCoverImages() throws
	 * IllegalArgumentException, NoSuchElementException, IOException { return
	 * coverImageRepo.getCoverImages(); }
	 * 
	 * @Override public void deleteCoverImage(String name) throws
	 * IllegalArgumentException, NoSuchElementException, IOException {
	 * coverImageRepo.deleteCoverImage(name); }
	 * 
	 * @Override public int createCv(String name, String office, int portraitId,
	 * String cv) throws IOException { return cvRepo.createCv(name, office,
	 * portraitId, cv); }
	 * 
	 * @Override public String getCv(int id) throws IOException { return
	 * cvRepo.getCv(id); }
	 * 
	 * @Override public void updateCv(int id, String name, int portraitId,
	 * String cv) throws IOException { cvRepo.updateCv(id, name, portraitId,
	 * cv); }
	 * 
	 * @Override public void deleteCv(int id) throws IOException {
	 * cvRepo.deleteCv(id); }
	 * 
	 * @Override public List<CvOverview> listCvs() throws IOException { return
	 * cvRepo.listCvs(); }
	 * 
	 * @Override public Layout getDefaultLayout() throws IOException { return
	 * repo.getDefaultLayout(); }
	 * 
	 * @Override public List<CvOverview> listCvs(String office) throws
	 * IOException { return cvRepo.listCvs(office); }
	 * 
	 * @Override public Image getCoverImage(int id) throws
	 * IllegalArgumentException, NoSuchElementException, IOException { return
	 * coverImageRepo.getCoverImage(id); }
	 * 
	 * @Override public int countPortraitIds(int portraitId) { return
	 * cvRepo.countPortraitIds(portraitId); }
	 */

}
