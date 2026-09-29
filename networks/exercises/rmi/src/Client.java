import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client {

    private String username;

    private Chat chat;
    private Notify notify;
    private Registry registry;

    private Scanner scanner;

    public Client() {
        try {
            registry = LocateRegistry.getRegistry(3000);
            chat = (Chat) registry.lookup(Chat.SERVICE_NAME);
            scanner = new Scanner(System.in);

            // registrazione utente
            do {
                System.out.printf("username: ");
                username = scanner.nextLine();
            } while (!chat.register(username));

            // servizio di notifica
            notify = new NotifyService(username);
            chat.registerForNotification(notify);
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            String cmd;
            while (true) {
                System.out.printf("> ");
                cmd = scanner.nextLine();
                if (cmd.equals("exit"))
                    return;
                else
                    chat.send(username, cmd);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            scanner.close();
            chat.unregisterForNotification(notify);
            chat.unregister(username);
            UnicastRemoteObject.unexportObject(notify, false);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
        client.shutdown();
    }
}
