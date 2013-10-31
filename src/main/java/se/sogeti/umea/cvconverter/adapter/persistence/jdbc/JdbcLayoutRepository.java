package se.sogeti.umea.cvconverter.adapter.persistence.jdbc;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.annotation.Resource;
import javax.sql.DataSource;

import se.sogeti.umea.cvconverter.application.Layout;
import se.sogeti.umea.cvconverter.application.LayoutOverview;
import se.sogeti.umea.cvconverter.application.LayoutRepository;
import se.sogeti.umea.cvconverter.application.Repository;

//@ApplicationScoped
@Repository
public class JdbcLayoutRepository implements LayoutRepository {
	
	// TODO use transaction here?

	@Resource(name = "jdbc/h2Database")
	DataSource ds;

	@Override
	public long createLayout(String name, String xslStylesheet)
			throws IOException {
		long id = 0;

		try (Connection con = ds.getConnection()) {
			try {
				con.setAutoCommit(false); // TODO can I set this as default in
											// config?

				try (PreparedStatement ps = con
						.prepareStatement("INSERT INTO layout (name,xsl_stylesheet) VALUES (?,?)")) {
					ps.setString(1, name);
					StringReader reader = new StringReader(xslStylesheet);
					ps.setCharacterStream(2, reader);

					ps.executeUpdate();

					ResultSet generatedKeys = ps.getGeneratedKeys();
					if (generatedKeys.next()) {
						id = generatedKeys.getInt(1);
					} else {
						throw new IOException("ID generation failed!");
					}
				}

				con.commit();
			} catch (SQLException | IOException e) {
				con.rollback();
				throw e;
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}

		return id;
	}

	@Override
	public Layout getLayout(long id) throws IllegalArgumentException,
			NoSuchElementException, IOException {
		LayoutImpl l = new LayoutImpl();

		try (Connection con = ds.getConnection();
				PreparedStatement ps = con
						.prepareStatement("SELECT id,name,xsl_stylesheet FROM layout WHERE id = ?")) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					l.setId(rs.getInt(1));
					l.setName(rs.getString(2));
					Reader characterStream = rs.getCharacterStream(3);
					String xmlStylesheet = readStream(characterStream);
					l.setXslStylesheet(xmlStylesheet);
				} else {
					l = null;
				}
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}

		return l;
	}

	@Override
	public List<LayoutOverview> getLayouts() throws NoSuchElementException,
			IOException {
		List<LayoutOverview> layouts = new ArrayList<>();

		try (Connection con = ds.getConnection();
				PreparedStatement ps = con
						.prepareStatement("SELECT id,name FROM layout");
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				LayoutOverviewImpl l = new LayoutOverviewImpl();
				l.setId(rs.getInt(1));
				l.setName(rs.getString(2));
				layouts.add(l);
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}

		return layouts;
	}

	@Override
	public void updateLayout(Layout layout) throws IllegalArgumentException,
			NoSuchElementException, IOException {
		try (Connection con = ds.getConnection();
				PreparedStatement ps = con
						.prepareStatement("UPDATE layout SET name = ?, xsl_stylesheet = ? WHERE id = ?")) {
			ps.setString(1, layout.getName());
			StringReader reader = new StringReader(layout.getXslStylesheet());
			ps.setCharacterStream(2, reader);
			ps.setLong(3, layout.getId());

			int update = ps.executeUpdate();
			if (update == 0) {
				throw new NoSuchElementException("Id '" + layout.getId()
						+ "' not found!");
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	@Override
	public void deleteLayout(long id) throws IllegalArgumentException,
			NoSuchElementException, IOException {
		try (Connection con = ds.getConnection();
				PreparedStatement ps = con
						.prepareStatement("DELETE FROM layout WHERE id = ?")) {
			ps.setLong(1, id);
			int update = ps.executeUpdate();
			if (update == 0) {
				throw new NoSuchElementException("Id '" + id + "' not found!");
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	// TODO move this to StreamUtil ???
	private String readStream(Reader characterStream) {
		try (Scanner scanner = new Scanner(characterStream)) {
			String xmlStylesheet = scanner.useDelimiter("\\A").hasNext() ? scanner
					.next() : "";
			return xmlStylesheet;
		}
	}
	
}
