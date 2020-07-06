package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import db.DBConnection;
import model.MyFile;

public class LogServiceImpl implements LogService {

	public LogServiceImpl() {
	}

	@Override
	public boolean insertLog(String fileName, String fileType, String status, String fileTimeStamp,String password)
			throws SQLException {
		Connection connection;
		connection = DBConnection.getConnection("control", password);
		//Remove "active" field
//		PreparedStatement ps1 = connection.prepareStatement("UPDATE log SET active=0 WHERE file_name=?");
//		ps1.setString(1, fileName);
//		ps1.executeUpdate();
		PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO log (file_name, file_type, status, file_timestamp) value (?,?,?,?)");
//		ps.setInt(1, 1);
		ps.setString(1, fileName);
		ps.setString(2, fileType);
		ps.setString(3, status);
		ps.setString(4, fileTimeStamp);
		ps.executeUpdate();
		connection.close();
		return true;
	}

	@Override
	public MyFile getFileWithStatus(String status, String password) throws SQLException {
		MyFile file = new MyFile();
		Connection connection;
		connection = DBConnection.getConnection("control", password);
		PreparedStatement ps = connection
				.prepareStatement("SELECT file_name, file_type FROM log WHERE status=?");
		ps.setString(1, status);
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
		LogService log = new LogServiceImpl();
		log.insertLog("abc", "tas", "ER", LocalTime.now().toString(), "langtutrunggio");
//		
//		if (log.getFileWithStatus("ER","langtutrunggio") != null) {
//			System.out.println(log.getFileWithStatus("ER","").toString());
//		} else {
//			System.out.println("No file status like that");
//		}
	}



}
