package se.sogeti.umea.cvconverter.application;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Image {

	private int id;
	
	private String name;

	private String url;

	public Image(int id) {
		this.id = id;
	}

	public Image() {
		super();
	}

	public Image(int id, String name, String url) {
		this.id = id;
		this.name = name;
		this.url = url;		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Image [id=" + id + ", name=" + name + ", url=" + url + "]";
	}
	
	
	
}
