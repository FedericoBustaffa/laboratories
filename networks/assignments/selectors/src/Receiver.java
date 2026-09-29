import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

public class Receiver implements Runnable {

	private SelectionKey key;
	private Selector selector;
	private SocketChannel socket;
	private ByteBuffer buffer;
	private AtomicInteger ACTIVE_CONNECTIONS;

	public Receiver(SelectionKey key, AtomicInteger ACTIVE_CONNECTIONS) {
		this.key = key;
		this.selector = key.selector();
		this.socket = (SocketChannel) key.channel();
		this.buffer = (ByteBuffer) key.attachment();
		this.ACTIVE_CONNECTIONS = ACTIVE_CONNECTIONS;
	}

	public void run() {
		try {
			// Thread.sleep(3000);
			buffer.clear();
			int b = socket.read(buffer);
			String msg = new String(buffer.array(), 0, b);
			if (msg.equals("exit")) {
				System.out.println("ACTIVE CONNECTIONS: " + ACTIVE_CONNECTIONS.decrementAndGet());
				selector.wakeup();
				return;
			}

			key.interestOps(SelectionKey.OP_WRITE);
			key.attach(buffer);
			selector.wakeup();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
