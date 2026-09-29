public class Consumer implements Runnable {

	private Counter counter;

	public Consumer(Counter counter) {
		this.counter = counter;
	}

	public void run() {
		for (int i = 0; i < 1000; i++) {
			counter.decrement();
		}
	}
}
