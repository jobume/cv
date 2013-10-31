package se.sogeti.umea.cvconverter.adapter.persistence.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import se.sogeti.umea.cvconverter.application.Repository;
import se.sogeti.umea.cvconverter.application.Image;
import se.sogeti.umea.cvconverter.application.CoverImageRepository;

@Repository
public class JdbcCoverImageRepository implements CoverImageRepository {

	@Resource(name = "jdbc/h2Database")
	DataSource ds;

	@Override
	public void createCoverImage(String name) throws IOException {
		
		try (Connection con = ds.getConnection();
				PreparedStatement ps = con
						.prepareStatement("INSERT INTO coverimage (name) VALUES (?)")) {
			ps.setString(1, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	@Override
	public List<Image> getCoverImages() throws IllegalArgumentException,
			NoSuchElementException, IOException {

		List<Image> coverImages = new ArrayList<>();
		try (Connection con = ds.getConnection();
				PreparedStatement ps = con
						.prepareStatement("SELECT * FROM coverimage");
				ResultSet rs = ps.executeQuery();) {
			while (rs.next()) {
				coverImages.add(new Image(rs.getString("name")));
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}

		return coverImages;
	}

	@Override
	public void deleteCoverImage(String name) throws IllegalArgumentException,
			NoSuchElementException, IOException {

		try (Connection con = ds.getConnection();
				PreparedStatement ps = con
						.prepareStatement("DELETE FROM coverimage WHERE name = (?)");) {
			ps.setString(1, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

}
