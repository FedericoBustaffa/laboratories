import java.io.File;
import java.util.concurrent.locks.Lock;

public class Consumer implements Runnable {

	private Directories directories;
	private Lock lock;

	public Consumer(File file, Directories directories, Lock lock) {
		this.directories = directories;
		this.lock = lock;
	}

	public void run() {
		long id = Thread.currentThread().getId();
		File dir;
		while (!directories.done() || !directories.empty()) {
			dir = directories.remove();
			lock.lock();
			System.out.println("Consumer " + id + " get " + dir.getName() + ": ");
			for (String f : dir.list()) {
				System.out.println(f);
			}
			System.out.println("- - - - - - -");
			lock.unlock();
		}
	}

}
