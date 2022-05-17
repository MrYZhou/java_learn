package nio.filechannel.src;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class fileChannelWrite {
    public static void main(String[] args) throws IOException {
        // 打开chnnel
        RandomAccessFile rw = new RandomAccessFile("nio/filechannel/src/1.txt", "rw");
        FileChannel channel = rw.getChannel();

        // 创建buffer

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        String data= "larry..";
        buffer.clear();

        // 写入buffer
        buffer.put(data.getBytes());

        // 写入到channel
        buffer.flip();
        while (buffer.hasRemaining()){
            channel.write(buffer);
        }

        channel.close();

    }
}
