import java.io.DataInputStream;
import java.io.IOException;

public class Receiver implements Runnable {

	private DataInputStream is;

	public Receiver(DataInputStream is) {
		this.is = is;
	}

	public void run() {
		String msg;
		try {
			while (!(msg = is.readUTF()).equals("exit")) {
				System.out.println(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
