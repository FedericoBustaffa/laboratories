import java.io.File;
import java.util.LinkedList;

public class Directories {

	private boolean done;
	private LinkedList<File> directories;

	public Directories() {
		done = false;
		directories = new LinkedList<File>();
	}

	public synchronized void add(File file) {
		directories.add(file);
	}

	public synchronized File remove() {
		return directories.removeFirst();
	}

	public synchronized void finish() {
		done = true;
	}

	public synchronized boolean done() {
		return done;
	}

	public synchronized boolean empty() {
		return directories.isEmpty();
	}

}
