package service;

import java.sql.SQLException;

public interface DBService {
	
	public boolean existTable(String table_name) throws SQLException;
	public int insertValues(String target_table, String column_list, String values) throws SQLException;
	public int createTable(String table_name, String variables, String column_list) throws SQLException;
	public int deleteTable(String table_name) throws SQLException;
	public int loadFile(String sourceFile, String tableName, String dilimiter) throws SQLException;

}
