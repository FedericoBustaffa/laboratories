import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;

public class PortScanner {
    public static void main(String[] args) throws IOException {
        ServerSocket server_socket;
        for (int i = 1; i <= 3000; i++) {
            try {
                server_socket = new ServerSocket(i);
                System.out.println("Porta " + i + " libera");
                server_socket.close();
            } catch (BindException e) {
                System.out.println("Porta " + i + " occupata");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
