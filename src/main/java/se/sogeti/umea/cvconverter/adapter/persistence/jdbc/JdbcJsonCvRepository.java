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

import se.sogeti.umea.cvconverter.application.CvOverview;
import se.sogeti.umea.cvconverter.application.JsonCvRepository;
import se.sogeti.umea.cvconverter.application.Repository;

@Repository
public class JdbcJsonCvRepository implements JsonCvRepository {

	private final static String TBL_CV = "cv";
	private final static String COL_ID = "id";
	private final static String COL_NAME = "name";
	private final static String COL_JSON = "json";

	@Resource(lookup = "java:jboss/datasources/MysqlDS")
	DataSource ds;

	@Override
	public int createCv(String name, String jsonCv) throws IOException {

		String stmt = JdbcUtils.createInsert(TBL_CV, COL_NAME, COL_JSON);
		try (Connection con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement(stmt)) {
			ps.setString(1, name);
			ps.setString(2, jsonCv);
			ps.executeUpdate();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			if (generatedKeys.next()) {
				return generatedKeys.getInt(1);
			} else {
				throw new IOException("ID generation failed!");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getCv(int id) throws IOException {

		String stmt = JdbcUtils.createSelectWhere(TBL_CV, COL_ID);

		try (Connection con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement(stmt)) {
			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getString(COL_JSON);
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return null;
	}

	@Override
	public void updateCv(int id, String name, String jsonCv) throws IOException {

		String stmt = "UPDATE " + TBL_CV + " SET " + COL_NAME + "= ?, "
				+ COL_JSON + "= ? WHERE " + COL_ID + "= ?";
		try (Connection con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement(stmt)) {
			ps.setString(1, name);
			ps.setString(2, jsonCv);
			ps.setInt(3, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void deleteCv(int id) throws IOException {
		String stmt = JdbcUtils.createDeleteWhere(TBL_CV, COL_ID);
		try (Connection con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement(stmt)) {
			ps.setInt(1, id);
			int update = ps.executeUpdate();
			if (update == 0) {
				throw new NoSuchElementException("Id '" + id + "' not found!");
			}
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	@Override
	public List<CvOverview> listCvs() {
		String stmt = JdbcUtils.createSelectWhere(TBL_CV);

		List<CvOverview> cvList = new ArrayList<>();

		try (Connection con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement(stmt)) {

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					cvList.add(new CvOverview(rs.getInt(COL_ID), rs
							.getString(COL_NAME)));
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return cvList;
	}
}