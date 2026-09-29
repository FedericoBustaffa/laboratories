import java.io.Serializable;

public class Person implements Serializable {

	private String name;
	private String surname;
	private int age;
	private String city;
	private String university;

	public Person(String name, String surname, int age, String city, String university) {
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.city = city;
		this.university = university;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public int getAge() {
		return age;
	}

	public String getCity() {
		return city;
	}

	public String getUniversity() {
		return university;
	}

}