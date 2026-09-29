import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLockCounter extends Counter {

	private ReadWriteLock rw_lock;
	private Lock r_lock;
	private Lock w_lock;

	public RWLockCounter() {
		super();
		rw_lock = new ReentrantReadWriteLock();
		r_lock = rw_lock.readLock();
		w_lock = rw_lock.writeLock();
	}

	@Override
	public void increment() {
		w_lock.lock();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		counter += 1;
		w_lock.unlock();
	}

	@Override
	public int get() {
		r_lock.lock();
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int c = counter;
		r_lock.unlock();

		return c;
	}

}
