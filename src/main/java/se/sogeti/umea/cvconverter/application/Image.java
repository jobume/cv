package se.sogeti.umea.cvconverter.application;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Image {

	private String name;
	
	private String url;
	
	private String localUrl;
	
	public Image() {
		super();
	}
	
	public Image(String name) {
		super();
		this.name = name;		
	}
	
	public Image(String name, String url, String localUrl) {
		this.name = name;
		this.url = url;
		this.localUrl = localUrl;
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

	public String getLocalUrl() {
		return localUrl;
	}

	public void setLocalUrl(String localUrl) {
		this.localUrl = localUrl;
	}
}
