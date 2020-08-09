package service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.chilkatsoft.CkSFtp;
import com.chilkatsoft.CkScp;
import com.chilkatsoft.CkSsh;

import model.Configuration;

/////////
public class ChilkatDownloadSShHost {
	// unlock chilkat & load lib
	// Unlock Sample for sample code
	static {
		try {
			System.load("E:\\Warehouse\\chilkat\\chilkat\\chilkat.dll");
		} catch (UnsatisfiedLinkError e) {
			System.err.println("Native code library failed to load.\n" + e);
			WritingError.sendError("Check chilkat library again. ChilkatDownload.java",
					"thuyphuongnguyen0170@gmail.com, creepy120499@gmail.com");
			System.exit(1);
		}
	}

	/*
	 * Prepare: connect to server, get all file which matched with the input pattern
	 * from remote directory
	 */
	public boolean prepareAndDownload(int configID, String username, String password, String host, String rDir,
			String lDir, int port, String pattern, String emails)
			throws IOException, AddressException, MessagingException {
		// This example requires the Chilkat API to have been previously unlocked.
		// See Global Unlock Sample for sample code.

		CkSsh ssh = new CkSsh();

// 3.2.1 Kết nối đến server ssh
		// Connect to an SSH server:
		boolean success = ssh.Connect(host, port);
		if (success != true) {
//			System.out.println(ssh.lastErrorText());
			WritingError.sendError("Wrong host or port. ChilkatDownload.java", emails);
			return false;

		}

		// Authenticate using login/password:
		success = ssh.AuthenticatePw(username, password);
		if (success != true) {
//			System.out.println(ssh.lastErrorText());
			WritingError.sendError("Wrong username or password. ChilkatDownload.java", emails);
			return false;
		}

//4. Lấy ra các file có trong remote directory (3.2.2 Mở 1 ssh session chanel)
		// Get all files from the remote
		List<String> list = getListFileName(rDir, ssh);

//5 Lọc ra các file tương ứng với pattern có trong config 
		// Find all files that equal user's pattern
		List<String> correspondingToPattern = checkPattern(list, pattern);
		System.out.println("correct: " + correspondingToPattern);

//5.1 Có tồn tại file nào không?
		if (correspondingToPattern.size() == 0) {
			WritingError.sendError("Do not have any files that compatible to the pattern", emails);
			return false;
		}

//6. Tiến hành tải file tất cả các file tương ứng với pattern
		// Start downloading
		return download(configID, correspondingToPattern, ssh, rDir, lDir, emails);

	}

	private boolean download(int configID, List<String> correspondingToPattern, CkSsh ssh, String rDir, String lDir,
			String emails) throws AddressException, IOException, MessagingException {
		// Once the SSH object is connected and authenticated, use it
		// in the SCP object.
		CkScp scp = new CkScp();
		boolean success = scp.UseSsh(ssh);
		if (success != true) {
//					System.out.println(scp.lastErrorText());
			WritingError.sendError("Cannot create scp connection. ChilkatDownload.java", emails);
			return false;
		}

		for (String filename : correspondingToPattern) {
			boolean ok = false;// check if send mail or not

			String remoteFile = rDir + "/" + filename;
			String localFile = lDir + "/" + filename;

			success = scp.DownloadFile(remoteFile, localFile);
//Download fail
			if (success != true) {
//					System.out.println(scp.lastErrorText());
				WritingError.sendError("Cannot download. ChilkatDownload.java " + filename, emails);
				return false;
			} else
//7.1. Ghi thông tin file vừa tải vào table Log với trạng thái "ER"
				ok = writingLog(configID, filename);

//8. Gửi mail báo lỗi và ghi lỗi vào file error.txt
			// can't write log then send error
			if (!ok) {
				WritingError.sendError("Cannot write log. ChilkatDownload.java " + filename, emails);
				return false;
			}
		}
		System.out.println("SCP download matching success.");

		// Disconnect
		ssh.Disconnect();
		return true;

	}

	public List<String> getListFileName(String rDir, CkSsh ssh) {
		List<String> result = null;

//3.2.2 Mở 1 ssh session chanel
		// open channel
		int channel = ssh.OpenSessionChannel();

		// command
		String cmd = "cd " + rDir + "; ls;";
		boolean success = ssh.SendReqExec(channel, cmd);
		// Receive channel
		ssh.ChannelReceiveToClose(channel);

		// The ANSI character set includes the standard ASCII character set (values 0 to
		// 127), plus an extended character set (values 128 to 255).
		// Note: includes Latin charset

//4. Lấy ra các file có trong remote directory 
		// receive result
		String list = ssh.getReceivedText(channel, "ansi");

		// add files to result's list
		String[] temp = list.split("\n");
		if (temp.length != 0)
			result = new LinkedList<String>(Arrays.asList(temp));

		System.out.println(result);
		return result;

	}

	// is file's name matched to pattern
	private List<String> checkPattern(List<String> list, String pattern) {
		List<String> result = new LinkedList<String>();
		for (String str : list) {
			if (Pattern.matches(pattern, str))
				result.add(str);
		}
		return result;
	}

	//// Write log if download success
	private boolean writingLog(int configID, String filename) {
		boolean result = true;
		LogServiceImpl log = new LogServiceImpl();
		try {
			log.insertLog(configID, filename, "ER", null);
		} catch (SQLException e) {
			result = false;
		}
		return result;

	}

	public static void main(String[] args) throws IOException, AddressException, MessagingException {
//		ChilkatDownload c = new ChilkatDownload();
//		String username = "guest_access";
//		String pass = "123456";
//		String host = "drive.ecepvn.org";
//		String rDir = "/volume1/ECEP/song.nguyen/DW_2020/data";
//		c.prepareAndDownload(1, username, pass, host, rDir, "/DataWarehouse2020/local/", 2227,
//				"sinhvien_(sang|chieu)_nhom([0-9]|[0-9][0-9]).txt",
//				"thuyphuongnguyen0170@gmail.com");
		ChilkatDownloadSShHost c = new ChilkatDownloadSShHost();
		String username = "asus";
		String pass = "Langtutrunggio";
		String host = "DESKTOP-P7RFKBN";
		String rDir = "E:/Warehouse";
		c.prepareAndDownload(1, username, pass, host, rDir,
				"E:\\Warehouse\\DW_2020\\DataWarehouse2020\\local\\test\\New folder", 22, "sinhvien*.*",
				"thuyphuongnguyen0170@gmail.com");
	}

}
