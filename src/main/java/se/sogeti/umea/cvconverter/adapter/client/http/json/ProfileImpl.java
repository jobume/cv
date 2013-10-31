package se.sogeti.umea.cvconverter.adapter.client.http.json;

import se.sogeti.umea.cvconverter.application.Image;

public class ProfileImpl implements se.sogeti.umea.cvconverter.application.Profile {

	private String name;
	private String title;
	private String dateOfBirth;
	private Image portrait;
	private String phoneNumber;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	@Override
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public Image getPortrait() {
		return portrait;
	}

	@Override
	public void setPortrait(Image portrait) {
		this.portrait = portrait;
	}

	@Override
	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Override
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
