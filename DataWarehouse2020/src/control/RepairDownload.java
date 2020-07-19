package control;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import model.Configuration;
import service.Downloading;
import service.LogServiceImpl;
import service.WritingError;

public class RepairDownload {
//	String[] arg = {"guest_access@drive.ecepvn.org:/volume1/ECEP/song.nguyen/DW_2020/data/17130044_sang_nhom8.txt", "E:/Warehouse"};
	Configuration config;
	private String user;
	private String password;
	private String host;
	private String rfile;
	private String lfile;
	private int port;

	public RepairDownload(Configuration config) {
		user = config.getSourceUsername();
		host = config.getSourceHost();
		rfile = "/" + config.getSourceRemoteFile() + config.getFileName();
		lfile = config.getDownloadPath();
		password = config.getSourcePassword();
		port = config.getSourcePort();
	}

	public void DownloadFile() throws AddressException, IOException, MessagingException {
		Downloading d = new Downloading();
		d.downloading(this.user, this.password, this.host, this.rfile, this.lfile, this.port);
		System.out.println(this.toString());
		boolean wrote = writingLog();
		
		if(!wrote) {
			WritingError.sendError("Cant write into Log");
		}
	}

	public boolean writingLog() {
		boolean result = true;
		String fileName = config.getFileName();
		System.out.println(fileName);
		LogServiceImpl log = new LogServiceImpl("control","root","");
		try {
			log.insertLog(1, fileName, "ER",null, LocalDateTime.now().toString());
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

	public static void main(String[] args) throws AddressException, IOException, MessagingException {
		Configuration configuration = new Configuration("sinhvien", "root", "");
		RepairDownload rp = new RepairDownload(configuration);
		rp.DownloadFile();
//		rp.toString();
//		rp.writingLog();
	}
}
