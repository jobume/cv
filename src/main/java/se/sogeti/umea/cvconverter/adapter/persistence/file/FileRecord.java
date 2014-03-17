package se.sogeti.umea.cvconverter.adapter.persistence.file;

public class FileRecord {
	
	private int id;	
	private final String name;
	private final String type;
	private final String url;

	public FileRecord(int id, String name, String type, String url) {
		super();
		this.id = id;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "FileRecord [id=" + id + ", name=" + name + ", type=" + type
				+ ", url=" + url + "]";
	}
	
	

}