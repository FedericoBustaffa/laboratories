import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	private Socket socket;
	private ObjectOutputStream os;
	private ObjectInputStream is;
	private boolean run;

	public Client() {
		try {
			socket = new Socket();
			InetAddress host = InetAddress.getLocalHost();
			int port = 1500;
			socket.connect(new InetSocketAddress(host, port));
			os = new ObjectOutputStream(socket.getOutputStream());
			is = new ObjectInputStream(socket.getInputStream());
			run = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isRunning() {
		return run;
	}

	private Message receive() {
		try {
			Message msg = (Message) is.readObject();
			if (msg.getText().equals("exit"))
				run = false;

			return msg;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	private void send(Message msg) {
		try {
			os.writeObject(msg);
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void shutdown() {
		try {
			socket.close();
			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Client client = new Client();
		Scanner scanner = new Scanner(System.in);
		System.out.printf("username: ");
		String username = scanner.nextLine();
		String text;
		Message msg = new Message();
		msg.setUsername(username);
		while (client.isRunning()) {
			System.out.printf("> text: ");
			text = scanner.nextLine();
			msg.setText(text);
			client.send(msg);
			msg = client.receive();
			System.out.println("< username: " + msg.getUsername());
			System.out.println("< text: " + msg.getText());
		}
		scanner.close();
		client.shutdown();
	}
}
