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
	private int sourcePort;
	private String fileName = "";
	private String fileColumnList = "";
	private String fileVariables = "";
	private String downloadPath = "";
	
	public Configuration(String configName, String userName, String password) {
		Connection connection;
		try {
			connection = DBConnection.getConnection("control", userName, password);
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM configuration WHERE config_name=?");
			ps.setString(1, configName);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				this.configID = rs.getInt("config_id");
				this.configName = configName;
				this.sourceHost = rs.getString("source_host");
				this.sourceRemoteFile = rs.getString("source_remote_path");
				this.sourceUsername = rs.getString("source_username");
				this.sourcePassword = rs.getString("source_password");
				this.sourcePort = rs.getInt("source_port");
				this.fileName = rs.getString("file_name");
				this.fileColumnList = rs.getString("file_column_list");
				this.fileVariables = rs.getString("file_variables");
				this.downloadPath = rs.getString("download_path");
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

	public String getFileVariables() {
		return fileVariables;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public int getSourcePort() {
		return sourcePort;
	}

	public void setSourcePort(int sourcePort) {
		this.sourcePort = sourcePort;
	}

	@Override
	public String toString() {
		return "Configuration [configID=" + configID + ", configName=" + configName + ", sourceHost=" + sourceHost
				+ ", sourceRemoteFile=" + sourceRemoteFile + ", sourceUsername=" + sourceUsername + ", sourcePassword="
				+ sourcePassword + ", sourcePort=" + sourcePort + ", fileName=" + fileName + ", fileColumnList="
				+ fileColumnList + ", fileVariables=" + fileVariables + ", downloadPath=" + downloadPath + "]";
	}

	public static void main(String[] args) {
		Configuration configuration = new Configuration("sinhvien","","");
		System.out.println(configuration.toString());
	}

}
