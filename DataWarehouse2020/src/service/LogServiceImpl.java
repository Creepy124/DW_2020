package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import db.DBConnection;
import model.MyFile;
/*
 * This class contain all methods that relative to LOG such as: get file with ER action, insert row, update action
 */
public class LogServiceImpl implements LogService {

	Connection connection;

	public LogServiceImpl() {
		connection = DBConnection.getConnection("control", "root", "1234");
	}
	
	@Override
	public MyFile getFileWithAction(int configID, String action) throws SQLException {
		PreparedStatement ps = connection
				.prepareStatement("SELECT file_name, file_type FROM log WHERE config_id=? and action=?");
		ps.setInt(1, configID);
		ps.setString(2, action);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			System.out.println(rs.getString("file_name"));
			return new MyFile(rs.getString("file_name"), rs.getString("file_type"));
		} else {
			return null;
		}
		
	}

	@Override
	public int insertLog(int configID, String fileName, String action, String status)
			throws SQLException {
		PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO log (config_id, file_name, file_type, action, status, file_timestamp) value (?,?,?,?,?,?)");
		ps.setInt(1, configID);
		ps.setString(2, fileName);
		ps.setString(3, fileName.substring(fileName.indexOf('.') + 1));
		ps.setString(4, action);
		ps.setString(5, status);
		ps.setString(6, LocalDateTime.now().toString());
		return ps.executeUpdate();
	}

	@Override
	public int updateAction(String fileName, String newAction) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("UPDATE log SET action=? where file_name=? and action<>'ERR'");
		ps.setString(1, newAction);
		ps.setString(2, fileName);
		return ps.executeUpdate();
	}
	@Override
	public int updateAction(int configID, String newAction) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("UPDATE log SET action=? where config_id=? and action<>'ERR'");
		ps.setString(1, newAction);
		ps.setInt(2, configID);
		return ps.executeUpdate();
	}
	public static void main(String[] args) throws SQLException {
		LogService log = new LogServiceImpl();
//		if (log.getFileWithStatus(2,"ER") != null) {
//			System.out.println(log.getFileWithStatus(2,"ER").toString());
//		} else {
//			System.out.println("No file status like that");
//		}
		log.insertLog(1, "sinhvien_chieu_nhom4.txt", "ER", null);
//		log.updateStatus(1, "", "TR");
		
	}

}
