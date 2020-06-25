package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DBConnection;

public class LogService {
	
	public boolean insertLog(int configID, String fileName, String fileType, String status, String fileTimeStamp) {
		Connection connection;
		try {
			connection = DBConnection.getConnection("control");
			PreparedStatement ps1 = connection.prepareStatement("UPDATE log SET active=0 WHERE file_name=?");
			ps1.setString(1, fileName);
			ps1.executeUpdate();
			PreparedStatement ps = connection.prepareStatement("INSERT INTO log (config_id, file_name, file_type, status, file_timestamp, active) value (?,?,?,?,?,1)");
			ps.setInt(1, configID);
			ps.setString(2, fileName);
			ps.setString(3, fileName.substring(fileName.indexOf('.')+1));
			ps.setString(4, status);
			ps.setString(5, fileTimeStamp);
			ps.executeUpdate();
			connection.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	

}
