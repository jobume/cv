package se.sogeti.umea.cvconverter.adapter.client.http.json;

import se.sogeti.umea.cvconverter.application.Layout;

public class LayoutJson implements Layout {
	
	private long id;
	private String name;
	private String xslStylesheet;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getXslStylesheet() {
		return xslStylesheet;
	}

	public void setXslStylesheet(String xslStylesheet) {
		this.xslStylesheet = xslStylesheet;
	}
	
}
