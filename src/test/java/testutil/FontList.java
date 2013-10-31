package testutil;

import java.awt.GraphicsEnvironment;

public class FontList {

	String fonts = "Myriad, Trebuchet, Minion Pro";
	
	/**
	 * Prints a list of all available fonts from the local graphics environment.
	 * 
	 * The output list varies from manchine to machine
	 */
	public void listUsingGraphicsEnvironment() {
		GraphicsEnvironment ge = null;

		ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		String[] fontNames = ge.getAvailableFontFamilyNames();

		for (int i = 0; i < fontNames.length; i++) {
			System.out.println(fontNames[i]);
		}
	}
	
	public boolean findFont(String fontFamilyName) {
		GraphicsEnvironment ge = null;

		ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		String[] fontNames = ge.getAvailableFontFamilyNames();

		for (int i = 0; i < fontNames.length; i++) {
			if(fontFamilyName.equals(fontNames[i]))
				return true;
			
		}
		return false;
	}
}
