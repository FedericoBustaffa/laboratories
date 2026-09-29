import java.rmi.AccessException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Server {

	private Chat chat;
	private Registry registry;

	public Server(int port) {
		try {
			chat = new ChatService();
			registry = LocateRegistry.createRegistry(3000);
			registry.rebind(Chat.SERVICE_NAME, chat);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void shutdown() {
		try {
			UnicastRemoteObject.unexportObject(chat, false);
			registry.unbind(Chat.SERVICE_NAME);
			System.out.println("server closed");
		} catch (NoSuchObjectException e) {
			e.printStackTrace();
		} catch (AccessException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server server = new Server(1500);
		Scanner scanner = new Scanner(System.in);
		System.out.println("press ENTER to stop");
		scanner.nextLine();
		scanner.close();
		server.shutdown();
	}
}
