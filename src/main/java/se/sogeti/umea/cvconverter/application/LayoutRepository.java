package se.sogeti.umea.cvconverter.application;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public interface LayoutRepository {

	public long createLayout(String name, String xslStylesheet)
			throws IOException;

	public Layout getLayout(long id) throws IllegalArgumentException,
			NoSuchElementException, IOException;

	public List<LayoutOverview> getLayouts() throws NoSuchElementException,
			IOException;

	public void updateLayout(Layout layout) throws IllegalArgumentException,
			NoSuchElementException, IOException;

	public void deleteLayout(long id) throws IllegalArgumentException,
			NoSuchElementException, IOException;

}
