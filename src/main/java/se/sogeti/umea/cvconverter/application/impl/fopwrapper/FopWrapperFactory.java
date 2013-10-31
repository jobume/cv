package se.sogeti.umea.cvconverter.application.impl.fopwrapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.enterprise.inject.Produces;
import javax.xml.transform.TransformerFactory;

import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.fop.apps.FopFactory;
import org.xml.sax.SAXException;

public class FopWrapperFactory {

	private static final String FOP_CONFIG_FILE = "/userconfig.xml";

	private static final String FOP_FONTS_FOLDER = "fonts";

	@Produces
	public static FopFactory getFopFactory() throws ConfigurationException,
			SAXException, IOException {

		URL resource = FopFactory.class.getResource(FOP_CONFIG_FILE);
		if (resource == null) {
			throw new RuntimeException(
					"Could not find userconfig as classpath resource!");
		}

		FopFactory fopFactory = FopFactory.newInstance();
		fopFactory.setBaseURL("");
		fopFactory.getFontManager().setFontBaseURL(
				FopWrapperFactory.getUriRelativeToResource(resource.getFile()));
		fopFactory.setUserConfig(new File(resource.getFile()));

		return fopFactory;
	}

	@Produces
	public static TransformerFactory getTransformerFactory() {
		return TransformerFactory.newInstance();
	}

	public static FopWrapper getFopWrapper() throws ConfigurationException,
			SAXException, IOException {
		return new FopWrapper(getFopFactory(), getTransformerFactory());
	}

	private static String getUriRelativeToResource(String resourceUri) {
		String path = resourceUri.substring(0, resourceUri.lastIndexOf("/"));
		return path + "/" + FOP_FONTS_FOLDER;
	}
}
