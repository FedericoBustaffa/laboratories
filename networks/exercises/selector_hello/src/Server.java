import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {

    private Selector selector;
    private AtomicInteger connections;

    public Server() {
        try {
            selector = Selector.open();
            connections = new AtomicInteger(0);

            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);
            InetAddress host = InetAddress.getLocalHost();
            int port = 1500;
            server.bind(new InetSocketAddress(host, port));

            server.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("< server on");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getConnections() {
        return connections.get();
    }

    private void accept(SelectionKey key) {
        try {
            System.out.println("< waiting for connections");
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel socket = server.accept();
            socket.configureBlocking(false);
            System.out.println("< new client connected: " + connections.incrementAndGet());
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            socket.register(selector, SelectionKey.OP_READ, buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receive(SelectionKey key) {
        try {
            SocketChannel socket = (SocketChannel) key.channel();
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            buffer.clear();
            socket.read(buffer);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(buffer.array());

            key.interestOps(SelectionKey.OP_WRITE);
            key.attach(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send(SelectionKey key) {
        try {
            SocketChannel socket = (SocketChannel) key.channel();
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            buffer.flip();
            while (buffer.hasRemaining())
                socket.write(buffer);

            key.interestOps(SelectionKey.OP_READ);
            key.attach(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void multiplex() {
        try {
            selector.select();
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = readyKeys.iterator();
            SelectionKey k;
            while (it.hasNext()) {
                k = it.next();
                it.remove();
                if (k.isAcceptable())
                    accept(k);
                else if (k.isReadable())
                    receive(k);
                else if (k.isWritable())
                    send(k);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            for (SelectionKey k : selector.keys()) {
                System.out.println("< closure: " + k.channel());
                k.channel().close();
                k.cancel();
            }
            selector.close();
            System.out.println("< server turned off");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        Server server = new Server();
        do {
            server.multiplex();
        } while (server.getConnections() > 0);
        server.shutdown();
    }
}
