package se.sogeti.umea.cvconverter.application.impl.cvparser;

import static org.junit.Assert.*;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;

import org.junit.Test;

import se.sogeti.umea.cvconverter.application.impl.cvparser.DocumentParser;


import static org.mockito.Mockito.*;

public class DocumentParserTest {

	@Test
	public void canBeCreated() {
		DocumentParser dp = new DocumentParser();
		assertNotNull(dp);
	}

	@Test
	public void canParseDocument() throws Exception {
		// Mock to root element
		DefaultStyledDocument mockDoc = mock(DefaultStyledDocument.class);
		Element mockRootElement = mock(Element.class);
		when(mockDoc.getDefaultRootElement()).thenReturn(mockRootElement);
		when(mockRootElement.getElementCount()).thenReturn(1);

		// Mock leaf
		Element mockElement = mock(Element.class);
		when(mockRootElement.getElement(anyInt())).thenReturn(mockElement);
		when(mockElement.isLeaf()).thenReturn(true);
		when(mockDoc.getText(anyInt(), anyInt())).thenReturn("abc");

		// Parse and assert
		DocumentParser parser = new DocumentParser();
		StringBuilder sb = parser.parseDocument(mockDoc);
		assertTrue(sb.length() > 0);
	}
}
