package service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBConnection;
import model.Configuration;

public class DBServiceImpl implements DBService {
	String targetDBName;

	public DBServiceImpl(String targetDBName) {
		this.targetDBName = targetDBName;
	}

	@Override
	public boolean existTable(String table_name) throws SQLException {
		DatabaseMetaData dbm = DBConnection.getConnection(targetDBName).getMetaData();
		ResultSet tables = dbm.getTables(null, null, table_name, null);
		if (tables.next()) {
			return true;
		}
		return false;
	}

	@Override
	public int insertValues(String target_table, String column_list, String values) throws SQLException {
		Connection connection = DBConnection.getConnection(targetDBName);
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
		Connection connection = DBConnection.getConnection(targetDBName);
		PreparedStatement ps = connection.prepareStatement(sql);
		connection.close();
		return ps.executeUpdate();
	}

	@Override
	public int truncateTable(String table_name) throws SQLException {
		Connection connection = DBConnection.getConnection(targetDBName);
		PreparedStatement ps = connection.prepareStatement("TRUNCATE TABLE ?");
		ps.setString(1, table_name);
		connection.close();
		return ps.executeUpdate();
	}
	
	//LOAD DATA LOCAL INFILE '/path/pet.txt' INTO TABLE pet;
	@Override
	public int loadFile(String sourceFile, String tableName, String dilimiter) throws SQLException {
		Connection connection = DBConnection.getConnection(targetDBName);
//		sourceFile = sourceFile.replace("\\", "\\\\");
		PreparedStatement ps = connection.prepareStatement("LOAD DATA LOCAL INFILE '"+sourceFile+"' INTO TABLE "+tableName+"\r\n" + 
															"FIELDS TERMINATED BY '"+dilimiter+"' \r\n" + 
															"ENCLOSED BY '\"' \r\n" + 
															"LINES TERMINATED BY '\\r\\n'");
		System.out.println("LOAD DATA LOCAL INFILE '"+sourceFile+"' INTO TABLE "+tableName+"\r\n" + 
				"FIELDS TERMINATED BY '"+dilimiter+"' \r\n" + 
				"ENCLOSED BY '\"' \r\n" + 
				"LINES TERMINATED BY '\\r\\n'");
		return ps.executeUpdate();
	}
	
	public static void main(String[] args) {
		Configuration config = new Configuration("sinhvien");
		DBService test = new DBServiceImpl("staging");
		try {
//			System.out.println(test.createTable("sinhvien", config.getVariabless(), config.getFileColumnList()));
			System.out.println(test.loadFile("local\\test\\data_1999-12-10_018.txt", "sinhvien","|"));
		} catch (SQLException e) {
			System.out.println("error");
			e.printStackTrace();
		}
	}

}
