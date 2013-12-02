package se.sogeti.umea.cvconverter.adapter.persistence.file;

import java.io.InputStream;

public class RemoteImageFileBinaryRepository /* implements FileBinaryRepository */ {

	
	public String createFile(InputStream fileInputStream, String name,
			String type) {
		
		// String url = SomeRemoteFileStorageService.PUT(new File(fileInputStream));
		
		return null;
	}

	
	public void deleteFile(String name, String type) {
		// SomeRemoteFileStorageService.DELETE(name, type)
	}

}
