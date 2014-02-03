package se.sogeti.umea.configuration;


public class SystemEnvResolver {
	
	interface Sys {
		String getenv(String name);
	}

	private Sys sys;

	private final static String SYSTEM_ENV_START = "${env.";
	private final static String SYSTEM_ENV_END = "}";

	/**
	 * Gets the value for a property that is a environment variable.
	 * 
	 * For example:
	 * 
	 * Suppose environment is set up with: MY_NAME = MY_VALUE.
	 * 
	 * ${env.MY_NAME}/folder will be replaced with MY_VALUE/folder
	 * 
	 * @param value
	 *            the property (i.e the name of the environment variable). The
	 *            property must start with the marker ${env..
	 *            Must NOT be null.
	 * 
	 * @return the property with the name of the environment variable replaced
	 *         with the value or the property if it is not an environment variable.
	 */
	public String getEnv(String value) {
		if (isSystemProperty(value)) {
			String strippedValue = value.substring(SYSTEM_ENV_START.length(),
					value.lastIndexOf(SYSTEM_ENV_END));

			String env = (sys != null ? sys.getenv(strippedValue) : System
					.getenv(strippedValue));
			if (env == null) {
				throw new IllegalStateException(
						"Null value for environment variable: " + strippedValue);
			}

			return value.replace(SYSTEM_ENV_START + strippedValue
					+ SYSTEM_ENV_END, env);
		}
		return value;
	}

	private boolean isSystemProperty(String value) {
		return value.startsWith(SYSTEM_ENV_START);
	}

	public void setSys(Sys sys) {
		this.sys = sys;
	}

}
