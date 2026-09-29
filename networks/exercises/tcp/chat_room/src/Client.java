import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    // user data
    private String nickname;

    // tcp
    private Socket socket;
    private DataInputStream is;
    private DataOutputStream os;

    // keyboard input
    Scanner scanner;

    public Client() {
        try {
            socket = new Socket(InetAddress.getLocalHost(), 1500);
            is = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            os = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            scanner = new Scanner(System.in);

            // nickname
            do {
                System.out.printf("nickname: ");
                nickname = scanner.nextLine();
                os.writeUTF(nickname);
                os.flush();
            } while (!is.readBoolean());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String cmd) {
        try {
            os.writeUTF(cmd);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receive() {
        try {
            return is.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void shell() {
        try {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            is.close();
            os.close();
            socket.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.shell();
        client.shutdown();
    }
}
