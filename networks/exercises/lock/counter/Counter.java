
public class Counter {
	
	protected int counter;

	public Counter() {
		this.counter = 0;
	}

	public void increment() {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		counter += 1;
	}

	public int get() {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return counter;
	}

}
