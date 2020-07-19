package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class Downloading {
	public void downloading(String user, String password, String host, String rfile, String lfile, int port) throws IOException {
//	    if(arg.length!=2){
//	      System.err.println("usage: java ScpFrom user@remotehost:file1 file2");
//	      System.exit(-1);
//	    }      

		FileOutputStream fos = null;
//
		try {
			String prefix = null;
			if (new File(lfile).isDirectory()) {
				prefix = lfile + File.separator;
			}

			JSch jsch = new JSch();
			JSch.setConfig("StrictHostKeyChecking", "no");
			Session session = jsch.getSession(user, host, port);

			session.setPassword(password);
			session.connect();

			// exec 'scp -f rfile' remotely
			rfile = rfile.replace("'", "'\"'\"'");
			rfile = "'" + rfile + "'";
			String command = "scp -f " + rfile;
			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);

			// get I/O streams for remote scp
			OutputStream out = channel.getOutputStream();
			InputStream in = channel.getInputStream();

			channel.connect();

			byte[] buf = new byte[1024];

			// send '\0'
			buf[0] = 0;
			out.write(buf, 0, 1);
			out.flush();

			while (true) {
				int c = checkAck(in);
				if (c != 'C') {
					break;
				}

				// read '0644 '
				in.read(buf, 0, 5);

				long filesize = 0L;
				while (true) {
					if (in.read(buf, 0, 1) < 0) {
						// error
						break;
					}
					if (buf[0] == ' ')
						break;
					filesize = filesize * 10L + (long) (buf[0] - '0');
				}

				String file = null;
				for (int i = 0;; i++) {
					in.read(buf, i, 1);
					if (buf[i] == (byte) 0x0a) {
						file = new String(buf, 0, i);
						break;
					}
				}

				// System.out.println("filesize="+filesize+", file="+file);

				// send '\0'
				buf[0] = 0;
				out.write(buf, 0, 1);
				out.flush();

				// read a content of lfile
				fos = new FileOutputStream(prefix == null ? lfile : prefix + file);
				int foo;
				while (true) {
					if (buf.length < filesize)
						foo = buf.length;
					else
						foo = (int) filesize;
					foo = in.read(buf, 0, foo);
					if (foo < 0) {
						// error
						break;
					}
					fos.write(buf, 0, foo);
					filesize -= foo;
					if (filesize == 0L)
						break;
				}
				fos.close();
				fos = null;

				if (checkAck(in) != 0) {
					return;
//					System.exit(0);
				}

				// send '\0'
				buf[0] = 0;
				out.write(buf, 0, 1);
				out.flush();
			}

			session.disconnect();
			return;
//		      System.exit(0);
		} catch (Exception e) {
			WritingError.writingError("Check User Again");
			System.out.println(e);
			try {
				if (fos != null)
					fos.close();
			} catch (Exception ee) {
			}
		}
	}

	static int checkAck(InputStream in) throws IOException {
		int b = in.read();
		// b may be 0 for success,
		// 1 for error,
		// 2 for fatal error,
		// -1
		if (b == 0)
			return b;
		if (b == -1)
			return b;

		if (b == 1 || b == 2) {
			StringBuffer sb = new StringBuffer();
			int c;
			do {
				c = in.read();
				sb.append((char) c);
			} while (c != '\n');
			if (b == 1) { // error
				System.out.print(sb.toString());
			}
			if (b == 2) { // fatal error
				System.out.print(sb.toString());
			}
		}
		return b;
	}

	public static void main(String[] args) {
		Downloading s = new Downloading();
		String[] arg = { "guest_access@drive.ecepvn.org:/volume1/ECEP/song.nguyen/DW_2020/data/17130044_sang_nhom8.txt",
				"E:/Warehouse" };
//	s.downloading(arg);
	}
}