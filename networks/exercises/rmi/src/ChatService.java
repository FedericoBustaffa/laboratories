import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

public class ChatService extends UnicastRemoteObject implements Chat {

	private Set<String> users;
	private Vector<Notify> notifies;

	public ChatService() throws RemoteException {
		users = new TreeSet<String>();
		notifies = new Vector<Notify>();
	}

	@Override
	public synchronized boolean register(String user) throws RemoteException {
		if (users.add(user)) {
			for (Notify n : notifies) {
				if (!n.getUser().equals(user))
					n.notifyEvent(user + " join the chat");
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public synchronized boolean unregister(String user) throws RemoteException {
		if (users.remove(user)) {
			for (Notify n : notifies) {
				if (!n.getUser().equals(user))
					n.notifyEvent(user + " left the chat");
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void send(String username, String msg) throws RemoteException {
		for (Notify n : notifies) {
			if (!n.getUser().equals(username))
				n.notifyEvent(username + ": " + msg);
		}
	}

	@Override
	public synchronized boolean registerForNotification(Notify notify) throws RemoteException {
		System.out.println("< " + notify.getUser() + " registered for notify");
		return notifies.add(notify);
	}

	@Override
	public synchronized boolean unregisterForNotification(Notify notify) throws RemoteException {
		System.out.println("< " + notify.getUser() + " unregistered for notify");
		return notifies.remove(notify);
	}

}
