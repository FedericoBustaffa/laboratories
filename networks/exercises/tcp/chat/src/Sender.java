import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Sender implements Runnable {

	private DataOutputStream os;
	private Scanner scanner;

	public Sender(DataOutputStream os, Scanner scanner) {
		this.os = os;
		this.scanner = scanner;
	}

	public void run() {
		String msg;
		try {
			do {
				msg = scanner.nextLine();
				os.writeUTF(msg);
				os.flush();
			} while (!msg.equals("exit"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
