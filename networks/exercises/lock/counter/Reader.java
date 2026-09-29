
public class Reader implements Runnable {

	private Counter counter;

	public Reader(Counter counter) {
		this.counter = counter;
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			counter.get();
			// System.out.println(counter.get());
		}
	}

}
