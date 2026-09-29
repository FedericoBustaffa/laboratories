// import java.util.Random;

public class Professor extends User {

	public Professor(Tutor tutor) {
		super(tutor);
	}

	public void run() {
		int k = 3;
		for (int i = 0; i < k; i++) {
			tutor.professorRequest();
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			tutor.professorRelease();
		}
	}

}
