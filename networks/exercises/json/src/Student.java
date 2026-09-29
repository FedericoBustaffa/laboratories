public class Student {

    private String name;
    private String surname;
    private int age;
    private String city;
    private String university;

    public Student(String name, String surname, int age, String city, String university) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.city = city;
        this.university = university;
    }

    public Student() {
        this(null, null, 0, null, null);
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

    public String toString() {
        return name + "\n" + surname + "\n" + age + "\n" + city + "\n" + university;
    }
}
