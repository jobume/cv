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

	private volatile static Properties properties = getProperties();

	public synchronized static Properties getProperties() {
		Properties prop = new Properties();
		try {
			InputStream input = ConfigurationInjectionManager.class
					.getClassLoader().getResourceAsStream("app.properties");
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
		String value;
		try {
			value = properties.getProperty(param.key());
			if (value == null || value.trim().length() == 0) {
				if (param.mandatory())
					throw new IllegalStateException(MessageFormat.format(
							MANDATORY_PARAM_MISSING,
							new Object[] { param.key() }));
				else
					return param.defaultValue();
			}
			return value;
		} catch (MissingResourceException e) {
			if (param.mandatory())
				throw new IllegalStateException(MessageFormat.format(
						MANDATORY_PARAM_MISSING, new Object[] { param.key() }));
			return MessageFormat.format(INVALID_KEY,
					new Object[] { param.key() });
		}
	}
}