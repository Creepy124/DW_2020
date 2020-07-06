package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	//password field
	@SuppressWarnings("unused")
	public static Connection getConnection(String dbName, String password) {
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/" + dbName +"?useSSL=false&characterEncoding=utf8";
		String user = "root";
//		String password = "";
		try {
			if (con == null || con.isClosed()) {
//				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(url, user, password);
				return con;

			} else {
				return con;
			}
		} catch (SQLException e) {
//			System.out.println(e.getMessage() + " asddfsfd");
			return null;
		}
	}
public static void main(String[] args) {
	DBConnection d = new DBConnection();
	d.getConnection("control","langtutrunggio");
}
}
