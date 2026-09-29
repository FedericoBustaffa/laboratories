import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockCounter extends Counter {

	private Lock lock;

	public LockCounter() {
		super();
		lock = new ReentrantLock();
	}

	@Override
	public void increment() {
		lock.lock();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		counter += 1;
		lock.unlock();
	}

	@Override
	public int get() {
		lock.lock();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int c = counter;
		lock.unlock();

		return c;
	}

}
