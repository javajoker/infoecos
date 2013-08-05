package com.infoecos.util.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Database utilities
 * 
 * A connection pool is maintained, to get quick db connection
 * 
 * db.properties under runtime path is requried
 * 
 * E.g.,
 * 
 * user=root
 * 
 * password=xxxxxx
 * 
 * name=dbname
 * 
 * @author Ning Hu
 * 
 */
public class DBUtil {
	private static String dbuser = "", dbpwd = "", dbname = "", dbhost = "";
	static {
		Properties prop = new Properties();
		try {
			prop.load(DBUtil.class.getResourceAsStream("/db.properties"));
			dbuser = prop.getProperty("user");
			dbpwd = prop.getProperty("password");
			dbname = prop.getProperty("name");
			dbhost = prop.getProperty("host", "localhost");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static int CONNECTION_CAPABILITY = 50;

	private static Driver driver = null;

	private static Object NULL = new Object();
	private static List<Connection> freeConns = new ArrayList<Connection>();
	private static Map<Connection, Object> usedConns = new ConcurrentHashMap<Connection, Object>();

	/**
	 * Get db connection
	 * 
	 * @return null if exception occurred
	 */
	public synchronized static Connection getConnection() {
		try {
			Connection conn = null;
			if (freeConns.isEmpty()) {
				if (driver == null)
					driver = new org.gjt.mm.mysql.Driver();
				conn = driver.connect(String.format(
						"jdbc:mysql://%s/%s?user=%s&password=%s", dbhost,
						dbname, dbuser, dbpwd), null);
			} else {
				conn = freeConns.get(freeConns.size() - 1);
				freeConns.remove(conn);
				try {
					Statement stmt = conn.createStatement();
					stmt.execute("SELECT 1");
					stmt.close();
					stmt = null;
				} catch (Exception e) {
					if (!conn.isClosed())
						conn.close();
					conn = null;
					return getConnection();
				}
				// if (conn.isClosed()) {
				// // break weak reference, notify system gc
				// conn = null;
				// return getConnection();
				// }
			}
			usedConns.put(conn, NULL);
			return conn;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Release db connection
	 * 
	 * @param conn
	 */
	public synchronized static void releaseConnection(Connection conn) {
		if (null == conn)
			return;
		if (usedConns.containsKey(conn)) {
			usedConns.remove(conn);
			freeConns.add(conn);
			if (freeConns.size() > CONNECTION_CAPABILITY) {
				Connection con = freeConns.remove(0);
				try {
					con.close();
					con = null;
				} catch (Exception e) {
				}
			}
		}
	}

	private static Map<Statement, Connection> statConns = new ConcurrentHashMap<Statement, Connection>();

	/**
	 * Get prepared statement
	 * 
	 * @param sql
	 * @return null if exception occurred
	 */
	public static PreparedStatement getPreparedStatement(String sql) {
		try {
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			statConns.put(pstmt, conn);
			return pstmt;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Get prepared statement, change dbname if necessary, not in connection
	 * pool
	 * 
	 * @param dbname
	 * @param sql
	 * @return null if exception occurred
	 */
	public static PreparedStatement getPreparedStatement(String dbname,
			String sql) {
		try {
			if (driver == null)
				driver = new org.gjt.mm.mysql.Driver();
			Connection conn = driver.connect(String.format(
					"jdbc:mysql://%s/%s?user=%s&password=%s", dbhost, dbname,
					dbuser, dbpwd), null);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			return pstmt;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Get db statement
	 * 
	 * @return null if exception occurred
	 */
	public static Statement getStatement() {
		try {
			Connection conn = getConnection();
			Statement stmt = conn.createStatement();
			statConns.put(stmt, conn);
			return stmt;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Get db statement, change dbname if necessary, not stored in connection
	 * pool
	 * 
	 * @param dbname
	 * @return null if exception occurred
	 */
	public static Statement getStatement(String dbname) {
		try {
			if (driver == null)
				driver = new org.gjt.mm.mysql.Driver();
			Connection conn = driver.connect(String.format(
					"jdbc:mysql://%s/%s?user=%s&password=%s", dbhost, dbname,
					dbuser, dbpwd), null);
			Statement stmt = conn.createStatement();
			return stmt;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Release db statement, release connection
	 * 
	 * @param stmt
	 */
	public static void releaseStatement(Statement stmt) {
		if (stmt == null)
			return;
		try {
			stmt.close();
		} catch (Exception e) {
		}
		if (!statConns.containsKey(stmt))
			return;
		Connection conn = statConns.get(stmt);
		if (conn != null)
			try {
				conn.close();
				conn = null;
			} catch (Exception e) {
			}
		statConns.remove(stmt);
		stmt = null;
	}

	public static String getDBHost() {
		return dbhost;
	}

	public static void setDBHost(String dbhost) {
		DBUtil.dbhost = dbhost;
	}

	public static String getDBUser() {
		return dbuser;
	}

	public static void setDBUser(String dbuser) {
		DBUtil.dbuser = dbuser;
	}

	public static String getDBPassword() {
		return dbpwd;
	}

	public static void setDBPassword(String dbpwd) {
		DBUtil.dbpwd = dbpwd;
	}

	public static int getConnectionCapability() {
		return CONNECTION_CAPABILITY;
	}

	public static void setConnectionCapability(int connectionCapability) {
		CONNECTION_CAPABILITY = connectionCapability;
	}
}
