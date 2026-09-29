import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.security.Security;
import java.util.Enumeration;

public class App {

    private static void cacheTest(String ttl, String hostname) {
        Security.setProperty("networkaddress.cache.ttl", ttl);
        long start = System.nanoTime();
        try {
            InetAddress.getByName(hostname);
        } catch (UnknownHostException e) {
            // e.printStackTrace();
        }
        long end = System.nanoTime();
        System.out.println((end - start) + " nanoseconds");
    }

    public static void main(String[] args) throws Exception {
        cacheTest("0", "www.google.it");
        cacheTest("1000", "www.google.it");

        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            System.out.println(interfaces.nextElement().toString());
        }
    }

}
