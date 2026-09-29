import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Random;

public class PingServer {

    private DatagramSocket socket;
    private DatagramPacket packet;

    public PingServer(int port) {
        try {
            socket = new DatagramSocket(1500);
            socket.setSoTimeout(10000);
            packet = new DatagramPacket(new byte[1], 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pong() {
        Random random = new Random();
        while (true) {
            try {
                socket.receive(packet);
                Thread.sleep((long) (random.nextInt(500)));
                packet.setAddress(InetAddress.getLocalHost());
                packet.setPort(packet.getPort());
                if (random.nextInt(100) >= 25) {
                    socket.send(packet);
                    System.out.println("sent");
                } else {
                    System.out.println("not sent");
                }
            } catch (SocketTimeoutException e) {
                return;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        socket.close();
    }

    public static void main(String[] args) {
        PingServer server = new PingServer(1500);
        server.pong();
        server.shutdown();
    }
}
