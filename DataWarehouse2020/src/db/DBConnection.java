package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	public static Connection getConnection(String dbName, String userName, String password) {
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/" + dbName
				+ "?useSSL=false&characterEncoding=utf8&allowLoadLocalInfile=true";
		try {
			if (con == null || con.isClosed()) {
				con = DriverManager.getConnection(url, userName, password);
			}
		} catch (SQLException e) {
			return null;
		}
		return con;
	}

	public static void main(String[] args) {
		DBConnection d = new DBConnection();
		d.getConnection("control", "root", "");
	}
}
