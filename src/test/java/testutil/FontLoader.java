package testutil;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class FontLoader {
	
	public static void loadFont(String fileName) throws FileNotFoundException {
		
		URL url = FontLoader.class.getResource(fileName);
		if(url==null) {
			throw new FileNotFoundException("Filename " + fileName
					+ " does not exist!");
		} 
		
		try {
			File fontFile = new File(url.getFile());
			GraphicsEnvironment ge = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, fontFile));
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
			throw new RuntimeException("Error loading fonts.", e);
		}
	}
}
