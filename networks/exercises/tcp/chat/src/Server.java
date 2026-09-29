import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    private Scanner scanner;

    private ServerSocket server;
    private Socket socket;
    private DataInputStream is;
    private DataOutputStream os;

    private Thread receiver;
    private Thread sender;

    public Server() {
        try {
            scanner = new Scanner(System.in);

            System.out.printf("port: ");
            server = new ServerSocket(scanner.nextInt());
            socket = server.accept();
            is = new DataInputStream(socket.getInputStream());
            os = new DataOutputStream(socket.getOutputStream());

            receiver = new Thread(new Receiver(is));
            sender = new Thread(new Sender(os, scanner));
        } catch (BindException e) {
            System.out.println("Porta occupata");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void start() {
        receiver.start();
        sender.start();
    }

    public void shutdown() {
        try {
            receiver.join();
            sender.join();
            is.close();
            os.close();
            scanner.close();
            socket.close();
            server.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
        server.shutdown();
    }
}
