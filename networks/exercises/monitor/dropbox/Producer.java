
public class Producer implements Runnable {

	private Dropbox dropbox;

	public Producer(Dropbox dropbox) {
		this.dropbox = dropbox;
	}

	public void run() {
		for (int i = 0; i < 20; i++) {
			dropbox.put(i);
		}
	}

}
