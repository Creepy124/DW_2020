package control;


import java.sql.SQLException;
import java.time.LocalTime;

import model.Configuration;
import service.LogService;
import service.LogServiceImpl;

public class RepairDownload {
	String[] arg = {"guest_access@drive.ecepvn.org:/volume1/ECEP/song.nguyen/DW_2020/data/17130044_sang_nhom8.txt", "E:/Warehouse"};
	Configuration config = new Configuration("configuration", "");
	private String user;
	private String password;
	private String host;
	private String rfile;
	private String lfile;
	private int port;
	
	public RepairDownload() {
	   user=config.getSourceUsername();
       host=config.getSourceHost();
       rfile="/"+config.getSourceRemoteFile();
       lfile=config.getDownloadPath();
       password = config.getSourcePassword();
       port  = config.getSourcePort();
	}
	
	public void DownloadFile() {
		Downloading d = new Downloading();
		d.downloading( this.user,  this.password,  this.host,  this.rfile,  this.lfile,  this.port);
		writingLog();
	}
	
	public boolean writingLog() {
		boolean  result = true;
		String[] part = rfile.split("/");
		String fileName = part[part.length-1];
		LogServiceImpl log = new  LogServiceImpl();
		try {
			log.insertLog(fileName, fileName.substring(fileName.indexOf('.') + 1) , "ER", LocalTime.now().toString(), password);
		} catch (SQLException e) {
		result = false;
		}
		return result;
	}
}
