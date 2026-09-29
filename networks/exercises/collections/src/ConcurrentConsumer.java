import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentConsumer implements Runnable {

	private ConcurrentLinkedQueue<Integer> queue;

	public ConcurrentConsumer(ConcurrentLinkedQueue<Integer> queue) {
		this.queue = queue;
	}

	public void run() {
		for (int i = 0; i < 1000; i++) {
			try {
				queue.remove();
			} catch (NoSuchElementException e) {

			}
		}
	}
}
