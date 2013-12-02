package se.sogeti.umea.cvconverter.adapter.persistence.file;

public class FileRecord {
	private final String name;
	private final String type;
	private final String url;

	public FileRecord(String name, String type, String url) {
		super();
		this.name = name;
		this.type = type;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}

}