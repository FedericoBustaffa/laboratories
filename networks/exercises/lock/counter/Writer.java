
public class Writer implements Runnable {
	
	private Counter counter;

	public Writer(Counter counter) {
		this.counter = counter;
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			counter.increment();
		}
	}

}
