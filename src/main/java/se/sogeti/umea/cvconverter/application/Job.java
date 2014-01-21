package se.sogeti.umea.cvconverter.application;

public interface Job {

	public String getDate();

	public void setDate(String date);

	public String getName();

	public void setName(String name);

	public String getDescription();
	
	public String getShortDescription();
	
	public void setShortDescription(String shortDescription);

	public void setDescription(String description);
	
	public void setDuration(int duration);
	
	public int getDuration();

}
