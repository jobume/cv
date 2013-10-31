package se.sogeti.umea.cvconverter.adapter.client.http.json;

import se.sogeti.umea.cvconverter.application.LayoutOverview;

public class LayoutOverviewJson implements LayoutOverview {

	private long id;
	private String name;
	private String href;

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

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

}
