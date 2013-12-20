package se.sogeti.umea.cvconverter.application.impl.fopwrapper;

import java.io.IOException;

import javax.enterprise.inject.Produces;
import javax.xml.transform.TransformerFactory;

import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.fop.apps.FopFactory;
import org.xml.sax.SAXException;

public class FopWrapperFactory {

	
	
	@Produces
	public static FopFactory getFopFactory() throws ConfigurationException,
			SAXException, IOException {

		FopFactory fopFactory = FopFactory.newInstance();
		
		FopConfiguration.getInstance().setConfig(fopFactory);
		
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

}
