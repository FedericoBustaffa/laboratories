import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class HelloClient {
    public static void main(String[] args) {
        try {
            MulticastSocket ms = new MulticastSocket(3000);
            InetAddress mc_address = InetAddress.getByName("239.255.255.1");
            ms.joinGroup(mc_address);

            DatagramPacket p = new DatagramPacket(new byte[1024], 1024);
            ms.receive(p);
            String hello = new String(p.getData(), p.getOffset(), p.getLength());
            System.out.println(hello);

            ms.leaveGroup(mc_address);
            ms.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
