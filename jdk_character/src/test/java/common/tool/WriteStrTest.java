package tool;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WriteStrTest {
    @Test
    @DisplayName("从文件读取成字符串")
    public void wewq() throws IOException {
        String dirPath = "D:/Users/JNPF/Desktop/project/java/java_learn/jdk_character/src/test/java/common/tool/com.txt";
        File dir = new File(dirPath);
        String content = FileUtils.readFileToString(dir, StandardCharsets.UTF_8);
        System.out.println(content);
    }

    @Test
    @DisplayName("直接获取字符串中的中文")
    public void wewq2() throws IOException {
        String dirPath = "D:/Users/JNPF/Desktop/project/java/java_learn/jdk_character/src/test/java/common/tool/com.txt";
        File dir = new File(dirPath);
        String content = FileUtils.readFileToString(dir, "utf8");

        Matcher matcher;
        String regex;
        regex = "<template>(.)*?<script>";
        Pattern pattern = Pattern.compile(regex);
        matcher = pattern.matcher(content);
        if (matcher.find()) {
            String group = matcher.group();
            System.out.println(group);
        }

    }

    public void replaceStr(File file, Map<String, String> source, HashSet<String> hashSet) throws IOException {
        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                replaceStr(subFile, source, hashSet);
            }
        } else {
            String content = FileUtils.readFileToString(file, "UTF-8");
//            content = content.replaceAll("\\{\\{'上一条'}}", "");
//            content = content.replaceAll("\\{\\{'下一条'}}", "");
            // 针对js文件则直接替换
            if (file.getName().contains(".js")) {
                return;
            }
            // 针对vue文件,切割布局和script内容，由于国际化包裹方式的不同
            if (file.getName().contains(".vue")) {

                String[] tem = content.split("<script>");
                if (tem == null || tem.length == 0) {
                    return;
                }

                String html = tem[0];
                String scriptStr = "<script>" + tem[1];
                String leftWrap = "{{$t('";
                String rightWrap = "')}}";

                for (String s : hashSet) {
                    String realKey = s.replace("'", "")
                            .replace(">", "")
                            .replace("<", "")
                            .replace("\"", "");
                    String value = source.get(realKey);
                    if (value == null) continue;

                    if (!s.contains("<")) {

                        String newStr = "\"" + leftWrap + value + rightWrap + "\"";
                        newStr = newStr.replace("{{", "").replace("}}", "");
                        html = html.replace(s, newStr);

                    } else {
                        html = html.replace(s, ">" + leftWrap + value + rightWrap + "<");
                    }

                }

                leftWrap = "this.$t('";
                rightWrap = "')";
                for (String s : hashSet) {
                    String realKey = s.replace("'", "")
                            .replace(">", "")
                            .replace("<", "")
                            .replace("\"", "");
                    String value = source.get(realKey);
                    if (value == null) continue;
                    scriptStr = scriptStr.replace(s, leftWrap + value + rightWrap);
                    scriptStr = scriptStr.replace("'{{", "");
                    scriptStr = scriptStr.replace("}}'", "");
                    scriptStr = scriptStr.replace("\"{{", "");
                    scriptStr = scriptStr.replace("}}\"", "");
                }
                content = html + scriptStr;
                content = content.replaceAll("<u-dropdown-item title", "<u-dropdown-item :title");
                content = content.replaceAll("<el-tooltip content", "<el-tooltip :content");
                content = content.replaceAll("placeholder=", ":placeholder=");

                FileUtils.writeStringToFile(file, content, "UTF-8");
            }


        }
    }

    @Test
    @DisplayName("获取文件中的中文")
    public void testrepstr() {
        String filePath = "D:\\Users\\JNPF\\Downloads\\i18-dev1685957692914\\html\\web";

        HashSet<String> chineseStrings = new HashSet<>();
        this.collectStr(filePath, chineseStrings);
        chineseStrings.forEach(System.out::println);
    }


    private void collectStr(String directory, HashSet<String> chineseStrings) {
        File dir = new File(directory);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files == null) {
                System.err.println("Error reading files in " + dir.getAbsolutePath());
                return;
            }
            files = files[0].listFiles();

            for (File file : files) {
                findFileChinese(file, chineseStrings);
            }

        } else {
            findFileChinese(dir, chineseStrings);

        }
    }


    private void findFileChinese(File file, HashSet<String> chineseStrings) {
        if (file.isDirectory()) return;
        Pattern pattern;
        Matcher matcher;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                pattern = Pattern.compile("\"[一-龥]+\"");
                matcher = pattern.matcher(line);
                while (matcher.find()) {
                    chineseStrings.add(matcher.group()); // 将中文字符串添加到HashSet中
                }

                pattern = Pattern.compile("'[一-龥]+'");
                matcher = pattern.matcher(line);
                while (matcher.find()) {
                    chineseStrings.add(matcher.group()); // 将中文字符串添加到HashSet中
                }

                pattern = Pattern.compile(">[一-龥].*<");
                matcher = pattern.matcher(line);
                while (matcher.find()) {
                    chineseStrings.add(matcher.group()); // 将中文字符串添加到HashSet中
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
