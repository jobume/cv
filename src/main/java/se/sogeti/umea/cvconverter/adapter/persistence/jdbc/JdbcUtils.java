package se.sogeti.umea.cvconverter.adapter.persistence.jdbc;

public class JdbcUtils {

	/**
	 * Creates an insert statement.
	 * 
	 * @param table
	 *            the table to insert into.
	 * @param cols
	 *            the name of the columns to insert.
	 * 
	 * @return an insert statement in the form: INSERT INTO tbl (col1, col2,
	 *         col3) VALUES (?, ?, ?)
	 */
	public static String createInsert(String table, String... cols) {
		StringBuilder stmt = new StringBuilder();
		stmt.append("INSERT INTO ").append(table).append(" (");
		for (int i = 0; i < cols.length; i++) {
			if (i > 0) {
				stmt.append(", ");
			}
			stmt.append(cols[i]);
		}
		stmt.append(") VALUES (");
		for (int j = 0; j < cols.length; j++) {
			if (j > 0) {
				stmt.append(", ");
			}
			stmt.append("?");
		}
		stmt.append(")");
		return stmt.toString();
	}

	public static String createDeleteWhere(String table, String... where) {
		StringBuilder stmt = new StringBuilder();

		stmt.append("DELETE FROM ").append(table).append(" WHERE ");
		for (int i = 0; i < where.length; i++) {
			if (i > 0) {
				stmt.append(" AND ");
			}
			stmt.append(where[i]).append("=(?)");
		}

		return stmt.toString();
	}

	public static String createSelect(String table) {
		return JdbcUtils.createSelectWhere(table, new String[]{});
	}
	
	public static String createSelectWhere(String table, String... where) {
		StringBuilder stmt = new StringBuilder();

		stmt.append("SELECT * FROM ").append(table);
		
		if (where.length > 0) {
			stmt.append(" WHERE ");
			for (int i = 0; i < where.length; i++) {
				if (i > 0) {
					stmt.append(" AND ");
				}
				stmt.append(where[i]).append("=(?)");
			}
		}

		return stmt.toString();
	}

}
