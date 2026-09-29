// import java.util.Random;

public class Thesist extends User {

	private int id;

	public Thesist(Tutor tutor, int id) {
		super(tutor);
		this.id = id;
	}

	public void run() {
		int k = 5;
		for (int i = 0; i < k; i++) {
			tutor.thesistRequest(id);
			try {
				Thread.sleep(150);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			tutor.thesistRelease(id);
		}
	}

}
