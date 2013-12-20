package se.sogeti.umea.cvconverter.adapter.client.http.streamutil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class StreamUtil {

	public static String readStreamToString(InputStream inputStream) {
		try (Scanner scanner = new Scanner(inputStream)) {
			return scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
		}
	}

	public static String readStreamToString(InputStream inputStream,
			String encoding) throws IOException {
		try (Scanner scanner = new Scanner(inputStream, encoding)) {
			return scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
		}
	}

	public static byte[] readStreamToBytes(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		int reads = is.read();
		while (reads != -1) {
			baos.write(reads);
			reads = is.read();
		}

		return baos.toByteArray();
	}
}
