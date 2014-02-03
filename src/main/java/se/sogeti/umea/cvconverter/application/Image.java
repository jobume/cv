package se.sogeti.umea.cvconverter.application;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Image {

	private String name;

	private String url;

	public Image(String url) {
		this.url = url;
		String[] urlSplitBySlash = url.split("/");
		if (urlSplitBySlash.length > 0)
			name = urlSplitBySlash[urlSplitBySlash.length - 1];

	}

	public Image() {
		super();
	}

	public Image(String name, String url) {
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
}
