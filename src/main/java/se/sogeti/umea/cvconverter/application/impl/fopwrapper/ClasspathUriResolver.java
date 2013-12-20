package se.sogeti.umea.cvconverter.application.impl.fopwrapper;

import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClasspathUriResolver implements URIResolver {
	
	private final static Logger LOG = LoggerFactory.getLogger(ClasspathUriResolver.class);
	
	@Override
	public Source resolve(String href, String base) throws TransformerException {
		LOG.debug("Custom resolver firing for base: " + base + "\n and href: " + href);
		Source source = null;
		InputStream inputStream = getClass().getResourceAsStream(href);
		if (inputStream != null) {
			LOG.debug("Got stream resource for href: " + href);
			source = new StreamSource(inputStream);
		} else {
			LOG.debug("Did NOT get stream resource for href: " + href);
		}
		return source;
	}
}
