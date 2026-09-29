import java.util.List;

public class SynchConsumer implements Runnable {

	private List<Integer> synch_list;

	public SynchConsumer(List<Integer> synch_list) {
		this.synch_list = synch_list;
	}

	public void run() {
		for (int i = 0; i < 1000; i++) {
			try {
				synch_list.remove(0);
			} catch (IndexOutOfBoundsException e) {

			}
		}
	}

}
