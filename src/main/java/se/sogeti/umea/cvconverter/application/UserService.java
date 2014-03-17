package se.sogeti.umea.cvconverter.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	public String getOfficeForUser(String user) {
		LOG.debug("Getting office for user " + user);
		String office;
		switch (user) {
		case "foo":
			office = "Umea";
			break;
		default:
			office = user;
			break;
		}
		LOG.debug("Returning office: " + office + " for user: " + user);
		return office;
	}
	
}
