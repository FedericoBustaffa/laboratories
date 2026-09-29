import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Congress extends Remote {

	public static final String SERVICE_NAME = "CONGRESS";

	public boolean register(String speaker, int session) throws RemoteException;

	public String program() throws RemoteException;

}
