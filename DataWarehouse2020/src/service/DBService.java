package service;

public interface DBService {
	
	public boolean existTable(String table_name);
	public boolean insertValues(String column_list, String values, String target_table);
	public boolean createTable(String table_name, String variables, String column_list);

}
