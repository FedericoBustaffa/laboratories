import java.io.File;

public class Producer implements Runnable {

	private File file;
	private Directories directories;

	public Producer(File file, Directories directories) {
		this.file = file;
		this.directories = directories;
	}

	public void visit(File file) {
		File[] files = file.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				directories.add(f);
				visit(f);
			}
		}
	}

	public void run() {
		directories.add(file);
		visit(file);
		directories.finish();
	}

}
