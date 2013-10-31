package se.sogeti.umea.cvconverter.application.impl.cvparser;

//import java.io.UnsupportedEncodingException;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;
//import javax.swing.text.AbstractDocument.BranchElement;

class DocumentParser {

	private static final String LN = System.getProperty("line.separator");
//	private static final String ENCODING = "ISO-8859-1";
	DefaultStyledDocument doc;

	StringBuilder parseDocument(DefaultStyledDocument doc) {
		this.doc = doc;

//		BranchElement rtfElement = (BranchElement) doc.getDefaultRootElement();
		Element rtfElement = doc.getDefaultRootElement();

		StringBuilder sb = parseRtfToString(doc, rtfElement);
		sb.trimToSize();
		return sb;
	}

	private StringBuilder parseRtfToString(DefaultStyledDocument doc,
			Element rtfElement) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < rtfElement.getElementCount(); i++) {
			Element rtfNextElement = rtfElement.getElement(i);
			if (rtfNextElement.isLeaf()) {
				try {
					int startOffset = rtfNextElement.getStartOffset();
					int endOffset = rtfNextElement.getEndOffset()
							- rtfNextElement.getStartOffset();
//					String text = new String(doc
//							.getText(startOffset, endOffset).getBytes(ENCODING));
					String text = doc.getText(startOffset, endOffset);
					text = text.trim();
					if (!text.isEmpty()) {
						sb.append(text);
						sb.append(LN);
					}
//				} catch (UnsupportedEncodingException | BadLocationException e1) { 
//					e1.printStackTrace();
				} catch (BadLocationException e2) {
					e2.printStackTrace();
				}
			} else {
				sb.append(parseRtfToString(doc, rtfNextElement).toString());
			}
		}

		return sb;
	}
}
