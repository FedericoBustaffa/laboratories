
public class Main {

	public static void main(String[] args) {
		Dropbox2 dropbox = new Dropbox2();
		Thread consumer1 = new Thread(new Consumer(true, dropbox));
		Thread consumer2 = new Thread(new Consumer(false, dropbox));
		Thread producer = new Thread(new Producer(dropbox));

		consumer1.start();
		consumer2.start();
		producer.start();

		try {
			consumer1.join();
			consumer2.join();
			producer.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
