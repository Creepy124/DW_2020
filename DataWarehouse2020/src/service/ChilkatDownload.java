package service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.chilkatsoft.*;

import model.Configuration;

/////////
public class ChilkatDownload {
	Configuration config;
	
	static {
		try {
			System.loadLibrary("chilkat");
		} catch (UnsatisfiedLinkError e) {
			System.err.println("Native code library failed to load.\n" + e);
			try {
				WritingError.sendError("Check chilkat library again. ChilkatDownload.java", "thuyphuongnguyen0170@gmail.com, creepy120499@gmail.com");
			} catch (IOException | MessagingException e1) {
				e1.printStackTrace();
			}
			System.exit(1);
		}
	}
	
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
		// Wait a max of 5 seconds when reading responses..
//		ssh.put_IdleTimeoutMs(5000);

		// Authenticate using login/password:
		success = ssh.AuthenticatePw(username, password);
		if (success != true) {
//			System.out.println(ssh.lastErrorText());
			WritingError.sendError("Wrong username or password. ChilkatDownload.java", emails);
			return;
		}

		// Get all files from the remote
		List<String> list = getListFileName(rDir, ssh);

		List<String> correspondingToPattern = checkPattern(list, pattern);
		System.out.println("correct: " + correspondingToPattern);
		
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

		scp.put_HeartbeatMs(200);

		// Set the SyncMustMatch property to "*.pem" to download only .pem files
//				scp.put_SyncMustMatch("sinhvien*.txt");
		for (String filename : correspondingToPattern) {
			boolean ok = false;//check if send mail or not
			
			String remoteDir = rDir + "/" + filename;
			String localDir = lDir + "/" + filename;
			
			success = scp.DownloadFile(remoteDir, localDir);
			
			if (success != true) {
//					System.out.println(scp.lastErrorText());
				WritingError.sendError("Cannot download. ChilkatDownload.java "+filename, emails);
				return;
			} else
				ok = writingLog(configID, filename);

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
		int channel = ssh.OpenSessionChannel();

		String cmd = "cd " + rDir + "; ls;";
		ssh.SendReqExec(channel, cmd);

		ssh.ChannelReceiveToClose(channel);
		String list = ssh.getReceivedText(channel, "ansi");

		String[] temp = list.split("\n");
		if (temp.length != 0)
			result = new LinkedList<String>(Arrays.asList(temp));

		System.out.println(result);
		return result;

	}

	private List<String> checkPattern(List<String> list, String pattern) {
		List<String> result = new LinkedList<String>();
		for (String str : list) {
			if (Pattern.matches(pattern, str))
				result.add(str);
		}
		return result;
	}

	private boolean writingLog(int configID, String filename) {
		boolean result = true;
		LogServiceImpl log = new LogServiceImpl("control", "root", "1234");
		try {
			log.insertLog(configID, filename, "ER", null, LocalDateTime.now().toString());
		} catch (SQLException e) {
			result = false;
		}
		return result;

	}

	public static void main(String[] args) throws IOException, AddressException, MessagingException {
		ChilkatDownload c = new ChilkatDownload();
		String username = "guest_access";
		String pass = "123456";
		String host = "drive.ecepvn.org";
		String rDir = "/volume1/ECEP/song.nguyen/DW_2020/data";
		c.prepareAndDownload(1, username, pass, host, rDir, "/DataWarehouse2020/local/", 2227,
				"sinhvien_(sang|chieu)_nhom([0-9]|[0-9][0-9]).*",
				"thuyphuongnguyen0170@gmail.com, creepy120499@gmail.com");

	}

}
