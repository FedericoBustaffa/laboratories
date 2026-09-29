import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class App {
    public static void main(String[] args) {
        try (InputStream is = new FileInputStream("file.txt");
                OutputStream os = new FileOutputStream("copy.txt")) {
            int c;

            long start = System.currentTimeMillis();
            while ((c = is.read()) != -1) {
                os.write(c);
            }
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (InputStream is = new FileInputStream("file.txt");
                OutputStream os = new FileOutputStream("copy.txt")) {
            int n = 64;
            byte[] b = new byte[n];

            long start = System.currentTimeMillis();
            while (is.read(b) == n) {
                os.write(b);
            }
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream("file.txt"));
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("copy.txt"))) {
            int c;

            long start = System.currentTimeMillis();
            while ((c = bis.read()) != -1) {
                bos.write(c);
            }
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream("file.txt"));
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("copy.txt"))) {
            int n = 64;
            byte[] b = new byte[n];

            long start = System.currentTimeMillis();
            while (bis.read(b) == n) {
                bos.write(b);
            }
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Reader reader = new FileReader("file.txt");
                Writer writer = new FileWriter("copy.txt")) {
            int c;

            long start = System.currentTimeMillis();
            while ((c = reader.read()) != -1) {
                writer.write(c);
            }
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"));
                BufferedWriter writer = new BufferedWriter(new FileWriter("copy.txt"))) {
            int c;

            long start = System.currentTimeMillis();
            while ((c = reader.read()) != -1) {
                writer.write(c);
            }
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
