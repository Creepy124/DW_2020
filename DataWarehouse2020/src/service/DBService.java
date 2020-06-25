package service;

public interface DBService {
	
	public boolean existTable(String table_name);
	public boolean insertValues(String target_table, String column_list, String values);
	public boolean createTable(String table_name, String variables, String column_list);
	public boolean deleteTable(String table_name);

}
