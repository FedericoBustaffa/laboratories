import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Notify extends Remote {

	public String getUser() throws RemoteException;

	public void notifyEvent(String notification) throws RemoteException;

}