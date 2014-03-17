package se.sogeti.umea.cvconverter.adapter.persistence.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import se.sogeti.umea.cvconverter.adapter.persistence.file.FileRecord;
import se.sogeti.umea.cvconverter.adapter.persistence.file.FileRecordRepository;
import se.sogeti.umea.cvconverter.application.Repository;

@Repository
public class JdbcFileRecordRepository implements FileRecordRepository {

	private final static String TBL_FILERECORD = "filerecord";
	private final static String COL_NAME = "name";
	private final static String COL_ID = "id";
	private final static String COL_TYPE = "filetype";
	private final static String COL_URL = "url";

	// @Resource(name = "jdbc/h2Database")
	@Resource(lookup = "java:jboss/datasources/MysqlDS")
	DataSource ds;

	@Override
	public FileRecord createFileRecord(String name, String type, String url) {

		String stmt = JdbcUtils.createInsert(TBL_FILERECORD, COL_NAME,
				COL_TYPE, COL_URL);
		int id = -1;
		try (Connection con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement(stmt,
						Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, name);
			ps.setString(2, type);
			ps.setString(3, url);
			ps.executeUpdate();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			if (generatedKeys.next()) {
				id = generatedKeys.getInt(1);
			} else {
				throw new RuntimeException("ID generation failed for file: "
						+ name + ", " + type + ", " + url);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return new FileRecord(id, name, type, url);
	}

	@Override
	public List<FileRecord> listFileRecords(String type) {
		String stmt;
		if (type != null) {
			stmt = JdbcUtils.createSelectWhere(TBL_FILERECORD, COL_TYPE);
		} else {
			stmt = JdbcUtils.createSelectWhere(TBL_FILERECORD);
		}
		List<FileRecord> fileRecords = new ArrayList<>();
		try (Connection con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement(stmt);) {
			if (type != null) {
				ps.setString(1, type);
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				fileRecords.add(new FileRecord(rs.getInt(COL_ID), rs
						.getString(COL_NAME), rs.getString(COL_TYPE), rs
						.getString(COL_URL)));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return fileRecords;
	}

	@Override
	public void deleteFileRecord(int id) {
		String stmt = JdbcUtils.createDeleteWhere(TBL_FILERECORD, COL_ID);
		try (Connection con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement(stmt)) {
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public FileRecord getFileRecord(int id) {
		String stmt = JdbcUtils.createSelectWhere(TBL_FILERECORD, COL_ID);

		try (Connection con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement(stmt);) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new FileRecord(rs.getInt(COL_ID),
						rs.getString(COL_NAME), rs.getString(COL_TYPE),
						rs.getString(COL_URL));
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateFileRecord(int id, String name, String url) {
		throw new NoSuchMethodError(
				"updateFileRecord(id, name, url) not implemented!");
	}
}
