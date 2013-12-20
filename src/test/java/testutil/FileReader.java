package testutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class FileReader {

	public final static String UTF8 = "UTF-8";
	public final static String ISO88591 = "ISO-8859-1";

	public static String readFile(String fileName) throws IOException {
		File file = new File(fileName);

		return readFile(file);
	}

	@SuppressWarnings("resource")
	public static String readFile(File file) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new java.io.FileReader(file));
		String line = null;
		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(System.getProperty("line.separator"));
		}
		return stringBuilder.toString();
	}

	public static String readFile(String fileName, String encoding)
			throws IOException {
		File file = new File(fileName);
		StringBuilder stringBuilder = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), Charset.forName(encoding)));) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(System.getProperty("line.separator"));
			}
		}
		return stringBuilder.toString();

	}

	/***
	 * Determines the encoding of the specified file. If a UTF16 Byte Order Mark
	 * (BOM) is found an encoding of "UTF16" is returned. If a UTF8 BOM is found
	 * an encoding of "UTF8" is returned. Otherwise the default encoding is
	 * returned.
	 * 
	 * @param filePath
	 *            file path
	 * @return "UTF8", "UTF16", or default encoding.
	 */
	public static String getEncoding(String filePath) {
		String encoding = "unknown"; // System.getProperty("file.encoding");

		BufferedReader bufferedReader = null;

		try {
			// In order to read files with non-default encoding, specify an
			// encoding in the FileInputStream constructor.
			bufferedReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filePath)));

			char buffer[] = new char[3];
			int length = bufferedReader.read(buffer);

			if (length >= 2) {
				if ((buffer[0] == (char) 0xff && buffer[1] == (char) 0xfe)
						|| (buffer[0] == (char) 0xfe && buffer[1] == (char) 0xff)) {
					encoding = "UTF16";
				}
			}
			if (length >= 3) {
				if (buffer[0] == (char) 0xef && buffer[1] == (char) 0xbb
						&& buffer[2] == (char) 0xbf) /* UTF-8 */{
					encoding = "UTF8";
				}
			}
		} catch (IOException ex) {
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
				}
			}
		}

		return encoding;
	}

}