import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Crawler {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("USAGE: java Crawler <path> <k>");
            return;
        }

        File file = new File(args[0]);
        int k = Integer.parseInt(args[1]);
        Directories directories = new Directories();
        Lock lock = new ReentrantLock();

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(new Producer(file, directories));
        for (int i = 0; i < k; i++) {
            executor.execute(new Consumer(file, directories, lock));
        }

        executor.shutdown();
        try {
            while (!executor.awaitTermination(60L, TimeUnit.SECONDS))
                ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
