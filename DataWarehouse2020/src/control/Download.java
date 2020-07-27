package control;

import java.io.IOException;
import java.util.Scanner;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import model.Configuration;
import service.ChilkatDownload;
import service.DBService;
import service.DBServiceImpl;
import service.FileService;
import service.FileServiceImpl;
import service.LogService;
import service.LogServiceImpl;

public class Download {
//	String[] arg = {"guest_access@drive.ecepvn.org:/volume1/ECEP/song.nguyen/DW_2020/data/17130044_sang_nhom8.txt", "E:/Warehouse"};
	Configuration config;
	private String username;
	private String password;
	private String host;
	private String rDir;
	private String lDir;
	private int port;
	private String pattern;
	private String emails;
	
	public Download(Configuration config) {
		this.config = config;
		repareDownload();
	}

	private void repareDownload() {
		username = config.getSourceUsername();
		host = config.getSourceHost();
		rDir = "/" + config.getSourceRemoteFile() ;
		lDir = config.getDownloadPath();
		password = config.getSourcePassword();
		port = config.getSourcePort();
		pattern = config.getFileNamePattern();
		emails = config.getToEmails();
	}

	public void DownloadFile() throws AddressException, IOException, MessagingException {
		ChilkatDownload d = new ChilkatDownload();
		d.prepareAndDownload(config.getConfigID(), username, password, host, rDir, lDir, port, pattern, emails);
		System.out.println(this.toString());
	}

	@Override
	public String toString() {
		return "RepairDownload [config=" + config + ", user=" + username + ", password=" + password + ", host=" + host
				+ ", rfile=" + rDir + ", lfile=" + lDir + ", port=" + port + "]";
	}

	public static void main(String[] args) throws AddressException, IOException, MessagingException {
		Scanner sc = new Scanner(System.in);
		Configuration configuration;
		FileService fileService = new FileServiceImpl();
		DBService dbService = new DBServiceImpl("staging", "root", "1234");
		LogService logService = new LogServiceImpl("control", "root", "1234");
		while (true) {
			System.out.print("Ten config: ");
		String cm = sc.nextLine();
		if(cm.equals("end"))
			break;
//		System.out.println(cm);
		
		try {
		configuration = new Configuration(cm, "root", "1234");
		Download rp = new Download(configuration);
		rp.DownloadFile();
		ExtractToStaging ex = new ExtractToStaging(configuration, fileService, dbService, logService);
		ex.extractToStaging();
		}catch(Exception e) {
			System.out.println("Wrong config name");
			continue;
		}
		
		}
	}
}
