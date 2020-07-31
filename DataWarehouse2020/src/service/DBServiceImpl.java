package service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import db.DBConnection;
import model.Configuration;

public class DBServiceImpl implements DBService {
	String targetDBName;
	String password;
	String userName;

	public DBServiceImpl(String targetDBName, String userName, String password) {
		this.targetDBName = targetDBName;
		this.password = password;
		this.userName = userName;
	}

	@Override
	public boolean existTable(String table_name) throws SQLException {
		DatabaseMetaData dbm = DBConnection.getConnection(targetDBName, userName, password).getMetaData();
		ResultSet tables = dbm.getTables(null, null, table_name, null);
		if (tables.next()) {
			return true;
		}
		return false;
	}

	@Override
	public int insertValues(String target_table, String column_list, String values) throws SQLException {
		Connection connection = DBConnection.getConnection(targetDBName, userName, password);
		PreparedStatement ps = connection
				.prepareStatement("INSERT INTO " + target_table + " (" + column_list + ") VALUES " + values);
		System.out.println("INSERT INTO " + target_table + " (" + column_list + ") VALUES " + values);
		return ps.executeUpdate();
	}

	@Override
	public int createTable(String table_name, String variables, String column_list) throws SQLException {
		String sql = "CREATE TABLE " + table_name + " (";
		String[] vari = variables.split(",");
		String[] col = column_list.split(",");
		for (int i = 0; i < vari.length; i++) {
			sql += col[i] + " " + vari[i] + " ,";
		}
		sql = sql.substring(0, sql.length() - 1) + ")";
		System.out.println(sql);
		Connection connection = DBConnection.getConnection(targetDBName, userName, password);
		PreparedStatement ps = connection.prepareStatement(sql);
		return ps.executeUpdate();
	}

	@Override
	public int truncateTable(String table_name) throws SQLException {
		Connection connection = DBConnection.getConnection(targetDBName, userName, password);
		PreparedStatement ps = connection.prepareStatement("TRUNCATE TABLE " + this.targetDBName + "." + table_name);
//		ps.setString(1, table_name);
		System.out.println("TRUNCATE TABLE " + table_name);
		return ps.executeUpdate();
	}

	// LOAD DATA LOCAL INFILE '/path/pet.txt' INTO TABLE pet;
	@Override
	public int loadFile(String sourceFile, String tableName, String dilimiter) throws SQLException {
		Connection connection = DBConnection.getConnection(targetDBName, userName, password);
//		sourceFile = sourceFile.replace("\\", "\\\\");
		PreparedStatement ps = connection.prepareStatement("LOAD DATA INFILE '" + sourceFile + "' INTO TABLE " + tableName + " Charset Latin1\r\n"
				+ "FIELDS TERMINATED BY '" + dilimiter + "' \r\n" + "ENCLOSED BY '\"' \r\n"
				+ "LINES TERMINATED BY '\\r\\n'" + "IGNORE 1 lines");
		System.out.println("LOAD DATA INFILE '" + sourceFile + "' INTO TABLE " + tableName + "\r\n"
				+ "FIELDS TERMINATED BY '" + dilimiter + "' \r\n" + "ENCLOSED BY '\"' \r\n"
				+ "LINES TERMINATED BY '\\r\\n'");
		return ps.executeUpdate();
	}
	
	public void tranformNullValue(String stagingName, String col, String defaut) throws SQLException {
		Connection con = DBConnection.getConnection("staging", userName, password);
		String sql = "Update "+stagingName +"set "+col +" = "+ defaut +"where "+ col + " isNull";
		PreparedStatement pre = con.prepareStatement(sql);
		pre.executeQuery();
	}
	
	public void DeleteNullID(String stagingName, String col) throws SQLException {
		Connection con = DBConnection.getConnection("staging", userName, password);
		String sql = "Delete from " + stagingName + "where " + col + "isNull";
		
	}
	public static void main(String[] args) {
		Configuration config = new Configuration("monhoc", "root", "1234");
		DBService test = new DBServiceImpl("staging", "root", "1234");
		try {
//			System.out.println(test.createTable(config.getConfigName(), config.getFileVariables(), config.getFileColumnList()));
			System.out.println(test.truncateTable(config.getConfigName()));
			System.out.println(test.loadFile("E:\\\\Warehouse\\\\sinhvien_chieu_nhom4.txt", "sinhvien", "|"));
		} catch (Exception e) {
			System.out.println("error");
			e.printStackTrace();
		}
	}

}
