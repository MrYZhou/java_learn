package re;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @DisplayName("测试2")
    public void testa3() {
        String content = """
              1. 时间: 2025/5/20 14:11:42   \s
                2. 内容: Unexpected character encountered while parsing value: <. Path '', line 0, position 0.#[{"Key":"id","Value":"d548bad875374f89a4855d221761bcfa"},{"Key":"encode","Value":"13820263193"},{"Key":"companyname","Value":"牛逼公司"},{"Key":"creatortime","Value":"2025/5/19 21:01:38"},{"Key":"fullname","Value":"sss"},{"Key":"ipaddress","Value":"60.247.72.98"},{"Key":"ipaddressname","Value":"北京市朝阳区 电信"},{"Key":"enabledMark","Value":"1"},{"Key":"expiresTime","Value":"2025/6/19 21:01:38"},{"Key":"sourceProduct","Value":"java"},{"Key":"sourceWebsite","Value":""},{"Key":"sourceKeyword","Value":""},{"Key":"sourceSale","Value":""},{"Key":"loginId","Value":"6a376928863243239d460e98054adafb"},{"Key":"loginaccount","Value":"13820263193"},{"Key":"loginaccount","Value":"13820263193"},{"Key":"loginipaddress","Value":"182.48.101.50"},{"Key":"loginipaddressname","Value":"北京市海淀区 联通"}]
                -----------------------------------------------------------------------------------------------------------------------------
               2. 内容: Unexpected character encountered while parsing value: <. Path '', line 0, position 0.#[{"Key2":"id","Value":"d548bad875374f89a4855d221761bcfa"},{"Key":"encode","Value":"13820263193"},{"Key":"companyname","Value":"牛逼公司"},{"Key":"creatortime","Value":"2025/5/19 21:01:38"},{"Key":"fullname","Value":"sss"},{"Key":"ipaddress","Value":"60.247.72.98"},{"Key":"ipaddressname","Value":"北京市朝阳区 电信"},{"Key":"enabledMark","Value":"1"},{"Key":"expiresTime","Value":"2025/6/19 21:01:38"},{"Key":"sourceProduct","Value":"java"},{"Key":"sourceWebsite","Value":""},{"Key":"sourceKeyword","Value":""},{"Key":"sourceSale","Value":""},{"Key":"loginId","Value":"6a376928863243239d460e98054adafb"},{"Key":"loginaccount","Value":"13820263193"},{"Key":"loginaccount","Value":"13820263193"},{"Key":"loginipaddress","Value":"182.48.101.50"},{"Key":"loginipaddressname","Value":"北京市海淀区 联通"}]

                1. 时间: 2025/5/20 14:11:42  \s
                """;

        Matcher matcher;
        String regex;
        regex = "#\\[\\{(.)*?\\}\\]";
        Pattern pattern = Pattern.compile(regex);
        matcher = pattern.matcher(content);
        while (matcher.find()){
            String group = matcher.group();
            System.out.println(group);
        }
        if (matcher.find()) {

        }
    }

    @Test
    @DisplayName("测试分割")
    public void testa2() {
        String fileId ="adasd/";
        if(fileId.endsWith("/")){
            fileId = fileId.substring(0, fileId.length()-1);
        }
        System.out.println(fileId);
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
