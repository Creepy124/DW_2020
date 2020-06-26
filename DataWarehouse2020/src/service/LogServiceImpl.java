package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBConnection;
import model.MyFile;

public class LogServiceImpl implements LogService {

	public LogServiceImpl() {
	}

	@Override
	public boolean insertLog(int configID, String fileName, String fileType, String status, String fileTimeStamp)
			throws SQLException {
		Connection connection;
		connection = DBConnection.getConnection("control");
		PreparedStatement ps1 = connection.prepareStatement("UPDATE log SET active=0 WHERE file_name=?");
		ps1.setString(1, fileName);
		ps1.executeUpdate();
		PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO log (config_id, file_name, file_type, status, file_timestamp, active) value (?,?,?,?,?,1)");
		ps.setInt(1, configID);
		ps.setString(2, fileName);
		ps.setString(3, fileName.substring(fileName.indexOf('.') + 1));
		ps.setString(4, status);
		ps.setString(5, fileTimeStamp);
		ps.executeUpdate();
		connection.close();
		return true;
	}

	@Override
	public MyFile getFileWithStatus(String status) throws SQLException {
		MyFile file = new MyFile();
		Connection connection;
		connection = DBConnection.getConnection("control");
		PreparedStatement ps = connection
				.prepareStatement("SELECT file_name, file_type FROM log WHERE status=? AND active=1");
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
		if (log.getFileWithStatus("ER") != null) {
			System.out.println(log.getFileWithStatus("ER").toString());
		} else {
			System.out.println("No file status like that");
		}
	}

}
