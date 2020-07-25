package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import db.DBConnection;
import model.MyFile;

public class LogServiceImpl implements LogService {

	Connection connection;

	public LogServiceImpl(String targetDBName, String userName, String password) {
		connection = DBConnection.getConnection(targetDBName, userName, password);
	}

	@Override
	public boolean insertLog(int configID, String fileName, String action, String status, String fileTimeStamp)
			throws SQLException {
		PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO log (config_id, file_name, file_type, action, status, file_timestamp) value (?,?,?,?,?,?)");
		ps.setInt(1, configID);
		ps.setString(2, fileName);
		ps.setString(3, fileName.substring(fileName.indexOf('.') + 1));
		ps.setString(4, action);
		ps.setString(5, status);
		ps.setString(6, fileTimeStamp);
		ps.executeUpdate();
		connection.close();
		return true;
	}

	@Override
	public MyFile getFileWithStatus(int configID, String action) throws SQLException {
		MyFile file = new MyFile();
		PreparedStatement ps = connection
				.prepareStatement("SELECT file_name, file_type FROM log WHERE config_id=? and action=? and status is null");
		ps.setInt(1, configID);
		ps.setString(2, action);
		ResultSet rs = ps.executeQuery();
		rs.last();
		if (rs.getRow() >= 1) {
			rs.first();
			file.setFileName(rs.getString("file_name"));
			file.setFileType(rs.getString("file_type"));
		} else {
			return null;
		}
		return file;
	}

	public static void main(String[] args) throws SQLException {
		LogService log = new LogServiceImpl("control","root","1234");
//		if (log.getFileWithStatus(2,"ER") != null) {
//			System.out.println(log.getFileWithStatus(2,"ER").toString());
//		} else {
//			System.out.println("No file status like that");
//		}
		log.insertLog(1, "a.txt", "ER", null, LocalTime.now().toString());
	}

}
