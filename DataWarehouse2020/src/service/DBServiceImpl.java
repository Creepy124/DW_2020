package service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBConnection;

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
	public boolean insertValues(String target_table, String column_list, String values) throws SQLException {
		Connection connection;
		connection = DBConnection.getConnection(targetDBName);
		PreparedStatement ps = connection
				.prepareStatement("INSERT INTO " + target_table + " (" + column_list + ") VALUES " + values);
		System.out.println("INSERT INTO " + target_table + " (" + column_list + ") VALUES " + values);
		ps.executeUpdate();
		connection.close();
		return true;
	}

	@Override
	public boolean createTable(String table_name, String variables, String column_list) throws SQLException {
		String sql = "CREATE TABLE " + table_name + " (";
		String[] vari = variables.split(",");
		String[] col = column_list.split(",");
		for (int i = 0; i < vari.length; i++) {
			sql += col[i] + " " + vari[i] + " NOT NULL,";
		}
		sql = sql.substring(0, sql.length() - 1) + ")";
		System.out.println(sql);
		Connection connection;
		connection = DBConnection.getConnection(targetDBName);
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.executeUpdate();
		connection.close();
		return true;
	}

	@Override
	public boolean deleteTable(String table_name) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
