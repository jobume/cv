package se.sogeti.umea.cvconverter.application.impl.fopwrapper;

import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

public class ClasspathUriResolver implements URIResolver {
	
	@Override
	public Source resolve(String href, String base) throws TransformerException {
		System.out.println("Custom resolver firing for base: " + base + "\n and href: " + href);
		Source source = null;
		InputStream inputStream = ClassLoader.getSystemResourceAsStream(href);
		if (inputStream != null) {
			source = new StreamSource(inputStream);
		}
		return source;
	}
}
