package control;


import java.sql.SQLException;
import java.time.LocalTime;

import model.Configuration;
import service.LogService;
import service.LogServiceImpl;

public class RepairDownload {
//	String[] arg = {"guest_access@drive.ecepvn.org:/volume1/ECEP/song.nguyen/DW_2020/data/17130044_sang_nhom8.txt", "E:/Warehouse"};
	Configuration config = new Configuration("sinhvien", "langtutrunggio");
	private String user;
	private String password;
	private String host;
	private String rfile;
	private String lfile;
	private int port;
	
	public RepairDownload() {
	   user=config.getSourceUsername();
       host=config.getSourceHost();
       rfile="/"+config.getSourceRemoteFile()+config.getFileName();
       lfile=config.getDownloadPath();
       password = config.getSourcePassword();
       port  = config.getSourcePort();
	}
	
	public void DownloadFile() {
		Downloading d = new Downloading();
		d.downloading( this.user,  this.password,  this.host,  this.rfile,  this.lfile,  this.port);
		System.out.println(this.toString());
		writingLog();
	}
	
	public boolean writingLog() {
		boolean  result = true;
		String fileName = config.getFileName();
		System.out.println(fileName);
		LogServiceImpl log = new  LogServiceImpl();
		try {
			log.insertLog(fileName, fileName.substring(fileName.indexOf('.') + 1) , "ER", LocalTime.now().toString(), "langtutrunggio");
		} catch (SQLException e) {
		result = false;
		}
		return result;
	}
	
	@Override
	public String toString() {
		return "RepairDownload [config=" + config + ", user=" + user + ", password=" + password + ", host=" + host
				+ ", rfile=" + rfile + ", lfile=" + lfile + ", port=" + port + "]";
	}

	public static void main(String[] args) {
		RepairDownload rp = new RepairDownload();
		rp.DownloadFile();
//		rp.toString();
//		rp.writingLog();
	}
}
