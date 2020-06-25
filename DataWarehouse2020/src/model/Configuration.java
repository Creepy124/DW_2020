package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBConnection;

public class Configuration {
	private int configID = 1;
	private String configName = "";
	private String sourceHost = "";
	private String sourceRemoteFile = "";
	private String sourceUsername = "";
	private String sourcePassword = "";
	private String fileName = "";
	private String fileColumnList = "";
	private String variabless = "";
	private String downloadPath = "";
	private String successPath = "";
	private String errorPath = "";
	
	public Configuration(String configName) {
		Connection connection;
		try {
			connection = DBConnection.getConnection("control");
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM configuration WHERE config_name=?");
			ps.setString(1, configName);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				this.configID = rs.getInt("");
				this.configName = configName;
				this.sourceHost = rs.getString("");
				this.sourceRemoteFile = rs.getString("");
				this.sourceUsername = rs.getString("");
				this.sourcePassword = rs.getString("");
				this.fileName = rs.getString("");
				this.fileColumnList = rs.getString("");
				this.variabless = rs.getString("");
				this.downloadPath = rs.getString("");
				this.successPath = rs.getString("");
				this.errorPath = rs.getString("");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getConfigID() {
		return configID;
	}

	public String getConfigName() {
		return configName;
	}

	public String getSourceHost() {
		return sourceHost;
	}

	public String getSourceRemoteFile() {
		return sourceRemoteFile;
	}

	public String getSourceUsername() {
		return sourceUsername;
	}

	public String getSourcePassword() {
		return sourcePassword;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileColumnList() {
		return fileColumnList;
	}

	public String getVariabless() {
		return variabless;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public String getSuccessPath() {
		return successPath;
	}

	public String getErrorPath() {
		return errorPath;
	}
	
	

}
