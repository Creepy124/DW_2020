package service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.chilkatsoft.*;

public class ChilkatDownloadLocalHost {
	//unlock chilkat & load libraby
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
	 * Prepare: connect to server, get all file which matched with the input pattern from remote directory
	 */
	public void prepareAndDownload(int configID, String username, String password, String host, String rDir,
			String lDir, int port, String pattern, String emails)
			throws IOException, AddressException, MessagingException {
		// This example requires the Chilkat API to have been previously unlocked.
		// See Global Unlock Sample for sample code.
		CkSFtp sftp = new CkSFtp();

		// Connect to an SSH server:
		boolean success = sftp.Connect(host, port);
		if (success != true) {
//			System.out.println(ssh.lastErrorText());
			WritingError.sendError("Wrong host or port. ChilkatDownloadLocalHost.java", emails);
			return;
		}

		// Authenticate using login/password:
		success = sftp.AuthenticatePw(username, password);
		if (success != true) {
//			System.out.println(ssh.lastErrorText());
			WritingError.sendError("Wrong username or password. ChilkatDownloadLocalHost.java", emails);
			return;

		}
		// Initial channel
		success = sftp.InitializeSftp();
		if (success != true) {
//			System.out.println(sftp.lastErrorText());
			WritingError.sendError("Can't initial sftl channel. ChilkatDownloadLocalHost.java", emails);
			return;

		}
		// Get all files from the remote
		List<String> list = getListFileName(rDir, sftp);

		// Find all files that equal user's pattern
		List<String> correspondingToPattern = checkPattern(list, pattern);
		System.out.println("correct: " + correspondingToPattern);

		// Start downloading
		download(configID, correspondingToPattern, sftp, rDir, lDir, emails);

	}

	private void download(int configID, List<String> correspondingToPattern, CkSFtp sftp, String rDir, String lDir,
			String emails) throws AddressException, IOException, MessagingException {
		
		//Iteration
		for (String filename : correspondingToPattern) {
			boolean ok = false;// check if send mail or not

			String remoteFile = rDir + "/" + filename;
			String localFile = lDir + "/" + filename;
			
			//Download
			boolean success = sftp.DownloadFile(remoteFile, localFile);

			if (success != true) {
//					System.out.println(scp.lastErrorText());
				WritingError.sendError("Cannot download. ChilkatDownload.java " + filename, emails);
				return;
			} else
				ok = writingLog(configID, filename);
			
			//cant write log then send error
			if (!ok) {
				WritingError.sendError("Cannot write log. ChilkatDownload.java " + filename, emails);
			}
		}
		System.out.println("SCP download matching success.");

		// Disconnect
		sftp.Disconnect();

	}

	public List<String> getListFileName(String rDir, CkSFtp sftp) {
		
		List<String> result = new LinkedList<String>();

		// cd remote directory
		String handle = sftp.openDir(rDir);
		// get all things inside directory
		CkSFtpDir insideDir = sftp.ReadDir(handle);

		int i = 0;
		String tmp = "";

		// Get file name, if isFile then add to result
		while (true) {

			tmp = insideDir.getFilename(i);

			if (tmp == null) {
				break;
			}
			//is file or not
			if (new File(rDir + "\\" + tmp).isFile()) {
				result.add(tmp);
			}

			i++;
		}

		System.out.print("List File: " + result + "\t");
		System.out.println();
		return result;

	}
	
	//is file's name matched to pattern
	private List<String> checkPattern(List<String> list, String pattern) {

		List<String> result = new LinkedList<String>();
		for (String str : list) {
			if (Pattern.matches(pattern, str))
				result.add(str);
		}
		return result;
	}

	//Write log if download success
	private boolean writingLog(int configID, String filename) {
		boolean result = true;
		//Class exec everything relative to log
		LogServiceImpl log = new LogServiceImpl();
		
		try {
			log.insertLog(configID, filename, "ER", null);
		} catch (SQLException e) {
			result = false;
		}
		
		return result;

	}

	public static void main(String argv[]) throws AddressException, IOException, MessagingException {
		ChilkatDownloadLocalHost local = new ChilkatDownloadLocalHost();
		String username = "asus";
		String pass = "Langtutrunggio";
		String host = "DESKTOP-P7RFKBN";
		String rDir = "E:/Warehouse2";
		String lDir = "E:\\Warehouse";
		local.prepareAndDownload(1, username, pass, host, rDir, lDir, 22,
				"sinhvien_(sang|chieu)_nhom([0-9]|[0-9][0-9]).txt", "thuyphuongnguyen0170@gmail.com");
	}
}