import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) {
        List<Integer> list = Collections.synchronizedList(new LinkedList<Integer>());
        ExecutorService executor1 = Executors.newCachedThreadPool();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            executor1.execute(new SynchProducer(list));
            executor1.execute(new SynchConsumer(list));
        }

        executor1.shutdown();
        try {
            while (!executor1.awaitTermination(60L, TimeUnit.SECONDS))
                ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("Synchronized: " + (end - start) + " ms");

        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<Integer>();
        ExecutorService executor2 = Executors.newCachedThreadPool();

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            executor2.execute(new ConcurrentProducer(queue));
            executor2.execute(new ConcurrentConsumer(queue));
        }

        executor2.shutdown();
        try {
            while (!executor2.awaitTermination(60L, TimeUnit.SECONDS))
                ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        end = System.currentTimeMillis();
        System.out.println("Concurrent: " + (end - start) + " ms");
    }
}
