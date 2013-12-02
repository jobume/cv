package se.sogeti.umea.cvconverter.adapter.persistence.jdbc;

import junit.framework.Assert;

import org.junit.Test;

public class JdbcUtilsTest {

	@Test
	public void testCreateSelect() {
		String expected = "SELECT * FROM filerecord";
		Assert.assertEquals(expected, JdbcUtils.createSelect("filerecord"));
	}

	@Test
	public void testCreateSelectWhere() {
		String expected = "SELECT * FROM filerecord WHERE name=(?) AND type=(?)";
		Assert.assertEquals(expected,
				JdbcUtils.createSelectWhere("filerecord", "name", "type"));
	}

	@Test
	public void testCreateInsert() {
		String expected = "INSERT INTO filerecord (name, filetype, url) VALUES (?, ?, ?)";
		Assert.assertEquals(expected,
				JdbcUtils.createInsert("filerecord", "name", "filetype", "url"));
	}

	@Test
	public void testDeleteWhere() {
		String expected = "DELETE FROM filerecord WHERE name=(?) AND type=(?)";
		Assert.assertEquals(expected,
				JdbcUtils.createDeleteWhere("filerecord", "name", "type"));
	}

}
