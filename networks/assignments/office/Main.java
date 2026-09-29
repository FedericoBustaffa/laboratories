
public class Main {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("USAGE: java Main <n> <k>");
			return;
		}

		int n = Integer.parseInt(args[0]);
		int k = Integer.parseInt(args[1]);

		Office office = new Office(n, k);
		for (int i = 0; i < n; i++) {
			office.serve();
		}
		
		office.close();
	}

}
