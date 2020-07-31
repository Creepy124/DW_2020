package service;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBService {
	
	public boolean existTable(String table_name) throws SQLException;
	public int insertValues(String target_table, String column_list, String values) throws SQLException;
	public int createTable(String table_name, String variables, String column_list) throws SQLException;
	public int truncateTable(String table_name) throws SQLException;
	public int loadFile(String sourceFile, String tableName, String dilimiter) throws SQLException;
	public void tranformNullValue(String stagingName, String col, String defaut) throws SQLException;
	public void DeleteNullID(String stagingName, String col) throws SQLException;
}
