import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	private Socket socket;
	private DataInputStream is;
	private DataOutputStream os;
	private Scanner scanner;
	private Thread receiver;
	private Thread sender;

	public Client() {
		try {
			scanner = new Scanner(System.in);
			System.out.printf("server ip: ");
			InetAddress address = InetAddress.getByName(scanner.nextLine());
			System.out.printf("port: ");
			socket = new Socket(address, scanner.nextInt());
			is = new DataInputStream(socket.getInputStream());
			os = new DataOutputStream(socket.getOutputStream());

			receiver = new Thread(new Receiver(is));
			sender = new Thread(new Sender(os, scanner));
		} catch (UnknownHostException e) {
			System.out.println("Host sconosciuto");
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		receiver.start();
		sender.start();
	}

	public void shutdown() {
		try {
			receiver.join();
			sender.join();
			is.close();
			os.close();
			scanner.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Client client = new Client();
		client.run();
		client.shutdown();
	}
}
