public abstract class User implements Runnable {

	protected Tutor tutor;

	public User(Tutor tutor) {
		this.tutor = tutor;
	}

	public abstract void run();

}
