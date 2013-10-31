package se.sogeti.umea.cvconverter.application.impl.cvparser.model;

public class JobImpl implements se.sogeti.umea.cvconverter.application.Job,
		se.sogeti.umea.cvconverter.application.Important {

	private String date;
	private String name;
	private String description;
	private Boolean important;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean isImportant() {
		return important;
	}

	public void setImportant(Boolean important) {
		this.important = important;
	}

}