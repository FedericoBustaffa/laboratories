
public class PiGreco extends Thread {

	private double accuracy;
	private double pi_greco;

	public PiGreco(double accuracy) {
		this.accuracy = accuracy;
		this.pi_greco = 0;
	}

	public double getPI() {
		return pi_greco;
	}

	public void run() {
		int sign = 1;
		double denom = 1;
		double error = Math.abs(Math.PI - pi_greco);
		long start = System.currentTimeMillis();
		do {
			pi_greco += (sign) * (4.0 / denom);
			sign = sign * (-1);
			denom += 2;
			// System.out.printf("Error: %.10g\n", error);
			error = Math.abs(Math.PI - pi_greco);
		} while(error > accuracy && !Thread.currentThread().isInterrupted());
		long end = System.currentTimeMillis();

		System.out.println("Tempo di calcolo: " + (end - start) + " ms");
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("USAGE: java PiGreco <accuracy> <time>");
			return;
		}

		double accuracy = Double.parseDouble(args[0]);
		long sleep_time = Long.parseLong(args[1]);

		PiGreco pi = new PiGreco(accuracy);
		pi.start();

		try {
			pi.join(sleep_time);
			if (pi.isAlive()) {
				pi.interrupt();
				System.out.printf("Calcolo interrotto\n");
			} else {
				System.out.printf("Calcolo terminato correttamente\n");
			}
			System.out.printf("Valore trovato: %.10g\n", pi.getPI());
			System.out.printf("Precisione: %.10g\n", Math.abs(Math.PI - pi.getPI()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

