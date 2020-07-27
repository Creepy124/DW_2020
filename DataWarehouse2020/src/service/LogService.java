package service;

import java.sql.SQLException;

import model.MyFile;

public interface LogService {
	
	public MyFile getFileWithAction(int configID, String action) throws SQLException;
	public int insertLog(int configID, String fileName, String action, String status) throws SQLException;
	public int updateAction(int configID, String fileName, String newAction) throws SQLException;
	public int updateStatus(int configID, String fileName, String status) throws SQLException;

}
