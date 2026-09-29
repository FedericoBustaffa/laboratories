import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class ClientNIO {

	private Selector selector;
	private SocketChannel socket;
	private ByteBuffer buffer;
	private Scanner scanner;

	private static boolean DONE = false;

	public ClientNIO() {
		try {
			socket = SocketChannel.open();
			socket.configureBlocking(false);

			selector = Selector.open();
			socket.register(selector, SelectionKey.OP_CONNECT);
			// System.out.println("client registered on CONNECT event");

			buffer = ByteBuffer.allocate(8);

			scanner = new Scanner(System.in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void connect(SelectionKey key, SocketAddress remote) {
		try {
			System.out.println("connection to: " + remote);
			socket = (SocketChannel) key.channel();
			socket.connect(remote);
			while (!socket.finishConnect())
				System.out.println("not connected yet...");
			System.out.println("connected to " + remote);
			key.interestOps(SelectionKey.OP_READ);
		} catch (IOException e) {
			System.out.println("service " + remote + " not available");
			System.exit(1);
		}
	}

	private void receive(SelectionKey key) {
		try {
			socket = (SocketChannel) key.channel();
			int bytes = socket.read(buffer);
			String msg = new String(buffer.array(), 0, bytes);
			System.out.println(msg);
			key.cancel();
			DONE = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void multiplex() {
		try {
			selector.select();
			Set<SelectionKey> readyKeys = selector.selectedKeys();
			Iterator<SelectionKey> it = readyKeys.iterator();
			SelectionKey k;
			while (it.hasNext()) {
				k = it.next();
				it.remove();
				if (k.isConnectable()) {
					System.out.println("CONNECT");
					connect(k, new InetSocketAddress("localhost", 1500));
				} else if (k.isReadable()) {
					System.out.printf("READ: ");
					receive(k);
				}
				System.out.println("- - - - - - - -");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void shutdown() {
		try {
			selector.close();
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ClientNIO client = new ClientNIO();
		while (!DONE)
			client.multiplex();
		client.shutdown();
	}
}
