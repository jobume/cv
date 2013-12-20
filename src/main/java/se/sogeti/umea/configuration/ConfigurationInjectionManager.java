package se.sogeti.umea.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.Properties;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class ConfigurationInjectionManager {
	static final String INVALID_KEY = "Invalid key '{0}'";
	static final String MANDATORY_PARAM_MISSING = "No definition found for a mandatory configuration parameter : '{0}'";
	static final String PROP_FILE_NAME = "app.properties";
	
	private volatile static Properties properties = getProperties();

	private static SystemEnvResolver envResolver = new SystemEnvResolver();

	public synchronized static Properties getProperties() {
		Properties prop = new Properties();
		try {
			InputStream input = ConfigurationInjectionManager.class
					.getClassLoader().getResourceAsStream(PROP_FILE_NAME);
			prop.load(input);
			input.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return prop;
	}

	@Produces
	@InjectedConfiguration
	public String injectConfiguration(InjectionPoint ip)
			throws IllegalStateException {

		InjectedConfiguration param = ip.getAnnotated().getAnnotation(
				InjectedConfiguration.class);
		if (param.key() == null || param.key().length() == 0) {
			return param.defaultValue();
		}
		
		try {
			return getValue(param.key(), param.defaultValue(),
					param.mandatory());
		} catch (MissingResourceException e) {
			if (param.mandatory())
				throw new IllegalStateException(MessageFormat.format(
						MANDATORY_PARAM_MISSING, new Object[] { param.key() }));
			return MessageFormat.format(INVALID_KEY,
					new Object[] { param.key() });
		}
	}

	public static String getValue(String key, String defaultValue,
			boolean mandatory) {
		String value = properties.getProperty(key);
		if (value == null || value.trim().length() == 0) {
			if (mandatory)
				throw new IllegalStateException(MessageFormat.format(
						MANDATORY_PARAM_MISSING, new Object[] { key }));
			else
				return defaultValue;
		}

		return envResolver.getEnv(value);
	}
}