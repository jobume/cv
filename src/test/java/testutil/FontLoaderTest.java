package testutil;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

public class FontLoaderTest {

	private static final String MYRIAD_WEB_PRO_FILE = "/fonts/MyriadWebPro.ttf";
	private static final String MINION_PRO_FILE = "/fonts/MinionPro.ttf";
	
	@Test
	public void testListFonts() throws FileNotFoundException {		
		FontList fontList = new FontList();
		FontLoader.loadFont(MYRIAD_WEB_PRO_FILE);
		FontLoader.loadFont(MINION_PRO_FILE);
		fontList.listUsingGraphicsEnvironment();
		assertTrue(fontList.findFont("Myriad Web Pro"));
		assertTrue(fontList.findFont("Minion Pro"));
	}
	
}
