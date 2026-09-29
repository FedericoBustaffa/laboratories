import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
	public static void main(String[] args) {
		try (DatagramSocket socket = new DatagramSocket(1500)) {
			DatagramPacket packet = new DatagramPacket(new byte[128], 128);
			String msg;
			socket.receive(packet);
			msg = new String(packet.getData(), 0, packet.getLength());
			System.out.println(msg);
			socket.receive(packet);
			msg = new String(packet.getData(), 0, packet.getLength());
			System.out.println(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
