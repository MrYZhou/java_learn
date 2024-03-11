package re;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
            add("D:\\Users\\JNPF\\Desktop\\qz\\qz-web\\src\\views\\msgCenter\\sendConfig");
        }};

        for (String directory : list) {
            File sourceDir = new File(directory);
            this.collectStr(sourceDir, hashSet);
        }

        int count = 0;
        for (String s : hashSet) {
            System.out.println(s.replace(">", "").replace("<", "").replace("\'", "")
                    .replace("\"", ""));

            count++;
        }

        System.out.println("总数:" + count);
    }

    private void collectStr(File dir, HashSet<String> chineseStrings) {

        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files == null) {
                System.err.println("Error reading files in " + dir.getAbsolutePath());
                return;
            }
            for (File file : files) {
                collectStr(file, chineseStrings);
            }
        } else {
            findFileChinese(dir, chineseStrings);

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

    private void findFileChinese(File file, HashSet<String> chineseStrings) {
        if (file.isDirectory()) return;
        Pattern pattern;
        Matcher matcher;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                boolean dialogTitle = false;
                if (line.contains("el-dialog :title")) {
                    dialogTitle = true;
                }
                pattern = Pattern.compile("\"[\u4e00-\u9fa5]+\"");
                matcher = pattern.matcher(line);
                while (matcher.find()) {
                    chineseStrings.add(matcher.group() + (dialogTitle ? "-dialogTitle" : "")); // 将中文字符串添加到HashSet中
                }

                pattern = Pattern.compile("\'[\u4e00-\u9fa5]+\'");
                matcher = pattern.matcher(line);
                while (matcher.find()) {
                    chineseStrings.add(matcher.group() + (dialogTitle ? "-dialogTitle" : "")); // 将中文字符串添加到HashSet中
                }

                pattern = Pattern.compile(">[\u4e00-\u9fa5].*<");
                matcher = pattern.matcher(line);
                while (matcher.find()) {
                    chineseStrings.add(matcher.group() + (dialogTitle ? "-dialogTitle" : "")); // 将中文字符串添加到HashSet中
                }

                pattern = Pattern.compile(">[\u4e00-\u9fa5].*\\S");
                matcher = pattern.matcher(line);
                while (matcher.find()) {
                    chineseStrings.add(matcher.group() + (dialogTitle ? "-dialogTitle" : "")); // 将中文字符串添加到HashSet中
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
