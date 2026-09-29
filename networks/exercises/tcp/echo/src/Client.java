import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private DataInputStream reader;
    private DataOutputStream writer;

    public Client(int port) {
        try {
            socket = new Socket(InetAddress.getLocalHost(), port);
            System.out.println("Receiving port " + socket.getLocalPort());
            System.out.println("Sending port " + socket.getPort());
            reader = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            writer = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String msg) {
        try {
            writer.writeUTF(msg);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receive() {
        try {
            return reader.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void shutdown() {
        try {
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client(3000);
        String msg;

        for (int i = 0; i < 10; i++) {
            client.send("TEST " + i);
            System.out.println("< " + client.receive());
        }

        Scanner scanner = new Scanner(System.in);
        do {
            System.out.printf("> ");
            client.send(scanner.nextLine());
            msg = client.receive();
            System.out.println("< " + msg);
        } while (!msg.equals("exit"));
        scanner.close();
        client.shutdown();
    }
}
