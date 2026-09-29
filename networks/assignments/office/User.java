
public class User implements Runnable {

	private int id;

	public User(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

	public void run() {
		try {
			Thread.sleep(1000);
			System.out.println("User n." + id + " served");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
