import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Request {

    private static void copy(InputStream is, String filepath) {
        try {
            File html = new File(filepath);
            if (html.exists())
                html.delete();
            html.createNewFile();
            OutputStream os = new FileOutputStream(html);

            byte[] buffer = new byte[512];
            int bytes = 0;
            while ((bytes = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytes);
            }

            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void headers(URLConnection connection) {
        String header;
        int i = 0;
        while ((header = connection.getHeaderField(i)) != null) {
            System.out.printf(connection.getHeaderFieldKey(i) + ": ");
            System.out.println(header);
            i++;
        }
        System.out.println("- - - - - - - - - - -");
    }

    private static void http(HttpURLConnection connection, String pathname) {
        try {
            connection.setRequestMethod("GET");
            File html = new File(pathname);
            if (html.exists())
                html.delete();
            html.createNewFile();
            // connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            String header;
            int i = 0;
            while ((header = connection.getHeaderField(i)) != null) {
                System.out.printf(connection.getHeaderFieldKey(i) + ": ");
                System.out.println(header);
                i++;
            }
            System.out.println("- - - - - - - - - - -");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                OutputStream os = new FileOutputStream(html);

                byte[] buffer = new byte[512];
                int bytes = 0;
                while ((bytes = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytes);
                }

                is.close();
                os.close();
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            URL google = new URL("https://www.di.unipi.it/");
            URLConnection connection = google.openConnection();

            copy(connection.getInputStream(), "file.html");
            headers(connection);
            http((HttpURLConnection) google.openConnection(), "file.html");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
