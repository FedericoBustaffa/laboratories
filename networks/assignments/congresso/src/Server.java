import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Server {

    Congress congress;
    Registry registry;

    public Server(int port) {
        try {
            congress = new CongressService();
            registry = LocateRegistry.createRegistry(port);
            registry.rebind(Congress.SERVICE_NAME, congress);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            UnicastRemoteObject.unexportObject(congress, false);
            registry.unbind(Congress.SERVICE_NAME);
            System.out.println("server turned off");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(2000);
        Scanner scanner = new Scanner(System.in);
        System.out.println("press ENTER to shutdown");
        scanner.nextLine();
        scanner.close();
        server.shutdown();
    }
}
