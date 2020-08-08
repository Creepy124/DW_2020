package service;

import java.sql.ResultSet;
import java.sql.SQLException;
/*
 * this class relative to progressing DB (mostly in configuration table)
 */
public interface DBService {
	//check table exist or not
	public boolean existTable(String tableName) throws SQLException;
	
	//create new table
	public int createTable(String tableName, String variables, String column_list) throws SQLException;
	
	//truncate table
	public int truncateTable(String tableName) throws SQLException;
	
	//load all data in file 
	public int loadFile(String sourceFile, String tableName, String dilimiter) throws SQLException;
	
	// transform null value to default
	public int tranformNullValue(String tableName, String col, String defaut) throws SQLException;
	
	//if row which has null ID
	public int deleteNullID(String tableName, String col) throws SQLException;
	
	//call procedure from database
	public void callProcedure(String procName) throws SQLException;	
	
	//if flag == prepare then run 1 process
	public int getFlag(String state) throws SQLException;
	
	//update config state: prepare -> done step 1 -> done step 2 -> done step 3 -> prepare(new config)
	public void updateFlag(int config_id, String state) throws SQLException;
	
}
