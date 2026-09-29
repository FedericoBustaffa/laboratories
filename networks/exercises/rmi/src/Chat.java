import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Chat extends Remote {

	public static final String SERVICE_NAME = "REGISTRATOR";

	public boolean register(String user) throws RemoteException;

	public void send(String username, String msg) throws RemoteException;

	public boolean registerForNotification(Notify notify) throws RemoteException;

	public boolean unregisterForNotification(Notify notify) throws RemoteException;

	public boolean unregister(String user) throws RemoteException;

}
