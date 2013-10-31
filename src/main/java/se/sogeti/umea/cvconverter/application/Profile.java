package se.sogeti.umea.cvconverter.application;

public interface Profile {

	public String getName();

	public void setName(String name);

	public String getTitle();

	public void setTitle(String title);

	public String getDateOfBirth();

	public void setDateOfBirth(String dateOfBirth);

	public Image getPortrait();

	public void setPortrait(Image portrait);
	
	public String getPhoneNumber();
	
	public void setPhoneNumber(String phoneNumber);

}
