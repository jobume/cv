package se.sogeti.umea.cvconverter.application;

public interface Layout {

	public long getId();

	public String getName();

	public void setName(String name);

	public String getXslStylesheet();

	public void setXslStylesheet(String xslStylesheet);

}
