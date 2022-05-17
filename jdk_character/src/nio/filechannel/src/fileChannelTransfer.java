package nio.filechannel.src;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class fileChannelTransfer {
    public static void main(String[] args) throws IOException {
        // 打开chnnel1
        RandomAccessFile rw = new RandomAccessFile("nio/filechannel/src/1.txt", "rw");
        FileChannel channel = rw.getChannel();

        RandomAccessFile rw2 = new RandomAccessFile("./2.txt", "rw");
        FileChannel channel2 = rw2.getChannel();

        RandomAccessFile rw3 = new RandomAccessFile("./3.txt", "rw");
        FileChannel channel3 = rw3.getChannel();

        long position =0 ;
        long count = channel.size();

        // 2去获取1的数据
        channel2.transferFrom(channel,position,count);

        // 从2中传输到3
        channel2.transferTo(position,count,channel3);

        channel.close();
        channel2.close();
        channel3.close();


    }
}
