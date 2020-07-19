package service;

import java.sql.SQLException;

import model.MyFile;

public interface LogService {
	
	public boolean insertLog(int configID, String fileName, String action, String status, String fileTimeStamp) throws SQLException;
	public MyFile getFileWithStatus(int configID, String action) throws SQLException;

}
