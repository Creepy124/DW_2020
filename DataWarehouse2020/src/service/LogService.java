package service;

import java.sql.SQLException;

import model.MyFile;

public interface LogService {
	
	public boolean insertLog(int configID, String fileName, String fileType, String status, String fileTimeStamp) throws SQLException;
	public MyFile getFileWithStatus(String status) throws SQLException;

}
