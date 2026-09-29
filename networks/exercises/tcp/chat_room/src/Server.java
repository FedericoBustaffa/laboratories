import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Server {

	// data structures
	private Set<String> nicknames;
	private List<String> chat;

	// tcp
	private ServerSocket server;

	// client handler threads
	private ExecutorService pool;

	public Server(int port) {
		try {
			nicknames = Collections.synchronizedSet(new TreeSet<String>());
			chat = Collections.synchronizedList(new LinkedList<String>());
			server = new ServerSocket(port);
			server.setSoTimeout(30000);
			pool = Executors.newCachedThreadPool();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void listen() {
		try {
			while (true) {
				Socket socket = server.accept();
				pool.execute(new Handler(nicknames, chat, socket));
			}
		} catch (SocketTimeoutException e) {
			System.out.println("timeout");
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void shutdown() {
		try {
			server.close();
			pool.shutdown();
			while (!pool.awaitTermination(5L, TimeUnit.SECONDS))
				;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server server = new Server(1500);
		server.listen();
		server.shutdown();
	}

}
