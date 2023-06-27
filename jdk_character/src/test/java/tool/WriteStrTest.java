package tool;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WriteStrTest {
    @Test
    @DisplayName("从文件读取成字符串")
    public void wewq() throws IOException {
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

    @Test
    @DisplayName("包裹国际化字符")
    public void wrapTag() throws IOException {
        String leftWrap = "{{this.$t(\"";
        String rightWrap = "\")}}";
        String dirPath = "D:/Users/JNPF/Desktop/project/java/java_learn/jdk_character/src/test/java/common/tool/com.txt";

        Map<String, String> source = new HashMap<>();
        String str1 = "提示";
        String str2 = "没有更多数据";
        source.put(str1, leftWrap + "common.tip" + rightWrap);
        source.put(str2, leftWrap + "common.search2" + rightWrap);

        File dir = new File(dirPath);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : Objects.requireNonNull(files)) {
                this.replaceStr(file, source);
            }
        } else {
            this.replaceStr(dir, source);
        }
    }

    public void replaceStr(File file, Map<String, String> source) throws IOException {
        if (file.isDirectory()) {
            for (File subFile : Objects.requireNonNull(file.listFiles())) {
                replaceStr(subFile, source);
            }
        } else {
            String content = FileUtils.readFileToString(file, "UTF-8");
            // 切割布局和script内容，由于国际化包裹方式的不同

            String html = content.split("<script>")[0];
            String scriptStr = content.split("<script>" + "<script>")[1];

            for (Map.Entry<String, String> entry : source.entrySet()) {
                String oldStr = entry.getKey();
                String newStr = entry.getValue();
                html = html.replace(oldStr, newStr);
            }

            for (Map.Entry<String, String> entry : source.entrySet()) {
                String oldStr = entry.getKey();
                String newStr = entry.getValue();
                scriptStr = scriptStr.replace(oldStr, newStr);
            }
            content = html + scriptStr;
            FileUtils.writeStringToFile(file, content, "UTF-8");
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

            if (files != null) {
                for (File file : files) {
                    findFileChinese(file, chineseStrings);
                }
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

    @Test
    @DisplayName("替换冒号")
    public void test() {
        Pattern pattern;
        Matcher matcher;
        String content = """
                     <el-form-item :label="数据选择">
                             <el-radio-group v-model="type">
                               <el-radio :label="0">当前页面数据</el-radio>
                               <el-radio :label="1">全部页面数据</e
                      <el-form-item :label="导出字段">
                             <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll"
                               @change="handleCheckAllChange">全选</el-checkbox>
                             <el-checkbox-group v-model="columns" @change="handleCheckedChange">
                               <el-checkbox v-for="item in columnList" :label="item.prop" :key="item.prop">
                                 {{item.label}}          
                """;
        String[] tagList = new String[]{
                "title", "placeholder", "description", "content"
        };
//        pattern = Pattern.compile(":label=\"([一-龥]+)\"");
        pattern = Pattern.compile(":label=\"([一-龥]+)\"");
        matcher = pattern.matcher(content);
        while (matcher.find()) {
            content = content.replace(matcher.group(1), "'" + matcher.group(1) + "'");
        }
        System.out.println(content);
    }
}
