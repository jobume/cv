package se.sogeti.umea.cvconverter.application.impl.fopwrapper;

import java.io.File;
import java.io.IOException;

import org.apache.fop.apps.FopFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import se.sogeti.umea.configuration.ConfigurationInjectionManager;

public class FopConfiguration {

	private final static Logger LOG = LoggerFactory
			.getLogger(FopConfiguration.class);

	private final static String FOP_CONFIG_FILE = ConfigurationInjectionManager
			.getValue("fop.config.file", null, true);

	private static FopConfiguration instance_;

	private FopConfiguration() {
		super();		
	}
	
	public static FopConfiguration getInstance() {
		if(instance_ == null) {
			instance_ = new FopConfiguration();
		}
		return instance_;
	}

	public void setConfig(FopFactory fopFactory) throws SAXException,
			IOException {
		LOG.debug("Configuring FopFactory... with " + FOP_CONFIG_FILE);
		File configFile = new File(FOP_CONFIG_FILE);
		
		if (configFile.exists()) {
			fopFactory.setUserConfig(configFile);			
		} else {
			LOG.error("Fop configuration file : " + FOP_CONFIG_FILE
					+ " was not found.");
		}
		
	}


}
