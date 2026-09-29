import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Deserializator {
	public static void main(String[] args) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("person.ser"))) {
			Person person = (Person) ois.readObject();
			System.out.println(person.getName());
			System.out.println(person.getSurname());
			System.out.println(person.getAge());
			System.out.println(person.getCity());
			System.out.println(person.getUniversity());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
