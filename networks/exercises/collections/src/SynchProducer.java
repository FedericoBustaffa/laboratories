import java.util.List;

public class SynchProducer implements Runnable {

	private List<Integer> synch_list;

	public SynchProducer(List<Integer> synch_list) {
		this.synch_list = synch_list;
	}

	public void run() {
		for (int i = 0; i < 1000; i++) {
			synch_list.add(i);
		}
	}

}
