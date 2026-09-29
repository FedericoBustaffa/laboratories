import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Counter counter = new Counter();
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 1000; i++) {
            executor.execute(new Producer(counter));
            executor.execute(new Consumer(counter));
        }

        executor.shutdown();
        try {
            while (!executor.awaitTermination(60L, TimeUnit.SECONDS))
                ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Counter: " + counter.get());
    }
}
