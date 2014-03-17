package se.sogeti.umea.cvconverter.adapter.persistence.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.sogeti.umea.cvconverter.application.CvOverview;
import se.sogeti.umea.cvconverter.application.JsonCvRepository;
import se.sogeti.umea.cvconverter.application.Repository;

@Repository
public class JdbcJsonCvRepository implements JsonCvRepository {

	private final static Logger LOG = LoggerFactory
			.getLogger(JsonCvRepository.class);

	private final static String TBL_CV = "cv";
	private final static String COL_ID = "id";
	private final static String COL_NAME = "name";
	private final static String COL_JSON = "json";
	private final static String COL_OFFICE = "office";
	private final static String COL_TS = "ts";
	private final static String COL_PORTRAIT_ID = "portrait_id";

	@Resource(lookup = "java:jboss/datasources/MysqlDS")
	DataSource ds;

	@Override
	public int createCv(String name, String office, int portraitId,
			String jsonCv) throws IOException {

		String stmt = JdbcUtils.createInsert(TBL_CV, COL_NAME, COL_OFFICE,
				COL_JSON, COL_PORTRAIT_ID);
		try (Connection con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement(stmt,
						Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, name);
			ps.setString(2, office);
			ps.setString(3, jsonCv);
			ps.setInt(4, portraitId);
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
	public void updateCv(int id, String name, int portraitId, String jsonCv)
			throws IOException {

		String stmt = "UPDATE " + TBL_CV + " SET " + COL_NAME + "= ?, "
				+ COL_PORTRAIT_ID + "=?, " + COL_JSON + "= ? WHERE " + COL_ID
				+ "= ?";
		try (Connection con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement(stmt)) {
			ps.setString(1, name);
			ps.setInt(2, portraitId);
			ps.setString(3, jsonCv);
			ps.setInt(4, id);
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
	public List<CvOverview> listCvs() throws IOException {
		return this.listCvs(null);
	}

	@Override
	public List<CvOverview> listCvs(String office) throws IOException {
		String stmt;
		if (office != null) {
			stmt = JdbcUtils.createSelectWhere(TBL_CV, COL_OFFICE);
			LOG.debug("List stmt: " + stmt);
		} else {
			stmt = JdbcUtils.createSelectWhere(TBL_CV);
		}

		List<CvOverview> cvList = new ArrayList<>();

		try (Connection con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement(stmt + " ORDER BY "
						+ COL_TS + " DESC")) {
			if (office != null) {
				ps.setString(1, office);
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					cvList.add(new CvOverview(rs.getInt(COL_ID), rs
							.getString(COL_NAME), rs.getString(COL_OFFICE), rs
							.getTimestamp(COL_TS)));
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return cvList;
	}

	@Override
	public int countPortraitIds(int portraitId) {
		String stmt = "SELECT count(*) FROM " + TBL_CV + " WHERE "
				+ COL_PORTRAIT_ID + "=?";
		try (Connection con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement(stmt + " ORDER BY "
						+ COL_TS + " DESC")) {
			ps.setInt(1, portraitId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					int count = rs.getInt(1);
					LOG.debug("Returning count: " + count + " for portraitId: "
							+ portraitId);
					return count;
				}
				return 0;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}