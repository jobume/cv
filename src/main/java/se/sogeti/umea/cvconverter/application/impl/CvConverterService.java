package se.sogeti.umea.cvconverter.application.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;

import se.sogeti.umea.cvconverter.application.ConversionException;
import se.sogeti.umea.cvconverter.application.ConverterService;
import se.sogeti.umea.cvconverter.application.CoverImageRepository;
import se.sogeti.umea.cvconverter.application.CurriculumVitae;
import se.sogeti.umea.cvconverter.application.Image;
import se.sogeti.umea.cvconverter.application.Layout;
import se.sogeti.umea.cvconverter.application.LayoutOverview;
import se.sogeti.umea.cvconverter.application.LayoutRepository;
import se.sogeti.umea.cvconverter.application.Repository;
import se.sogeti.umea.cvconverter.application.impl.cvparser.Parser;
import se.sogeti.umea.cvconverter.application.impl.cvparser.XmlGenerator;
import se.sogeti.umea.cvconverter.application.impl.fopwrapper.FopWrapper;

public class CvConverterService implements ConverterService {

	private static final String ENCODING = "UTF-8";
	private static final String CONVERTER_RESULT_MEDIA_TYPE = "application/pdf";
	private LayoutRepository repo;
	private CoverImageRepository coverImageRepo;
	private Parser parser;
	private FopWrapper fopWrapper;
	private XmlGenerator xmlGenerator;

	@Inject
	public CvConverterService(@Repository CoverImageRepository coverImageRepo,
			@Repository LayoutRepository repo, Parser parser,
			XmlGenerator xmlGenerator, FopWrapper fopWrapper) {
		this.repo = repo;
		this.parser = parser;
		this.xmlGenerator = xmlGenerator;
		this.fopWrapper = fopWrapper;
		this.coverImageRepo = coverImageRepo;
	}

	@Override
	public long createLayout(String name, String xslStylesheet)
			throws IllegalArgumentException, IOException {
		return repo.createLayout(name, xslStylesheet);
	}

	@Override
	public Layout getLayout(long layoutId) throws IllegalArgumentException,
			NoSuchElementException, IOException {
		return repo.getLayout(layoutId);
	}

	@Override
	public List<LayoutOverview> getLayouts() throws IOException {
		return repo.getLayouts();
	}

	@Override
	public void updateLayout(Layout layout) throws IllegalArgumentException,
			NoSuchElementException, IOException {
		repo.updateLayout(layout);
	}

	@Override
	public void deleteLayout(long layoutId) throws IllegalArgumentException,
			NoSuchElementException, IOException {
		repo.deleteLayout(layoutId);
	}

	@Override
	public CurriculumVitae parseRtf(String rtf)
			throws IllegalArgumentException, IOException {
		CurriculumVitae cv = null;

		cv = parser.parseToModel(rtf, ENCODING);

		return cv;
	}

	@Override
	public byte[] convert(long layoutId, CurriculumVitae cv)
			throws IllegalArgumentException, NoSuchElementException,
			IOException, ConversionException {

		// Get XSL stylesheet
		Layout layout = repo.getLayout(layoutId);

		String xmlCv = convertCvToXml(cv);

		ByteArrayOutputStream out = convertXmlCvToPdf(xmlCv, layout);

		return out.toByteArray();
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

	@Override
	public Image createCoverImage(InputStream imgStream, String name) throws IOException {
		return coverImageRepo.createCoverImage(imgStream, name);
	}

	@Override
	public List<Image> getCoverImages() throws IllegalArgumentException,
			NoSuchElementException, IOException {
		return coverImageRepo.getCoverImages();
	}

	@Override
	public void deleteCoverImage(String name) throws IllegalArgumentException,
			NoSuchElementException, IOException {
		coverImageRepo.deleteCoverImage(name);
	}

}
