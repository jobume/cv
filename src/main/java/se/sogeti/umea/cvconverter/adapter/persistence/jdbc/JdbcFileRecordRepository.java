package se.sogeti.umea.cvconverter.adapter.persistence.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	private final static String COL_TYPE = "filetype";
	private final static String COL_URL = "url";

	@Resource(name = "jdbc/h2Database")
	DataSource ds;

	@Override
	public FileRecord createFileRecord(String name, String type, String url) {

		String stmt = JdbcUtils.createInsert(TBL_FILERECORD, COL_NAME,
				COL_TYPE, COL_URL);
		try (Connection con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement(stmt)) {
			ps.setString(1, name);
			ps.setString(2, type);
			ps.setString(3, url);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return new FileRecord(name, type, url);
	}

	@Override
	public List<FileRecord> listFileRecords(String type) {
		String stmt = JdbcUtils.createSelect(TBL_FILERECORD);
		List<FileRecord> fileRecords = new ArrayList<>();
		try (Connection con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement(stmt);
				ResultSet rs = ps.executeQuery();) {
			while (rs.next()) {
				fileRecords.add(new FileRecord(rs.getString(COL_NAME), rs
						.getString(COL_TYPE), rs.getString(COL_URL)));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return fileRecords;
	}

	@Override
	public void deleteFileRecord(String name, String type) {
		String stmt = JdbcUtils.createDeleteWhere(TBL_FILERECORD, COL_NAME,
				COL_TYPE);
		try (Connection con = ds.getConnection();
				PreparedStatement ps = con.prepareStatement(stmt)) {
			ps.setString(1, name);
			ps.setString(2, type);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
