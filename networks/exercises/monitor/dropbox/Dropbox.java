
class Dropbox {

	protected boolean full = false;
	protected int num;

	public int take(boolean e) {
		String s = e ? "Pari" : "Dispari";

		while (!full || e == (num % 2 != 0)) {
			System.out.println("Attendi per: " + s);
			try {
				Thread.sleep((long) (Math.random() * 100));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		try {
			Thread.sleep((long) (Math.random() * 1000));
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println(s + " <-> " + num);
		full = false;
		return num;
	}

	public void put(int n) {
		while (full) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Producer ha inserito " + n);
		num = n;
		full = true;
	}
}
