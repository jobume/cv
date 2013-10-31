package se.sogeti.umea.cvconverter.application;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public interface CoverImageRepository {

	public void createCoverImage(String name)
			throws IOException;

	public List<Image> getCoverImages() throws IllegalArgumentException,
			NoSuchElementException, IOException;

	public void deleteCoverImage(String name) throws IllegalArgumentException,
			NoSuchElementException, IOException;	
}
