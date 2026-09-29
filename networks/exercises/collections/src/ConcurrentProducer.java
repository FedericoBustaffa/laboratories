import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentProducer implements Runnable {

	private ConcurrentLinkedQueue<Integer> queue;

	public ConcurrentProducer(ConcurrentLinkedQueue<Integer> queue) {
		this.queue = queue;
	}

	public void run() {
		for (int i = 0; i < 1000; i++) {
			queue.add(i);
		}
	}

}
