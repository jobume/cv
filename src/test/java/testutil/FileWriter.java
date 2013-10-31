package testutil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileWriter {
	// TODO make this to take encoding as argument
	// TODO beautify the code here
	public static void writeFile(File file, String content)
			throws FileNotFoundException, IOException {
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
				new FileOutputStream(file), "ISO-8859-1");
		BufferedWriter out = new BufferedWriter(outputStreamWriter);
		// BufferedWriter out = new BufferedWriter(new
		// java.io.FileWriter(file));
		out.write(content);
		out.close();
	}
}
