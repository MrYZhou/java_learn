package re;


import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChineseStringExtractorTest {
    List<String> breaklist = new ArrayList<String>() {{
        add(".git");
        add(".idea");
        add("jnpf-extend");
        add("salesorder");
        add("util");
    }};

    List<String> breakStrlist = new ArrayList<String>() {{
        add("@ApiOperation");
        add("@Api");
        add("log.");
        add("@ApiModelProperty");
        add("@ApiImplicitParam");
        add("@HandleLog");
    }};

    @Test
    public void test() {
        String directory;
        directory = "D:\\Users\\JNPF\\Desktop\\3.4.6-i18n\\3.4.6-i18n-java-boot";
        File dir = new File(directory);

        if (dir.isDirectory()) {
            Set<String> chineseStrings = findChineseStrings(dir);
            // 输出去重后的中文字符串
            int count = 0;
            for (String str : chineseStrings) {
                if (!Objects.equals(str.trim(), "") && str.length() > 5) {
                    System.out.println(str);
                    count++;
                }
            }
            System.out.println("总数:" + count);
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
//        if (!"controller".contains(dir.getName())) {
//            return new HashSet<>();
//        }
        File[] files = dir.listFiles();
        if (files == null) {
            System.err.println("Error reading files in " + dir.getAbsolutePath());
            return chineseStrings;
        }

        Pattern pattern = Pattern.compile("\"[一-龥]+\""); // 匹配中文字符

        Matcher matcher;


        for (File file : files) {
            if (file.isDirectory()) {
                chineseStrings.addAll(findChineseStrings(file)); // 递归遍历子目录
            } else if (file.isFile()) {
                if (file.getName().contains("Controller")) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (!ContainsJudge(line)) {
                                matcher = pattern.matcher(line);
                                while (matcher.find()) {
                                    chineseStrings.add(matcher.group()); // 将中文字符串添加到HashSet中
                                }
                            }

                        }
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                }

            }
        }

        return chineseStrings;
    }
}
