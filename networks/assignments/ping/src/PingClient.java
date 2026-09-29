import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class PingClient {

	DatagramSocket socket;
	InetAddress host;
	DatagramPacket packet;
	int lost;

	public PingClient(int port) {
		try {
			socket = new DatagramSocket();
			socket.setSoTimeout(1000);
			host = InetAddress.getLocalHost();
			packet = new DatagramPacket(new byte[1], 0, 1, host, port);
			lost = 0;
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void ping() {
		long start, end;
		long sum = 0;
		long min = Long.MAX_VALUE;
		long max = 0;
		long time;
		for (int i = 0; i < 10; i++) {
			try {
				start = System.currentTimeMillis();
				socket.send(packet);
				System.out.printf("packet %d RTT: ", i);
				socket.receive(packet);
				end = System.currentTimeMillis();

				// statistics
				time = end - start;
				System.out.println(time + " ms");
				sum += time;
				if (min > time) {
					min = time;
				} else if (max < time) {
					max = time;
				}
			} catch (SocketTimeoutException e) {
				System.out.println("packet " + i + " lost");
				lost++;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("- - - statistics - - -");
		System.out.println("lost: " + lost + " packets");
		System.out.println("min RTT: " + min + " ms");
		System.out.println("max RTT: " + max + " ms");
		double avg = (double) sum / (10 - lost);
		System.out.printf("avg RTT: %g ms\n", avg);
	}

	public void shutdown() {
		socket.close();
	}

	public static void main(String[] args) {
		PingClient client = new PingClient(1500);
		client.ping();
		client.shutdown();
	}

}
