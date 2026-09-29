import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	private ServerSocket server;
	private Socket socket;
	private DataInputStream reader;
	private DataOutputStream writer;

	public EchoServer(int port) {
		try {
			server = new ServerSocket(port);
			System.out.println("Server waiting for connections");
			socket = server.accept();
			System.out.println("Receiving port " + socket.getLocalPort());
			System.out.println("Sending port " + socket.getPort());
			reader = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			writer = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		} catch (BindException e) {
			System.out.println("port " + " not available");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(String msg) {
		try {
			writer.writeUTF(msg);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String receive() {
		try {
			String msg = reader.readUTF();
			return msg;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void shutdown() {
		try {
			reader.close();
			writer.close();
			socket.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		EchoServer echo_server = new EchoServer(3000);
		String msg;

		for (int i = 0; i < 10; i++) {
			msg = echo_server.receive();
			System.out.println(msg);
			echo_server.send(msg);
		}

		do {
			msg = echo_server.receive();
			System.out.println("> " + msg);
			echo_server.send(msg);
		} while (!msg.equals("exit"));
		echo_server.shutdown();
		System.out.println("Server shutted down");
	}
}
