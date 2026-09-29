import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Sender implements Runnable {

	private SelectionKey key;
	private Selector selector;
	private SocketChannel socket;
	private ByteBuffer buffer;

	public Sender(SelectionKey key) {
		this.key = key;
		this.selector = key.selector();
		this.socket = (SocketChannel) key.channel();
		this.buffer = (ByteBuffer) key.attachment();
	}

	public void run() {
		try {
			// Thread.sleep(3000);
			buffer.flip();
			while (buffer.hasRemaining())
				socket.write(buffer);

			key.interestOps(SelectionKey.OP_READ);
			key.attach(buffer);
			selector.wakeup();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
