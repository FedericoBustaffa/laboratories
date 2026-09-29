
public class Consumer implements Runnable {

	private boolean e;
	private Dropbox dropbox;

	public Consumer(boolean e, Dropbox dropbox) {
		this.e = e;
		this.dropbox = dropbox;
	}
	
	public void run() {
		for (int i = 0; i < 10; i++) {
			dropbox.take(e);
		}
	}

}
