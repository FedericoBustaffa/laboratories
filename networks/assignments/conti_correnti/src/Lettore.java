import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Lettore implements Runnable {

	private File file;
	private ExecutorService pool;
	private ConcurrentHashMap<Causale, Integer> contatore;

	public Lettore(File file) {
		this.file = file;
		pool = Executors.newFixedThreadPool(50);
		contatore = new ConcurrentHashMap<Causale, Integer>();
	}

	public void run() {
		try {
			JsonFactory factory = new JsonFactory();
			JsonParser parser = factory.createParser(file);
			parser.setCodec(new ObjectMapper());
			if (parser.nextToken() != JsonToken.START_ARRAY) {
				System.out.println("not a json array");
				return;
			}
			ContoCorrente conto;
			while (parser.nextToken() == JsonToken.START_OBJECT) {
				conto = parser.readValueAs(ContoCorrente.class);
				pool.execute(new Contatore(contatore, conto));
			}
			pool.shutdown();
			while (!pool.awaitTermination(60L, TimeUnit.SECONDS))
				;
			parser.close();
			System.out.println(contatore);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
