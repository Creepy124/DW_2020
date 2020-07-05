package control;


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
	   user=config.getSourceUsername();
       host=config.getSourceHost();
       rfile="/"+config.getSourceRemoteFile();
       lfile=config.getDownloadPath();
       password = config.getSourcePassword();
       port  = config.getSourcePort();
	}
	
	public void DownloadFile() {
		Downloading d = new Downloading();
		d.downloading( this.user,  this.password,  this.host,  this.rfile,  this.lfile,  this.port);;
	}
	
	public void writingLog() {
		
	}
}
