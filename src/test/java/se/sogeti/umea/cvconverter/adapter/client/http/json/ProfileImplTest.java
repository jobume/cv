package se.sogeti.umea.cvconverter.adapter.client.http.json;

import org.junit.Assert;
import org.junit.Test;

public class ProfileImplTest {

	@Test
	public void testSetFirstAndLastName() {
		ProfileImpl profile = new ProfileImpl();
		profile.setName("First And Last Name");
		Assert.assertEquals("First", profile.getFirstName());
		Assert.assertEquals("And Last Name", profile.getLastName());
	}
	
	@Test
	public void testNoWhiteSpace() {
		ProfileImpl profile = new ProfileImpl();
		profile.setName("First");
		profile.setName("First ");
		profile.setName(" First");
		profile.setName(null);
	}
	
}
