package control;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

import com.chilkatsoft.*;

public class ChilkatDownload {
	static {
		try {
			System.loadLibrary("chilkat");
		} catch (UnsatisfiedLinkError e) {
			System.err.println("Native code library failed to load.\n" + e);
			System.exit(1);
		}
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

	public void prepareAndDownload(String username, String password, String host, String rDir, String lDir, int port,
			String pattern) throws IOException {
		// This example requires the Chilkat API to have been previously unlocked.
		// See Global Unlock Sample for sample code.
		CkSsh ssh = new CkSsh();
		// Connect to an SSH server:
		boolean success = ssh.Connect(host, port);
		if (success != true) {
//			System.out.println(ssh.lastErrorText());
			System.out.println("host");
			return;
		}
		// Wait a max of 5 seconds when reading responses..
//		ssh.put_IdleTimeoutMs(5000);

		// Authenticate using login/password:
		success = ssh.AuthenticatePw(username, password);
		if (success != true) {
//			System.out.println(ssh.lastErrorText());
			System.out.println("pass");
			return;
		}

		// Get all files from the remote
		List<String> list = getListFileName(rDir, ssh);

		List<String> correspondingToPattern = checkPattern(list, pattern);
		System.out.println("correct: " + correspondingToPattern);
		download(correspondingToPattern, ssh, rDir, lDir);

	}

	private void download(List<String> correspondingToPattern, CkSsh ssh, String rDir, String lDir) {
		// Once the SSH object is connected and authenticated, use it
		// in the SCP object.
		CkScp scp = new CkScp();
		boolean success = scp.UseSsh(ssh);
		if (success != true) {
//					System.out.println(scp.lastErrorText());
			System.out.println("line 50");
			return;
		}

		scp.put_HeartbeatMs(200);

		// Set the SyncMustMatch property to "*.pem" to download only .pem files
//				scp.put_SyncMustMatch("sinhvien*.txt");
		for (String str : correspondingToPattern) {
			String remoteDir =rDir+"/"+str;
			System.out.println(remoteDir);
			String localDir = lDir;
			success = scp.DownloadFile(remoteDir,localDir);

			if (success != true) {
//					System.out.println(scp.lastErrorText());
				System.out.println("nothing");
				return;
			}
		}
		System.out.println("SCP download matching success.");

		// Disconnect
		ssh.Disconnect();

	}

	public static void main(String[] args) throws IOException {
		ChilkatDownload c = new ChilkatDownload();
		String username = "guest_access";
		String pass = "123456";
		String host = "drive.ecepvn.org";
		String rDir = "/volume1/ECEP/song.nguyen/DW_2020/data";
		c.prepareAndDownload(username, pass, host, rDir, "E:/Warehouse", 2227,
				"sinhvien_(sang|chieu)_nhom([0-9]|[0-9][0-9]).txt");

	}
}
