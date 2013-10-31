package se.sogeti.umea.cvconverter.adapter.persistence.jdbc;

import se.sogeti.umea.cvconverter.application.LayoutOverview;

public class LayoutOverviewImpl implements LayoutOverview {

	private long id;
	private String name;

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

	public void setName(String name) {
		this.name = name;
	}

}
