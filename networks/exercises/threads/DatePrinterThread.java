import java.util.Calendar;

public class DatePrinterThread extends Thread {

	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				System.out.printf("Thread %s ", Thread.currentThread().getName());
				System.out.printf("day: %s\n", Calendar.getInstance().getTime());
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
