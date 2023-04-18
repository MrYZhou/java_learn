package nio.filechannel.src;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class fileChannelRead {
    public static void main(String[] args) throws IOException {
        // 打开chnnel
        RandomAccessFile rw = new RandomAccessFile("nio/filechannel/src/1.txt", "rw");
        FileChannel channel = rw.getChannel();

        // 创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 读取数据到buffer
        int read = channel.read(buffer);
        while (read!=-1){
            System.out.println(read);
            buffer.flip();
            while (buffer.hasRemaining()){
                System.out.println("数据是"+(char)buffer.get());
            }
            buffer.clear();
            read = channel.read(buffer);
        }
        channel.close();
    }
}
