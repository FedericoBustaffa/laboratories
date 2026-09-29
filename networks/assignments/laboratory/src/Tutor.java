import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Tutor {

	private Boolean[] laboratory;
	private int free;

	private FileWriter writer;

	private ReentrantLock lock;
	private Condition free_lab;
	private Condition[] thesist_pc_free;
	private Condition one_free;

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

		lock = new ReentrantLock();
		free_lab = lock.newCondition();
		thesist_pc_free = new Condition[20];
		for (int i = 0; i < 20; i++) {
			thesist_pc_free[i] = lock.newCondition();
		}
		one_free = lock.newCondition();
	}

	private int getFree() {
		int id = 0;
		while (id < 20 && (laboratory[id] || lock.hasWaiters(thesist_pc_free[id])))
			id++;

		return (id != 20) ? id : -1;
	}

	public int studentRequest() {
		lock.lock();
		int id;
		long s_id = Thread.currentThread().getId();
		while (lock.hasWaiters(free_lab) || (id = getFree()) == -1) {
			try {
				writer.append("Student " + s_id + " waiting\n");
				one_free.await();
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

		lock.unlock();

		return id;
	}

	public void studentRelease(int id) {
		lock.lock();

		laboratory[id] = false;
		free++;
		try {
			long s_id = Thread.currentThread().getId();
			writer.append("Student " + s_id + " release " + id + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (free == 20 && lock.hasWaiters(free_lab)) {
			free_lab.signal();
		} else if (lock.hasWaiters(thesist_pc_free[id])) {
			thesist_pc_free[id].signal();
		} else if (lock.hasWaiters(one_free)) {
			one_free.signal();
		}

		lock.unlock();
	}

	public void thesistRequest(int id) {
		lock.lock();
		long t_id = Thread.currentThread().getId();
		while (lock.hasWaiters(free_lab) || laboratory[id]) {
			try {
				writer.append("Thesist " + t_id + " waiting for " + id + "\n");
				thesist_pc_free[id].await();
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

		lock.unlock();
	}

	public void thesistRelease(int id) {
		lock.lock();

		laboratory[id] = false;
		free++;
		try {
			long t_id = Thread.currentThread().getId();
			writer.append("Thesist " + t_id + " release " + id + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (lock.hasWaiters(free_lab) && free == 20) {
			free_lab.signal();
		} else if (lock.hasWaiters(thesist_pc_free[id])) {
			thesist_pc_free[id].signal();
		} else if (lock.hasWaiters(one_free)) {
			one_free.signal();
		}
		lock.unlock();
	}

	public void professorRequest() {
		lock.lock();
		long p_id = Thread.currentThread().getId();
		while (free < 20) {
			try {
				writer.append("Professor " + p_id + " waiting\n");
				free_lab.await();
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

		lock.unlock();
	}

	public void professorRelease() {
		lock.lock();

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

		if (lock.hasWaiters(free_lab)) {
			free_lab.signal();
		} else {
			for (int i = 0; i < 20; i++) {
				if (lock.hasWaiters(thesist_pc_free[i])) {
					thesist_pc_free[i].signal();
				} else if (lock.hasWaiters(one_free)) {
					one_free.signal();
				}
			}
		}
		lock.unlock();
	}

	public void shutdown() {
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
