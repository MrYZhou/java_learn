package tool;

import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class IncludeText {
    @Test
    public void test() {
        File cc = new File("D:\\Users\\JNPF\\Desktop\\project\\java\\java_learn\\jdk_character\\src\\test\\java\\common\\tool\\com.txt");
        HashSet<String> set = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(cc))) {
            String line;
            while ((line = reader.readLine()) != null) {
                set.add(line);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
//        set.forEach(k-> System.out.println(k));

        File cc2 = new File("D:\\Users\\JNPF\\Desktop\\project\\java\\java_learn\\jdk_character\\src\\test\\java\\common\\tool\\tem.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(cc2))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (set.contains(line)) {
//                    System.out.println(line);
                } else {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
