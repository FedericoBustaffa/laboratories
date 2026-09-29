import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Pong {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(1500)) {
            byte[] buffer = new byte[4];
            DatagramPacket ping = new DatagramPacket(buffer, buffer.length);
            socket.receive(ping);
            System.out.println(ping.getPort());
            String msg = new String(ping.getData(), 0, ping.getLength());
            System.out.println(msg);

            InetAddress local = InetAddress.getLocalHost();
            buffer = "PONG".getBytes();
            DatagramPacket pong = new DatagramPacket(buffer, msg.length(), local, ping.getPort());
            socket.send(pong);
            System.out.println(pong.getPort());
            System.out.println(socket.getLocalPort());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
