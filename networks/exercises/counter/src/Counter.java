import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

	private AtomicInteger counter;

	public Counter() {
		counter = new AtomicInteger(0);
	}

	public void increment() {
		counter.incrementAndGet();
	}

	public void decrement() {
		counter.decrementAndGet();
	}

	public int get() {
		return counter.get();
	}

}
