import java.util.Calendar;

public class DatePrinter {
	
	public static void main(String[] args) {
		DatePrinterThread date_printer_thread = new DatePrinterThread();
		Thread date_printer_runnable = new Thread(new DatePrinterRunnable());

		date_printer_thread.start();
		date_printer_runnable.start();

		for (int i = 0; i < 10; i++) {
			try {
				System.out.printf("Thread %s ", Thread.currentThread().getName());
				System.out.printf("day: %s\n", Calendar.getInstance().getTime());
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		try {
			date_printer_thread.join();
			date_printer_runnable.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
