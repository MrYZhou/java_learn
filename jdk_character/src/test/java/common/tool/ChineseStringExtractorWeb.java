package tool;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChineseStringExtractorWeb {
    List<String> breaklist = new ArrayList() {{
        add(".git");
        add(".idea");
        add("jnpf-extend");
        add("salesorder");
        add("util");
        add("extend");
    }};

    List<String> breakStrlist = new ArrayList() {{
        add("//");
        add("/*");
    }};

    @Test
    @DisplayName("输出前端项目中的文字")
    public void test() {
        HashSet<String> hashSet = new HashSet<>();
        ArrayList<String> list = new ArrayList<String>() {{
            add("D:\\Users\\JNPF\\Desktop\\3.4.6-i18n\\3.4.6-i18n-web\\src\\components");
//            add("E:\\Code\\Git\\develop\\java\\overtime\\jnpf-resources\\TemplateCode\\TemplateCode2\\html");
//            add("E:\\Code\\Git\\develop\\java\\overtime\\jnpf-resources\\TemplateCode\\macro");

//            add("D:\\Users\\JNPF\\Desktop\\3.4.6-i18n\\3.4.6-i18n-web\\src\\components");
//            add("D:\\Users\\JNPF\\Desktop\\3.4.6-i18n\\3.4.6-i18n-web\\src\\views");
        }};
        ArrayList<String> nostr = new ArrayList<String>() {{
//            add("打印属性相关");
//            add("导入组件相关");
//            add("打印属性相关");
//            add("这是一个批量打印的方法宏,包含打印相关方法");
        }};

        for (String directory : list) {
            this.collectStr(directory, hashSet);
        }

        int count = 0;
        for (String s : hashSet) {
            System.out.println(s);
            count++;
        }

        System.out.println("总数:" + count);
    }

    private void collectStr(String directory, HashSet<String> hashSet) {
        File dir = new File(directory);
        if (dir.isDirectory()) {
            Set<String> chineseStrings = findChineseStrings(dir);
            // 输出去重后的中文字符串
            for (String str : chineseStrings) {
                if (str.contains("{")) continue;
                hashSet.add(str.replace(">", "").replace("<", "").replace("'", ""));
            }

        } else {
            System.exit(1);
        }
    }

    private boolean ContainsJudge(String line) {
        boolean flag = false;
        for (String s : breakStrlist) {
            if (line.contains(s)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private Set<String> findChineseStrings(File dir) {
        Set<String> chineseStrings = new HashSet<>(); // 存储中文字符串
        if (breaklist.contains(dir.getName())) {
            return new HashSet<>();
        }
        File[] files = dir.listFiles();
        if (files == null) {
            System.err.println("Error reading files in " + dir.getAbsolutePath());
            return chineseStrings;
        }

        Pattern pattern = Pattern.compile("\"[\u4e00-\u9fa5]+\""); // 匹配中文字符
        Matcher matcher;
        for (File file : files) {
            if (file.isDirectory()) {
                chineseStrings.addAll(findChineseStrings(file)); // 递归遍历子目录
            } else if (file.isFile()) {
                if (file.getName().contains(".js")) continue;
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (!ContainsJudge(line)) {

                        }
                        matcher = pattern.matcher(line);
                        while (matcher.find()) {
                            chineseStrings.add(matcher.group()); // 将中文字符串添加到HashSet中
                        }
                        pattern = Pattern.compile(">[\u4e00-\u9fa5]+<");
                        matcher = pattern.matcher(line);
                        while (matcher.find()) {
                            chineseStrings.add(matcher.group()); // 将中文字符串添加到HashSet中
                        }

                        pattern = Pattern.compile("'[\u4e00-\u9fa5]+'");
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

        return chineseStrings;
    }

}
