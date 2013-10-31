package testutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class FileReader {
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

}
