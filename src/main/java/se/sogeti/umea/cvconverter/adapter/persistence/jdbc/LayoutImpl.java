package se.sogeti.umea.cvconverter.adapter.persistence.jdbc;

import se.sogeti.umea.cvconverter.application.Layout;

public class LayoutImpl implements Layout {

	private long id;
	private String name;
	private String xslStylesheet;
	
	@Override
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getXslStylesheet() {
		return xslStylesheet;
	}

	@Override
	public void setXslStylesheet(String xslStylesheet) {
		this.xslStylesheet = xslStylesheet;
	}

}
