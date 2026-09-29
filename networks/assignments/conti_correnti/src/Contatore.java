import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Contatore implements Runnable {

	private ConcurrentHashMap<Causale, Integer> contatore;
	private ContoCorrente conto;

	public Contatore(ConcurrentHashMap<Causale, Integer> contatore, ContoCorrente conto) {
		this.contatore = contatore;
		this.conto = conto;
	}

	public void run() {
		List<Movimento> movimenti = conto.getMovimenti();
		Causale causale;
		for (Movimento m : movimenti) {
			causale = m.getCausale();
			if (contatore.get(causale) != null) {
				contatore.put(causale, contatore.get(causale) + 1);
			} else {
				contatore.put(causale, 1);
			}
		}
	}

}
