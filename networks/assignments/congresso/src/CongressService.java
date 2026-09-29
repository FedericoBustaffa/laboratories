import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class CongressService extends UnicastRemoteObject implements Congress {

	private List<List<String>> sessions;

	public CongressService() throws RemoteException {
		sessions = new ArrayList<List<String>>(12);
		for (int i = 0; i < 12; i++) {
			sessions.add(new ArrayList<String>());
		}
	}

	@Override
	public synchronized boolean register(String speaker, int session) throws RemoteException {
		if (session < 0 || session >= 12) {
			return false;
		}
		if (sessions.get(session).size() == 5) {
			return false;
		}
		if (sessions.get(session).contains(speaker)) {
			return false;
		}
		return sessions.get(session).add(speaker);
	}

	@Override
	public synchronized String program() throws RemoteException {
		return sessions.toString();
	}

}
