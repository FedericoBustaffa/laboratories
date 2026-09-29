import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class NotifyService extends UnicastRemoteObject implements Notify {

	String user;

	public NotifyService(String user) throws RemoteException {
		this.user = user;
	}

	@Override
	public String getUser() {
		return user;
	}

	@Override
	public void notifyEvent(String notification) throws RemoteException {
		System.out.printf("\n< " + notification + "\n> ");
	}

}
