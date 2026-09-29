import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Station {
	
	private ThreadPoolExecutor station;
	private int served;
	private int rejected;

	public Station() {
		station = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10));
		served = 0;
		rejected = 0;
	}

	public void serve(Traveler t) {
		try {
			station.execute(t);
			served++;
			System.out.println("Traveler " + t.getID() + " served");
		} catch (RejectedExecutionException e) {
			rejected++;
			System.out.println("Traveler " + t.getID() + " rejected");
		}
	}

	public void close() {
		station.shutdown();
		try {
			while(!station.awaitTermination(60L, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Served: " + served);
		System.out.println("Rejected: " + rejected);
	}
}
