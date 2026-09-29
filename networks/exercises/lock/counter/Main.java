
public class Main {

	public static void TestCounter(Counter counter, String s) {
		Thread[] writers = new Thread[10];
		Thread[] readers = new Thread[10];
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			writers[i] = new Thread(new Writer(counter));
			readers[i] = new Thread(new Reader(counter));
			writers[i].start();
			readers[i].start();
		}

		try {
			for (int i = 0; i < 10; i++) {
				writers[i].join();
				readers[i].join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println(s + ": " + (end - start) + " ms");
	}

	public static void main(String[] args) {
		Counter counter = new Counter();
		TestCounter(counter, "No lock");

		Counter lock_counter = new LockCounter();
		TestCounter(lock_counter, "Lock");
		
		Counter rw_lock_counter = new RWLockCounter();
		TestCounter(rw_lock_counter, "Read Write Lock");
	}

}
