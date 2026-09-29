import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonStream<T> {

    private File file;
    private JsonFactory factory;
    private ObjectMapper mapper;
    private JsonGenerator generator;
    private JsonParser parser;

    public JsonStream(String filepath) {
        file = new File(filepath);
        factory = new JsonFactory();
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void writeObject(Object object) {
        try {
            mapper.writeValue(file, object);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public T readObject(Class<T> type) {
        try {
            return mapper.readValue(file, type);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void writeArray(List<T> objects) {
        try {
            generator = factory.createGenerator(file, JsonEncoding.UTF8);
            generator.setCodec(mapper);
            generator.useDefaultPrettyPrinter();
            generator.writeStartArray();
            for (T o : objects) {
                generator.writeObject(o);
            }
            generator.writeEndArray();
            generator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<T> readArray(Class<T> type) {
        try {
            List<T> objects = new Vector<T>();
            parser = factory.createParser(file);
            parser.setCodec(mapper);
            if (parser.nextToken() != JsonToken.START_ARRAY) {
                System.out.println("not an array");
                return null;
            }
            while (parser.nextToken() == JsonToken.START_OBJECT) {
                objects.add(parser.readValueAs(type));
            }
            parser.close();

            return objects;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        JsonStream<Student> js = new JsonStream<Student>("student.json");
        Student federico = new Student("Federico", "Bustaffa", 23, "Pisa", "Informatica");
        Student elisabetta = new Student("Elisabetta", "Rossi", 23, "Firenze", "Fisica");
        Student margherita = new Student("Margherita", "Aloisi", 22, "Catanzaro", "Chimica");
        Student ciccio = new Student("Francesco", "Artuso", 26, "Reggio Calabria", "Fisica");

        List<Student> students = new Vector<Student>();
        students.add(federico);
        students.add(elisabetta);
        students.add(margherita);
        students.add(ciccio);

        js.writeArray(students);
        students.clear();
        students = js.readArray(Student.class);
        for (Student s : students) {
            System.out.println(s);
            System.out.println("- - - - -");
        }
    }
}
