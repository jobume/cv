package testutil;

import java.util.List;
import junit.framework.*;
import org.custommonkey.xmlunit.*;

/*
 * This class was written by Hamlet D'Arcy
 * http://java.dzone.com/articles/simple-xml-testing-java
 * http://hamletdarcy.blogspot.com
 * 
 * Usage:
 * XmlAssertions.assertXmlSimilar("<root>data</root>", "<root>data</root>");
 */
public class XmlAssertions {

	private static final String ERROR_MSG = "XML comparison failure. \nExpected: %s\nReceived: %s\n%s";

	static {
		XMLUnit.setIgnoreWhitespace(true);
	}

	public static void assertXmlSimilar(String expected, String actual) {
		try {
			Diff diff = new Diff(expected, actual);
			@SuppressWarnings("rawtypes")
			List differences = new DetailedDiff(diff).getAllDifferences();
			Assert.assertTrue(
					String.format(ERROR_MSG, expected, actual, differences),
					diff.similar());
		} catch (Exception ex) {
			Assert.fail(String.format(ERROR_MSG, expected, actual,
					ex.getMessage()));
		}
	}
}