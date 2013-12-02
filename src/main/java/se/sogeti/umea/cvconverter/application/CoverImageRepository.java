package se.sogeti.umea.cvconverter.application;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;

public interface CoverImageRepository {

	public Image createCoverImage(InputStream uploadedInputStream, String name)
			throws IOException;

	public List<Image> getCoverImages() throws IllegalArgumentException,
			NoSuchElementException, IOException;

	public void deleteCoverImage(String name) throws IllegalArgumentException,
			NoSuchElementException, IOException;	
}
