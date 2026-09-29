import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Tutor tutor = new Tutor();
        ExecutorService executor = Executors.newCachedThreadPool();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 20; i++) {
            executor.execute(new Student(tutor));
        }

        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            executor.execute(new Thesist(tutor, random.nextInt(20)));
        }

        for (int i = 0; i < 3; i++) {
            executor.execute(new Professor(tutor));
        }

        executor.shutdown();
        try {
            while (!executor.awaitTermination(60L, TimeUnit.SECONDS))
                ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        tutor.shutdown();
        System.out.println("Execution time: " + (end - start) + " ms");
    }
}
