package re;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class IncludeTextTest {
    @Test
    @DisplayName("获取汉字")
    public void testa() {
        File cc = new File("D:\\Users\\JNPF\\Desktop\\project\\java_learn\\jdk_character\\src\\test\\java\\re\\com.txt");
        HashSet<String> set = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(cc))) {
            String line;
            while ((line = reader.readLine()) != null) {
                set.add(line);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("总数"+set.size());
        for (String key : set) {
            System.out.println(key);
        }
    }

    @Test
    @DisplayName("测试两个文件差异,显示出第一个文件没有的字")
    public void test() {
        File cc = new File("D:\\Users\\JNPF\\Desktop\\project\\java_learn\\jdk_character\\src\\test\\java\\re\\com.txt");
        HashSet<String> set = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(cc))) {
            String line;
            while ((line = reader.readLine()) != null) {
                set.add(line);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        File cc2 = new File("D:\\Users\\JNPF\\Desktop\\project\\java_learn\\jdk_character\\src\\test\\java\\re\\tem.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(cc2))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!set.contains(line)) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
