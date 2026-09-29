import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Main {

	private static int numero_conti = 3;
	private static int numero_movimenti = 2;

	private static List<ContoCorrente> generazioneConti() {
		Random random = new Random();
		Causale[] causali = Causale.values();
		List<ContoCorrente> conti = new LinkedList<ContoCorrente>();
		List<Movimento> movimenti;
		String data;
		Causale causale;
		for (int i = 1; i <= numero_conti; i++) {
			movimenti = new LinkedList<Movimento>();
			for (int j = 1; j <= numero_movimenti; j++) {
				data = new Date(random.nextLong()).toString();
				causale = causali[random.nextInt(causali.length)];
				movimenti.add(new Movimento(data, causale));
			}
			conti.add(new ContoCorrente("Correntista " + i, movimenti));
		}

		return conti;
	}

	private static File generazioneFileConti(List<ContoCorrente> conti) {
		try {
			File file = new File("conti.json");
			if (file.exists())
				file.delete();
			file.createNewFile();

			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			// mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

			long start = System.currentTimeMillis();
			byte[] content = mapper.writeValueAsBytes(conti);

			FileChannel fc = FileChannel.open(file.toPath(), StandardOpenOption.WRITE);
			ByteBuffer buffer = ByteBuffer.wrap(content);
			while (buffer.hasRemaining()) {
				fc.write(buffer);
			}
			long end = System.currentTimeMillis();
			System.out.println("Writing time: " + (end - start) + " ms");
			fc.close();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		List<ContoCorrente> conti = generazioneConti();
		File file = generazioneFileConti(conti);

		Thread lettore = new Thread(new Lettore(file));
		lettore.start();

		try {
			lettore.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
