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
	public void prepareAndDownload(int configID, String username, String password, String host, String rDir,
			String lDir, int port, String pattern, String emails)
			throws IOException, AddressException, MessagingException {
		// This example requires the Chilkat API to have been previously unlocked.
		// See Global Unlock Sample for sample code.

		CkSsh ssh = new CkSsh();

		// Connect to an SSH server:
		boolean success = ssh.Connect(host, port);
		if (success != true) {
//			System.out.println(ssh.lastErrorText());
			WritingError.sendError("Wrong host or port. ChilkatDownload.java", emails);
			return;

		}

		// Authenticate using login/password:
		success = ssh.AuthenticatePw(username, password);
		if (success != true) {
//			System.out.println(ssh.lastErrorText());
			WritingError.sendError("Wrong username or password. ChilkatDownload.java", emails);
			return;
		}

		// Get all files from the remote
		List<String> list = getListFileName(rDir, ssh);

		// Find all files that equal user's pattern
		List<String> correspondingToPattern = checkPattern(list, pattern);
		System.out.println("correct: " + correspondingToPattern);

		// Start downloading
		download(configID, correspondingToPattern, ssh, rDir, lDir, emails);

	}

	private void download(int configID, List<String> correspondingToPattern, CkSsh ssh, String rDir, String lDir,
			String emails) throws AddressException, IOException, MessagingException {
		// Once the SSH object is connected and authenticated, use it
		// in the SCP object.
		CkScp scp = new CkScp();
		boolean success = scp.UseSsh(ssh);
		if (success != true) {
//					System.out.println(scp.lastErrorText());
			WritingError.sendError("Cannot create scp connection. ChilkatDownload.java", emails);
			return;
		}

		for (String filename : correspondingToPattern) {
			boolean ok = false;// check if send mail or not

			String remoteFile = rDir + "/" + filename;
			String localFile = lDir + "/" + filename;

			success = scp.DownloadFile(remoteFile, localFile);

			if (success != true) {
//					System.out.println(scp.lastErrorText());
				WritingError.sendError("Cannot download. ChilkatDownload.java " + filename, emails);
				return;
			} else
				ok = writingLog(configID, filename);

			// can't write log then send error
			if (!ok) {
				WritingError.sendError("Cannot write log. ChilkatDownload.java " + filename, emails);
			}
		}
		System.out.println("SCP download matching success.");

		// Disconnect
		ssh.Disconnect();

	}

	public List<String> getListFileName(String rDir, CkSsh ssh) {
		List<String> result = null;
		// open channel
		int channel = ssh.OpenSessionChannel();

		// command
		String cmd = "cd " + rDir + "; ls;";
		ssh.SendReqExec(channel, cmd);

		//Receive channel
		ssh.ChannelReceiveToClose(channel);
		
		// The ANSI character set includes the standard ASCII character set (values 0 to
		// 127), plus an extended character set (values 128 to 255).
		//Note: includes Latin charset
		
		//receive result
		String list = ssh.getReceivedText(channel, "ansi");

		//add files to result's list
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
