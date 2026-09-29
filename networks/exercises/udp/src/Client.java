import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress local = InetAddress.getLocalHost();
            byte[] msg = "0123456789abcdefghijklmnopqrstuvwxyz".getBytes();
            DatagramPacket packet = new DatagramPacket(msg, msg.length, local, 1500);
            socket.send(packet);
            msg = "ciao".getBytes();
            packet.setData(msg);
            packet.setLength(msg.length);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
