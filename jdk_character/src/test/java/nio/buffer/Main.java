package nio.buffer;

import java.nio.IntBuffer;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // 复制缓冲区
//        copyBUffer();

        //划分缓冲区
//        splice();

        equalsBUffer();

    }

    private static void equalsBUffer() {
        IntBuffer buffer1 = IntBuffer.wrap(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0});
        IntBuffer buffer2 = IntBuffer.wrap(new int[]{6, 5, 4, 3, 2, 1, 7, 8, 9, 0});
        System.out.println(buffer1.equals(buffer2));   //直接比较
    }

    private static void splice() {
        IntBuffer buffer = IntBuffer.wrap(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0});
        for (int i = 0; i < 4; i++) {
            buffer.get();
        }
        IntBuffer slice = buffer.slice();

        System.out.println("划分之后的情况："+ Arrays.toString(slice.array()));
        System.out.println("划分之后的偏移地址："+slice.arrayOffset());
        System.out.println("当前position位置："+slice.position());
        System.out.println("当前limit位置："+slice.limit());

        while (slice.hasRemaining()) {   //将所有的数据全部挨着打印出来
            System.out.print(slice.get()+", ");
        }
    }

    private static void copyBUffer() {
        IntBuffer buffer = IntBuffer.wrap(new int[]{1, 2, 3, 4, 5});
        IntBuffer duplicate = buffer.duplicate();

        System.out.println(buffer == duplicate);
        System.out.println(buffer.array() == duplicate.array());
    }

}
