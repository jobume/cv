package se.sogeti.umea.cvconverter.application;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CvOverview {
	private int id;
	private String name;
	private Date ts;
	private String office;
	
	public CvOverview() {
		super();
	}

	public CvOverview(int id, String name, String office, Date ts) {
		super();
		this.id = id;
		this.name = name;
		this.office = office;
		this.ts = ts;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}
	
}
