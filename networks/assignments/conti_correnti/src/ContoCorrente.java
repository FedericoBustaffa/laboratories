import java.util.List;

public class ContoCorrente {

    private String correntista;
    private List<Movimento> movimenti;

    public ContoCorrente(String correntista, List<Movimento> movimenti) {
        this.correntista = correntista;
        this.movimenti = movimenti;
    }

    public ContoCorrente() {
        this(null, null);
    }

    public String getCorrentista() {
        return correntista;
    }

    public List<Movimento> getMovimenti() {
        return movimenti;
    }

    public String toString() {
        String s = correntista + ":\n";
        for (int i = 0; i < movimenti.size(); i++) {
            s += "\tmovimento " + (i + 1) + ":\n" + movimenti.get(i);
        }

        return s;
    }

}
