public class Movimento {

	private String data;
	private Causale causale;

	public Movimento(String data, Causale causale) {
		this.data = data;
		this.causale = causale;
	}

	public Movimento() {
		this(null, null);
	}

	public String getData() {
		return data;
	}

	public Causale getCausale() {
		return causale;
	}

	public String toString() {
		return "\t\tData: " + data + "\n\t\tCausale: " + causale.toString() + "\n";
	}
}
