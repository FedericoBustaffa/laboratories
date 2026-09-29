import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.util.Date;

public class Server {
	public static void main(String[] args) {
		try {
			MulticastSocket ms = new MulticastSocket();
			SocketAddress group = new InetSocketAddress("239.255.255.1", 3000);
			byte[] buffer = new Date().toString().getBytes();
			DatagramPacket p = new DatagramPacket(buffer, buffer.length, group);
			for (int i = 0; i < 15; i++) {
				Thread.sleep(1000);
				ms.send(p);
			}
			ms.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
