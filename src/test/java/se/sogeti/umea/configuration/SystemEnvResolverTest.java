package se.sogeti.umea.configuration;

import junit.framework.Assert;

import org.junit.Test;

import se.sogeti.umea.configuration.SystemEnvResolver.Sys;

public class SystemEnvResolverTest {
	
	@Test
	public void testGetEnv() {
		final String ENV_PATH = "/var/lib/data";
		final String ENV_NAME = "OPENSHIFT_APP_DIR";
		String propName = "${env." + ENV_NAME + "}/images";
		SystemEnvResolver envResolver = new SystemEnvResolver();
		envResolver.setSys(new Sys() {
			public String getenv(String e) {
				if (ENV_NAME.equals(e)) {
					return ENV_PATH;
				}
				return  e;
			}
		});
		
		Assert.assertEquals(ENV_PATH + "/images", envResolver.getEnv(propName));
	}
	
	@Test
	public void testGetNoEnv() {
		String prop = "not-an-environment-variable";
		Assert.assertEquals(prop, new SystemEnvResolver().getEnv(prop));
	}

}
