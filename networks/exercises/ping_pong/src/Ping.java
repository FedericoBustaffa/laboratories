import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Ping {
	public static void main(String[] args) {
		try (DatagramSocket socket = new DatagramSocket()) {
			byte[] msg = "PING".getBytes();
			InetAddress local = InetAddress.getLocalHost();
			DatagramPacket ping = new DatagramPacket(msg, msg.length, local, 1500);
			System.out.println("Socket port: " + socket.getLocalPort());
			System.out.println("Send ping to port: " + ping.getPort());
			socket.send(ping);

			DatagramPacket pong = new DatagramPacket(new byte[4], 4);
			socket.receive(pong);
			String response = new String(pong.getData(), 0, pong.getLength());
			System.out.println(response);
			System.out.println(pong.getPort());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
