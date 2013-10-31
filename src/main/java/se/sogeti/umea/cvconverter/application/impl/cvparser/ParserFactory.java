package se.sogeti.umea.cvconverter.application.impl.cvparser;

import javax.enterprise.inject.Produces;
import javax.swing.text.rtf.RTFEditorKit;
import javax.xml.stream.XMLOutputFactory;

public class ParserFactory {

	@Produces
	public static XMLOutputFactory getXMLOutputFactory() {
		return XMLOutputFactory.newInstance();
	}

	@Produces
	public static RTFEditorKit getRTFEditorKit() {
		return new RTFEditorKit();
	}
	
	public static Parser getParser() {
		RtfParser rtfParser = new RtfParser(getRTFEditorKit());
		DocumentParser documentParser = new DocumentParser();
		StringParser stringParser = new StringParser();
		XmlGenerator xmlGenerator = new XmlGenerator(getXMLOutputFactory());
		
		return new Parser(rtfParser, documentParser, stringParser, xmlGenerator);
	}
	
}
