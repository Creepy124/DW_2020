package control;
import com.chilkatsoft.*;

public class ChilkatExample {

  static {
    try {
        System.loadLibrary("chilkat");
    } catch (UnsatisfiedLinkError e) {
      System.err.println("Native code library failed to load.\n" + e);
      System.exit(1);
    }
  }

  public static void main(String argv[])
  {
    // This example requires the Chilkat API to have been previously unlocked.
    // See Global Unlock Sample for sample code.

    CkSsh ssh = new CkSsh();

    // Hostname may be an IP address or hostname:
    String hostname = "drive.ecepvn.org";
    int port = 2227;

    // Connect to an SSH server:
    boolean success = ssh.Connect(hostname,port);
    if (success != true) {
        System.out.println(ssh.lastErrorText());
        return;
        }

    // Wait a max of 5 seconds when reading responses..
    ssh.put_IdleTimeoutMs(5000);

    // Authenticate using login/password:
    success = ssh.AuthenticatePw("guest_access","123456");
    if (success != true) {
        System.out.println(ssh.lastErrorText());
        return;
        }

    // Once the SSH object is connected and authenticated, we use it
    // in our SCP object.
    CkScp scp = new CkScp();

    success = scp.UseSsh(ssh);
    if (success != true) {
        System.out.println(scp.lastErrorText());
        return;
        }

    String remotePath = "sinhvien_chieu_nhom4.txt";
    String localPath = "E:/Warehouse";
    success = scp.DownloadFile(remotePath,localPath);
    if (success != true) {
        System.out.println("Asd");
        return;
        }

    System.out.println("SCP download file success.");

    // Disconnect
    ssh.Disconnect();
  }
}