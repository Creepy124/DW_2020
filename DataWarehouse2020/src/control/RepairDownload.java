package control;

import com.mysql.jdbc.Connection;

import db.DBConnection;
import model.Configuration;

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
	  String user=config.getSourceUsername();
      String host=config.getSourceHost();
      String rfile="/"+config.getSourceRemoteFile();
      String lfile=config.getDownloadPath();
      String pass = config.getSourcePassword();
      int port  = config.getSourcePort();
	}
	
	public void DownloadFile() {
		Downloading d = new Downloading();
		d.downloading( this.user,  this.password,  this.host,  this.rfile,  this.lfile,  this.port);;
	}
	
	public void writingLog() {
		
	}
}
