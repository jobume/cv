package se.sogeti.umea.cvconverter.application.impl.cvparser.model;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import se.sogeti.umea.cvconverter.adapter.client.http.json.AcquisitionDeserializer;

@JsonDeserialize(using = AcquisitionDeserializer.class)
public class AcquisitionImpl implements
		se.sogeti.umea.cvconverter.application.Acquisition {

	private String date;
	private String name;
	private String location;

	public AcquisitionImpl(String date, String name, String location) {
		this.date = date;
		this.name = name;
		this.location = location;
	}

	@Override
	public String getDate() {
		return date;
	}

	@Override
	public void setDate(String date) {
		this.date = date;
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
	public String getLocation() {
		return location;
	}

	@Override
	public void setLocation(String location) {
		this.location = location;
	}

}
