package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	//testing
	@SuppressWarnings("unused")
	public static Connection getConnection(String dbName) {
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/" + dbName +"?useSSL=false&charact erEncoding=utf8";
		String user = "root";
		String password = "";
		try {
			if (con == null || con.isClosed()) {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(url, user, password);
				return con;

			} else {
				return con;
			}
		} catch (SQLException | ClassNotFoundException e) {
			return null;
		}
	}

}
