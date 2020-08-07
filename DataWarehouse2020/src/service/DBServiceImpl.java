package service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.CallableStatement;

import db.DBConnection;
import model.Configuration;
/*
 * This class contain all methods that relative to database such as: create table, truncate, transformed , load file
 */
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
	public boolean existTable(String tableName) throws SQLException {
		DatabaseMetaData dbm = DBConnection.getConnection(targetDBName, userName, password).getMetaData();
		ResultSet tables = dbm.getTables(null, null, tableName, null);
		if (tables.next()) {
			return true;
		}
		return false;
	}

	@Override
	public int createTable(String tableName, String variables, String column_list) throws SQLException {
		String sql = "CREATE TABLE " + tableName + " (";
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
	public int truncateTable(String tableName) throws SQLException {
		Connection connection = DBConnection.getConnection(targetDBName, userName, password);
		PreparedStatement ps = connection.prepareStatement("TRUNCATE TABLE " + this.targetDBName + "." + tableName);
//		ps.setString(1, table_name);
		System.out.println("TRUNCATE TABLE " + tableName);
		return ps.executeUpdate();
	}

	// LOAD DATA LOCAL INFILE '/path/pet.txt' INTO TABLE pet;
	@Override
	public int loadFile(String sourceFile, String tableName, String dilimiter) throws SQLException {
		Connection connection = DBConnection.getConnection(targetDBName, userName, password);
//		sourceFile = sourceFile.replace("\\", "\\\\");
		PreparedStatement ps = connection.prepareStatement("LOAD DATA INFILE '" + sourceFile + "' INTO TABLE " + targetDBName+"."+tableName + "\r\n" + "FIELDS TERMINATED BY '"
				+ dilimiter + "' \r\n" + "ENCLOSED BY '\"' \r\n" + "LINES TERMINATED BY '\\r\\n'" + "IGNORE 1 lines");
		System.out.println(
				"LOAD DATA INFILE '" + sourceFile + "' INTO TABLE " + targetDBName+"."+tableName + "\r\n" + "FIELDS TERMINATED BY '"
						+ dilimiter + "' \r\n" + "ENCLOSED BY '\"' \r\n" + "LINES TERMINATED BY '\\r\\n'");
		return ps.executeUpdate();
	}

	@Override
	public int tranformNullValue(String tableName, String col, String defaut) throws SQLException {
		Connection con = DBConnection.getConnection(targetDBName, userName, password);
		String sql = "Update " + tableName + " set " + col + " = '" + defaut + "' where " + col + " is Null";
		PreparedStatement pre = con.prepareStatement(sql);
		System.out.println(sql);
		return pre.executeUpdate();
	}

	@Override
	public int deleteNullID(String tableName, String col) throws SQLException {
		Connection con = DBConnection.getConnection(targetDBName, userName, password);
		String sql = "Delete from " + tableName + " where " + col + " is Null";
		System.out.println(sql);
		PreparedStatement pre = con.prepareStatement(sql);
		return pre.executeUpdate();
	}

	@Override
	public ResultSet loadFromStaging(String tableName) throws SQLException {
		Connection con = DBConnection.getConnection(targetDBName, userName, password);
		String sql = "Select * from " + tableName;
		PreparedStatement pre = con.prepareStatement(sql);
		return pre.executeQuery();
	}
	
	@Override
	public void callProcedure(String procName) throws SQLException {
		Connection con = DBConnection.getConnection(targetDBName, userName, password);
		String query = "{CALL "+procName+"()}";
		CallableStatement stmt = (CallableStatement) con.prepareCall(query);
		stmt.execute();
	}

	@Override
	public int getFlag(String state) throws SQLException{
		Connection con = DBConnection.getConnection(targetDBName, userName, password);
		String sql = "Select config_id from configuration where flag = " + state;
		PreparedStatement pre = con.prepareStatement(sql);
		ResultSet rs = pre.executeQuery();
		if(rs.next()) {
			 return rs.getInt("config_id");
		}
		return 0;
	}
	
	@Override
	public void updateFlag(int config_id, String state){
		Connection con = DBConnection.getConnection(targetDBName, userName, password);
		String sql = "Update configuration set flag = " +state +"where config_id = " +config_id;
		PreparedStatement pre;
		try {
			pre = con.prepareStatement(sql);
			pre.executeUpdate();
			
		} catch (SQLException e) {
		
		}
		
	}
	
	
	public static void main(String[] args) {
		Configuration config = new Configuration(1, "root", "");
		DBService test = new DBServiceImpl("staging", "root", "");
		try {
//			System.out.println(test.createTable(config.getConfigName(), config.getFileVariables(), config.getFileColumnList()));
//			System.out.println(test.truncateTable(config.getConfigName()));
//			System.out.println(test.loadFile("C:\\\\Users\\\\Phuong\\\\git\\\\DW_2020\\\\Datawarehouse2020\\\\local\\\\test\\\\sinhvien_chieu_nhom9.txt", "sinhvien", "|"));
//			test.deleteNullID("sinhvien", "mssv");
//			test.tranformNullValue("sinhvien", "sdt", "null");
			test.callProcedure("loadStudent");
		} catch (Exception e) {
			System.out.println("error");
			e.printStackTrace();
		}
	}

}
