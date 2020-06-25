package service;

import model.MyFile;

public interface LogService {
	
	public boolean insertLog(int configID, String fileName, String fileType, String status, String fileTimeStamp);
	public MyFile getFileWithStatus(String status);

}
