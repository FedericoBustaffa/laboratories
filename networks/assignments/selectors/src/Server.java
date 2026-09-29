import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {

    private Selector selector;
    private ExecutorService pool;

    private static AtomicInteger ACTIVE_CONNECTIONS = new AtomicInteger(0);

    public Server(SocketAddress service) {
        try {
            ServerSocketChannel server = ServerSocketChannel.open();
            server.bind(service);
            System.out.println("service on: " + service);
            server.configureBlocking(false);
            // System.out.println("server set on non blocking mode");

            selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);
            // System.out.println("server registered on ACCEPT event");

            pool = Executors.newCachedThreadPool();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void accept(SelectionKey key) {
        try {
            System.out.println("waiting for connections");
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel socket = server.accept();
            System.out.println("ACTIVE CONNECTIONS: " + ACTIVE_CONNECTIONS.incrementAndGet());
            socket.configureBlocking(false);
            System.out.println("accepted connection from: " + socket.getRemoteAddress());
            socket.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(32));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void multiplex() {
        try {
            selector.select();
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = readyKeys.iterator();
            SelectionKey key;
            while (it.hasNext()) {
                key = it.next();
                it.remove();
                if (key.isValid()) {
                    if (key.isAcceptable()) {
                        accept(key);
                    } else if (key.isReadable()) {
                        key.interestOps(0);
                        pool.execute(new Receiver(key, ACTIVE_CONNECTIONS));
                    } else if (key.isWritable()) {
                        key.interestOps(0);
                        pool.execute(new Sender(key));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            pool.shutdown();
            while (!pool.awaitTermination(60L, TimeUnit.SECONDS))
                ;

            for (SelectionKey k : selector.keys()) {
                System.out.println("chiusura: " + k.channel());
                k.channel().close();
            }
            selector.close();
            System.out.println("server turned off");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(new InetSocketAddress("localhost", 1500));
        do {
            server.multiplex();
        } while (ACTIVE_CONNECTIONS.get() > 0);
        server.shutdown();
    }
}
