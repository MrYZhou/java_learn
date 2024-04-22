package file;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

public class Index {
    @Test
    @DisplayName("测试文件夹目录生成")
    public void test(){
        File file = new File("D:\\aa\\bb");
        // 可以递归生成文件夹，但是注意文件不会生成
        boolean mkdirs = file.mkdirs();

    }
}
