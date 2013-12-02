package se.sogeti.umea.cvconverter.application.impl.cvparser.model;

import se.sogeti.umea.cvconverter.application.Image;

public class ProfileImpl implements se.sogeti.umea.cvconverter.application.Profile {

	private String name;
	private String title;
	private String dateOfBirth;
	private Image portrait;
	private String phoneNumber;
	private String firstName;
	private String lastName;

	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		if(name != null && name.indexOf(" ") > 0) {
			String firstName = name.substring(0, name.indexOf(" ")).trim();
			setFirstName(firstName);
			String lastName = name.substring(name.indexOf(" ")).trim();
			setLastName(lastName);			
		}
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
	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Override
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public Image getPortrait() {
		return this.portrait;
	}

	@Override
	public void setPortrait(Image portrait) {
		this.portrait = portrait;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


}
