import java.util.concurrent.Callable;

public class Power implements Callable<Double> {

	private Double n;
	private Double e;

	public Power(Double n, Double e) {
		this.n = n;
		this.e = e;
	}

	public Double call() {
		return Math.pow(n, e);
	}

}
