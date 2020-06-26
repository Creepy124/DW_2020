package service;

import java.sql.SQLException;

public interface DBService {
	
	public boolean existTable(String table_name) throws SQLException;
	public boolean insertValues(String target_table, String column_list, String values) throws SQLException;
	public boolean createTable(String table_name, String variables, String column_list) throws SQLException;
	public boolean deleteTable(String table_name) throws SQLException;

}
