package se.sogeti.umea.cvconverter.application;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Tag {

	public static enum Size {
		SMALL, MEDIUM, LARGE;
	}
	
	public static enum Font {
		MYRIAD("myriad"), TREBUCHET("trebuchet"), MINION("minion");

		private String name;

		Font(String name) {
			this.name = name;
		}

		String getName() {
			return name;
		}
	}
	
	private boolean bold;

	private boolean upperCase;

	private boolean italic;

	private Size size;

	private String font;
	
	private String tagName;

	public Tag() {
		super();
	}
	
	public Tag(String tagName) {
		this.tagName = tagName;
	}

	public Tag(String tagName, boolean bold, boolean upperCase, boolean italic, Size size,
			String font) {
		super();
		this.tagName = tagName;
		this.bold = bold;
		this.upperCase = upperCase;
		this.italic = italic;
		this.size = size;
		this.font = font;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public boolean isBold() {
		return bold;
	}

	public void setBold(boolean bold) {
		this.bold = bold;
	}

	public boolean isUpperCase() {
		return upperCase;
	}

	public void setUpperCase(boolean upperCase) {
		this.upperCase = upperCase;
	}

	public boolean isItalic() {
		return italic;
	}

	public void setItalic(boolean italic) {
		this.italic = italic;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}
	
	@Override
	public String toString() {
		return "Tag [bold=" + bold + ", upperCase=" + upperCase + ", italic="
				+ italic + ", size=" + size + ", font=" + font + ", tagName="
				+ tagName + "]";
	}


}