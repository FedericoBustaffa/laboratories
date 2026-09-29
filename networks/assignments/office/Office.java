import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Office {
	
	private ThreadPoolExecutor office;
	private Queue<User> queue;

	public Office(int n, int k) {
		office = new ThreadPoolExecutor(4, 4, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(k));
		queue = new LinkedList<User>();
		for (int i = 0; i < n; i++) {
			queue.add(new User(i));
		}
	}

	public void serve() {
		User u = queue.element();
		boolean rejected = true;
		while(rejected) {
			try {
				office.execute(u);
				rejected = false;
			} catch (RejectedExecutionException e) {
				rejected = true;
			}
		}
		queue.remove();
	}

	public void close() {
		office.shutdown();
		try {
			while(!office.awaitTermination(60, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Queue size: " + queue.size());
		System.out.println("Served: " + office.getCompletedTaskCount());
	}

}
