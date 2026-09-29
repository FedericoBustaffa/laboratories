import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

	Congress congress;
	Registry registry;
	Scanner scanner;

	public Client(int port) {
		try {
			registry = LocateRegistry.getRegistry(port);
			congress = (Congress) registry.lookup(Congress.SERVICE_NAME);
			scanner = new Scanner(System.in);
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
				if (cmd.equals("register")) {
					System.out.printf("speaker: ");
					String speaker = scanner.nextLine();
					System.out.printf("session: ");
					int session = Integer.parseInt(scanner.nextLine());
					if (!congress.register(speaker, session))
						System.out.println("< speaker registration error");
				} else if (cmd.equals("program")) {
					System.out.println(congress.program());
				} else if (cmd.equals("exit")) {
					break;
				} else {
					System.out.println("command \"" + cmd + "\" not valid");
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void shutdown() {
		scanner.close();
	}

	public static void main(String[] args) {
		Client client = new Client(2000);
		client.run();
		client.shutdown();
	}
}
