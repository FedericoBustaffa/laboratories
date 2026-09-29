import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

public class ChannelCopy {

    private static File gen(String pathname) {
        try {
            File file = new File(pathname);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileChannel channel = FileChannel.open(file.toPath(), StandardOpenOption.WRITE);
            ByteBuffer buffer;
            long start = System.currentTimeMillis();
            for (int i = 0; i < 50000; i++) {
                buffer = ByteBuffer.wrap(("Riga_" + i + "\n").getBytes());
                channel.write(buffer);
            }
            long end = System.currentTimeMillis();
            System.out.println("Write time: " + (end - start) + " ms");
            channel.close();

            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static long nioCopy(File file, File copy) {
        try {
            FileChannel in = FileChannel.open(file.toPath(), StandardOpenOption.READ);
            FileChannel out = FileChannel.open(copy.toPath(), StandardOpenOption.WRITE);
            ByteBuffer buffer = ByteBuffer.allocate(32);
            long start = System.currentTimeMillis();
            while (in.read(buffer) != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    out.write(buffer);
                }
                buffer.clear();
            }
            long end = System.currentTimeMillis();
            in.close();
            out.close();

            return (end - start);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static long ioCopy(File file, File copy) {
        try {
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(copy);
            byte[] buffer = new byte[32];
            long start = System.currentTimeMillis();
            int bytes;
            while ((bytes = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytes);
            }
            long end = System.currentTimeMillis();
            fis.close();
            fos.close();

            return (end - start);
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void main(String[] args) {
        File file = gen("file.txt");
        if (file == null) {
            System.out.println("file generation error");
            return;
        }
        File copy = new File("copy.txt");
        try {
            if (!copy.exists()) {
                copy.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long time = nioCopy(file, copy);
        System.out.println("Nio copy time: " + time + " ms");

        time = ioCopy(file, copy);
        System.out.println("IO copy time: " + time + " ms");
    }
}
