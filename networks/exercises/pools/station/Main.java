
public class Main {

	public static void main(String[] args) {
		Station station = new Station();
		for (int i = 0; i < 50; i++) {
			try {
				Thread.sleep(50);
				station.serve(new Traveler(i));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		station.close();
	}

}
