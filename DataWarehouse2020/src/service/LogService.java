package service;

import java.sql.SQLException;

import model.MyFile;

public interface LogService {
	
	//get file with "ER" action for step 2
	public MyFile getFileWithAction(int configID, String action) throws SQLException;
	
	//writting log after download
	public int insertLog(int configID, String fileName, String action, String status) throws SQLException;
	
	//update log action 
	public int updateAction(int configID, String newAction) throws SQLException;

}
