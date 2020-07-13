package service;

import java.sql.SQLException;

import model.MyFile;

public interface LogService {
	
	public boolean insertLog(int configID, String fileName, String action, String status, String fileTimeStamp, String password) throws SQLException;
//	public MyFile getFileWithStatus(String status) throws SQLException;
	//Add password field
	public MyFile getFileWithStatus(String action, String password) throws SQLException;

}
