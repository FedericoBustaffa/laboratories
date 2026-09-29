import java.io.FileWriter;
import java.io.IOException;

public class Tutor {

	private Boolean[] laboratory;
	private int free;

	private FileWriter writer;

	private int[] waiting_thesists;
	private int waiting_professors;

	public Tutor() {
		laboratory = new Boolean[20];
		for (int i = 0; i < 20; i++) {
			laboratory[i] = false;
		}
		free = 20;

		try {
			writer = new FileWriter("history.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		waiting_thesists = new int[20];
		for (int i = 0; i < 20; i++) {
			waiting_thesists[i] = 0;
		}
		waiting_professors = 0;
	}

	private int getFree() {
		int id = 0;
		while (id < 20 && (laboratory[id] || waiting_thesists[id] > 0))
			id++;

		return (id != 20) ? id : -1;
	}

	public synchronized int studentRequest() {
		int id;
		long s_id = Thread.currentThread().getId();
		while (waiting_professors > 0 || (id = getFree()) == -1) {
			try {
				writer.append("Student " + s_id + " waiting\n");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		laboratory[id] = true;
		free--;
		try {
			writer.append("Student " + s_id + " get " + id + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return id;
	}

	public synchronized void studentRelease(int id) {
		laboratory[id] = false;
		free++;
		try {
			long s_id = Thread.currentThread().getId();
			writer.append("Student " + s_id + " release " + id + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

		notifyAll();
	}

	public synchronized void thesistRequest(int id) {
		long t_id = Thread.currentThread().getId();
		waiting_thesists[id]++;
		while (waiting_professors > 0 || laboratory[id]) {
			try {
				writer.append("Thesist " + t_id + " waiting for " + id + "\n");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		laboratory[id] = true;
		free--;
		try {
			writer.append("Thesist " + t_id + " get " + id + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		waiting_thesists[id]--;
	}

	public synchronized void thesistRelease(int id) {
		laboratory[id] = false;
		free++;
		try {
			long t_id = Thread.currentThread().getId();
			writer.append("Thesist " + t_id + " release " + id + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

		notifyAll();
	}

	public synchronized void professorRequest() {
		long p_id = Thread.currentThread().getId();
		waiting_professors++;
		while (free < 20) {
			try {
				writer.append("Professor " + p_id + " waiting\n");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < 20; i++) {
			laboratory[i] = true;
		}
		free = 0;
		try {
			writer.append("Professor " + p_id + " get the lab\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		waiting_professors--;
	}

	public synchronized void professorRelease() {
		long p_id = Thread.currentThread().getId();
		for (int i = 0; i < 20; i++) {
			laboratory[i] = false;
		}
		free = 20;
		try {
			writer.append("Professor " + p_id + " release the lab\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

		notifyAll();
	}

	public void shutdown() {
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
