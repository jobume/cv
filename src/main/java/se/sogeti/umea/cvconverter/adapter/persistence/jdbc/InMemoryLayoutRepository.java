package se.sogeti.umea.cvconverter.adapter.persistence.jdbc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.enterprise.context.ApplicationScoped;

import se.sogeti.umea.cvconverter.application.Layout;
import se.sogeti.umea.cvconverter.application.LayoutOverview;
import se.sogeti.umea.cvconverter.application.LayoutRepository;
import se.sogeti.umea.cvconverter.application.Repository;

@ApplicationScoped
// @Repository
public class InMemoryLayoutRepository implements LayoutRepository {

	private final Map<Long, Layout> inMemoryDb = new HashMap<Long, Layout>();
	private int idCounter = 0;

	private Layout newLayout(long id, String name, String xslStylesheet) {
		LayoutImpl l = new LayoutImpl();
		l.setName(name);
		l.setXslStylesheet(xslStylesheet);
		l.setId(id);
		return l;
	}
	
	@Override
	public long createLayout(String name, String xslStylesheet)
			throws IOException {
		Long id = new Long(idCounter++);
		inMemoryDb.put(id, newLayout(id.longValue(), name, xslStylesheet));		
		System.out.println("Creating layout with id: " + id.longValue());
		return id.longValue();
	}

	@Override
	public Layout getLayout(long id) throws IllegalArgumentException,
			NoSuchElementException, IOException {
		return inMemoryDb.get(new Long(id));		
	}

	@Override
	public List<LayoutOverview> getLayouts() throws NoSuchElementException,
			IOException {
		List<LayoutOverview> layouts = new ArrayList<LayoutOverview>();
		for(Layout l : inMemoryDb.values()) {
			LayoutOverviewImpl layoutOverview = new LayoutOverviewImpl();
			layoutOverview.setId(l.getId());
			layoutOverview.setName(l.getName());
			layouts.add(layoutOverview);
		}
		System.out.println("Returning " + layouts.size() + " layouts from memory");
		return layouts;
	}

	@Override
	public void updateLayout(Layout layout) throws IllegalArgumentException,
			NoSuchElementException, IOException {
		inMemoryDb.put(new Long(layout.getId()), layout);
	}

	@Override
	public void deleteLayout(long id) throws IllegalArgumentException,
			NoSuchElementException, IOException {
		inMemoryDb.remove(new Long(id));
	}

}
