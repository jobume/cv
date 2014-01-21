package se.sogeti.umea.cvconverter.adapter.client.http.resource;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.sogeti.umea.configuration.InjectedConfiguration;
import se.sogeti.umea.cvconverter.application.Image;

public class ImageDecorator {

	private static final Logger LOG = LoggerFactory
			.getLogger(ImageDecorator.class);

	@Context
	private ServletContext context;

	@Inject
	@InjectedConfiguration(key = "host.name", defaultValue = "http://localhost:8080")
	/* package */String hostName;

	@Inject
	@InjectedConfiguration(key = "host.name.internal", defaultValue = "http://localhost:8080")
	/* package */String hostNameInternal;

	@Inject
	@InjectedConfiguration(key = "image.location", defaultValue = "home/images")
	private String imageLocation;

	@Inject
	@InjectedConfiguration(key = "image.path", defaultValue = "images")
	private String imagePath;

	private final String jettyHomeDefault = "/opt/jetty";

	/**
	 * Location on disk for an image (eg. /opt/jetty/home/images)
	 * 
	 * @param imageName
	 *            the name of the image
	 * 
	 * @return the location on the disk where an image should be stored
	 */
	public String getImageLocation(String imageName) {
		String jettyHome = System.getProperty("jetty.home");
		if (jettyHome == null) {
			jettyHome = jettyHomeDefault;
		}
		LOG.debug("Jetty home was: " + jettyHome);
		return jettyHome + "/" + imageLocation + "/" + imageName;
	}

	/**
	 * Public path for an image (eg. http://myserver.com:8088/images/name.png)
	 * 
	 * @param imageName
	 *            the name of the image
	 * 
	 * @return the public path where the image can be accessed
	 */
	public String getImageUrl(String imageName) {
		return hostName + "/" + imagePath + "/" + imageName;
	}

	/**
	 * Internal path for an image (eg. http://localhost:8080/images/name.png)
	 * 
	 * @param name
	 *            the name of the image
	 * 
	 * @return the internal path of the image
	 */
	private String getInternalImageUrl(String name) {
		return hostNameInternal + "/" + imagePath + "/" + name;
	}

	/**
	 * Sets internal and external image paths for an image.
	 * 
	 * @param image
	 *            the image to set urls for.
	 */
	public void setImageUrls(Image image) {
		if (image != null && image.getName() != null) {
			image.setLocalUrl(getInternalImageUrl(image.getName()));
			image.setUrl(getImageUrl(image.getName()));
		} 
	}

}
