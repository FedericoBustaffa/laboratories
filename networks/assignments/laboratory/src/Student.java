// import java.util.Random;

public class Student extends User {

	public Student(Tutor tutor) {
		super(tutor);
	}

	public void run() {
		int k = 10;
		int id;
		for (int i = 0; i < k; i++) {
			id = tutor.studentRequest();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			tutor.studentRelease(id);
		}
	}

}
