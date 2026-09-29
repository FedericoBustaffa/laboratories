import java.util.Random;

public class Traveler implements Runnable {

	private int id;

	public Traveler(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

	public void run() {
		Random random = new Random();
		System.out.println("Traveler: " + id + " buying a ticket");
		try {
			Thread.sleep(random.nextLong(200, 500));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Traveler: " + id + " ticket bougth");
	}

}
