package service;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBService {
	
	public boolean existTable(String tableName) throws SQLException;
	public int createTable(String tableName, String variables, String column_list) throws SQLException;
	public int truncateTable(String tableName) throws SQLException;
	public int loadFile(String sourceFile, String tableName, String dilimiter) throws SQLException;
	public int tranformNullValue(String tableName, String col, String defaut) throws SQLException;
	public int deleteNullID(String tableName, String col) throws SQLException;
	public ResultSet loadFromStaging(String tableName) throws SQLException;
	public void callProcedure(String procName) throws SQLException;	
	
}
