package filechannel.src;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTest {
    @Test
    public void test() throws IOException {
        // 获取文件的path
        Path jsonFilePath = Paths.get("1.json");
        // 读取文件的字节
        byte[] bytes = Files.readAllBytes(jsonFilePath);

    }
}
