import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();

		Double n = 2.0;
		Vector<Future<Double>> powers = new Vector<Future<Double>>();
		for (double i = 2; i <= 50; i++) {
			powers.add(pool.submit(new Power(n, i)));
		}

		double s = 0.0;
		try {
			for (Future<Double> p : powers) {
				s += p.get();
			}
			System.out.println("Result: " + s);
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		pool.shutdown();
		try {
			while (!pool.awaitTermination(60L, TimeUnit.SECONDS));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
