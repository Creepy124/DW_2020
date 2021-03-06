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
//		System.out.println(sql);
		Connection connection = DBConnection.getConnection(targetDBName, userName, password);
		PreparedStatement ps = connection.prepareStatement(sql);
		return ps.executeUpdate();
	}

	@Override
	public int truncateTable(String tableName) throws SQLException {
		Connection connection = DBConnection.getConnection(targetDBName, userName, password);
		PreparedStatement ps = connection.prepareStatement("TRUNCATE TABLE " + this.targetDBName + "." + tableName);
//		System.out.println("TRUNCATE TABLE " + tableName);
		return ps.executeUpdate();
	}

	@Override
	public int loadFile(String sourceFile, String tableName, String dilimiter) throws SQLException {
		Connection connection = DBConnection.getConnection(targetDBName, userName, password);
		PreparedStatement ps = connection.prepareStatement("LOAD DATA INFILE '" + sourceFile + "' INTO TABLE " + targetDBName+"."+tableName + "\r\n" + "FIELDS TERMINATED BY '"
				+ dilimiter + "' \r\n" + "ENCLOSED BY '\"' \r\n" + "LINES TERMINATED BY '\\n'" + "IGNORE 1 lines");
		System.out.println(
				"LOAD DATA INFILE '" + sourceFile + "' INTO TABLE " + targetDBName+"."+tableName + "\r\n" + "FIELDS TERMINATED BY '"
						+ dilimiter + "' \r\n" + "ENCLOSED BY '\"' \r\n" + "LINES TERMINATED BY '\\n'" + "IGNORE 1 lines");
		return ps.executeUpdate();
	}

	@Override
	public int tranformNullValue(String tableName, String col, String defaut) throws SQLException {
		Connection con = DBConnection.getConnection(targetDBName, userName, password);
		String sql = "Update " + tableName + " set " + col + " = '" + defaut + "' where " + col + " is Null";
		PreparedStatement pre = con.prepareStatement(sql);
//		System.out.println(sql);
		return pre.executeUpdate();
	}

	@Override
	public int deleteNullID(String tableName, String col) throws SQLException {
		Connection con = DBConnection.getConnection(targetDBName, userName, password);
		String sql = "Delete from " + tableName + " where " + col + " is Null";
//		System.out.println(sql);
		PreparedStatement pre = con.prepareStatement(sql);
		return pre.executeUpdate();
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
		String sql = "Select config_id from configuration where flag = '" + state+"'";
		PreparedStatement pre = con.prepareStatement(sql);
		ResultSet rs = pre.executeQuery();
		if(rs.next()) {
			 return rs.getInt("config_id");
		}
		return 0;
	}
	
	@Override
	public void updateFlag(int config_id, String state) throws SQLException{
		Connection con = DBConnection.getConnection(targetDBName, userName, password);
		String sql = "Update configuration set flag = '" +state +"' where config_id = " +config_id;
//		System.out.println(sql);
		PreparedStatement pre;
		pre = con.prepareStatement(sql);
		pre.executeUpdate();
	}
	
	@Override
	public int nextConfig(int configID) throws SQLException {
		
		Connection con = DBConnection.getConnection(targetDBName, userName, password);
		String sql = "Select flag from configuration where config_id = " + configID;
		
		PreparedStatement pre = con.prepareStatement(sql);
		ResultSet rs = pre.executeQuery();
		if(rs.next()) {
			String tmp = rs.getString("flag");
			if(tmp.isEmpty() || tmp == null) {
				return configID;
			}
			else {
				configID++;
				return nextConfig(configID);
			}
		}
		return 0;
	}
	
	
	public static void main(String[] args) {
		Configuration config = new Configuration(1, "root", "1234");
		DBService test = new DBServiceImpl("control", "root", "1234");
		try {
//			System.out.println(test.createTable(config.getConfigName(), config.getFileVariables(), config.getFileColumnList()));
//			System.out.println(test.truncateTable(config.getConfigName()));
			System.out.println(test.loadFile("E:\\\\Warehouse2\\\\sinhvien_chieu_nhom9.txt", "sinhvien", "|"));
//			test.deleteNullID("sinhvien", "mssv");
//			test.tranformNullValue("sinhvien", "sdt", "null");
//			test.callProcedure("loadStudent");
//			test.updateFlag(5, "ee");1
			
		} catch (Exception e) {
			System.out.println("error");
			e.printStackTrace();
		}
	}

	

}
