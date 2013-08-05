package com.infoecos.nlp.resource.dictionary.wiktionary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.infoecos.util.db.DBUtil;

public class DictionaryUtil {
	private static String DB_NAME = "infoecos";

	public static Set<String> getKeys(String tblName) {
		return getKeys(tblName, "");
	}

	public static Set<String> getKeys(String tblName, String condition) {
		Set<String> keys = new HashSet<String>();

		Connection conn = DBUtil.getConnection(DB_NAME);
		if (conn == null)
			throw new NullPointerException();

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(String.format("SELECT `key` FROM %s %s",
					tblName, condition));
			while (rs.next()) {
				keys.add(rs.getString("key").toLowerCase());
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.releaseConnection(conn);
		}
		return keys;
	}
}
