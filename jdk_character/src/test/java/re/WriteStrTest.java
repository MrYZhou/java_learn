package re;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Cleanup;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WriteStrTest {
    @Test
    @DisplayName("从文件读取成字符串")
    public void wewq() throws IOException {
//        Triple< Map<String, Object>, Map<String, String[]>> triple =
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
        String filePath = "D:\\Users\\JNPF\\Desktop\\project\\java_learn\\jdk_character\\src\\test\\java\\tool\\txt";
        HashSet<String> chineseStrings = new HashSet<>();
        this.collectFile(new File(filePath), chineseStrings);
        for (String chineseString : chineseStrings) {
            chineseString = chineseString.replace("\"","").replace("'","");
            if(chineseString.length()<=1) continue;
            System.out.println(chineseString);
        }
    }

    @Test
    @DisplayName("获取文件中datatule的中文")
    public void testrepstr22() {
        String filePath = "D:\\Users\\JNPF\\Desktop\\qz\\qz-web\\src\\views\\permission";
        HashSet<String> chineseStrings = new HashSet<>();
        File dir = new File(filePath);
        this.collectFile(dir, chineseStrings);
        for (String chineseString : chineseStrings) {
            chineseString = chineseString.replace("\"","").replace("'","");
            if(chineseString.length()<=1) continue;
            System.out.println(chineseString);
        }
    }

    @Test
    @DisplayName("获取替换的标识")
    public void testrepstr221() {
        String scriptStr = "const txt = row.enabledMark ? '您确定要禁用当前区域吗, 是否继续?' : '您确定要开启当前区域吗, 是否继续?'";

        Matcher matcher;
        String regex;
        // 替换单引号字符串
        regex = "('[\\u4e00-\\u9fa5|，|？|、|！|：|\\w|\\s+|,|?|&]*')";
        Pattern pattern = Pattern.compile(regex);
        matcher = pattern.matcher(scriptStr);
        while (matcher.find()) {
            String group = matcher.group();
            System.out.println(group);
        }
    }




    private void collectFile(File dir, HashSet<String> chineseStrings) {

        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files == null) {
                System.err.println("Error reading files in " + dir.getAbsolutePath());
                return;
            }
            for (File file : files) {
                collectFile(file, chineseStrings);
            }

        } else {
            findFileChinese(dir, chineseStrings);

        }
    }

    @Test
    @DisplayName("再次找是不是有存在中文")
    public void test121() {
        Pattern pattern2;
        Matcher matcher2;
        String line = "1";
        pattern2 =  Pattern.compile("([一-龥]+)");
        matcher2 = pattern2.matcher(line);
        while (matcher2.find()) {
            System.out.println(matcher2.group());
        }
    }

    private void findFileChinese(File file, HashSet<String> chineseStrings) {
        if (file.isDirectory()) return;
        Pattern pattern;
        Matcher matcher;
        Pattern pattern2;
        Matcher matcher2;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                boolean canadd = false;
                pattern = Pattern.compile("\"([\\u4e00-\\u9fa5|，|？|：|\\w]+)\"");
                matcher = pattern.matcher(line);

                while (matcher.find()) {
                    canadd = false;
                    // 再次过滤必须有存在中文
                    pattern2 = Pattern.compile("([一-龥]+)");
                    matcher2 = pattern2.matcher(matcher.group());
                    while (matcher2.find()) {
                        canadd  = true;
                        break;
                    }

                    if(canadd) chineseStrings.add(matcher.group()); // 将中文字符串添加到HashSet中
                }

                pattern = Pattern.compile("'([\\u4e00-\\u9fa5|，|？|：|\\w]+)'");
                matcher = pattern.matcher(line);
                while (matcher.find()) {
                    canadd =false;
                    // 再次过滤必须有存在中文
                    pattern2 = Pattern.compile("([一-龥]+)");
                    matcher2 = pattern2.matcher(matcher.group());
                    while (matcher2.find()) {
                        canadd  = true;
                        break;
                    }
                    if(canadd) chineseStrings.add(matcher.group()); // 将中文字符串添加到HashSet中
                }

            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    String myh = """
              var checkStartTime = (rule, value, callback) => {
                  if (!this.dataForm.endTime) {
                    callback()
                  } else {
                    if (this.dataForm.endTime < value) {
                      callback(new Error('开始日期应该小于结束日期'));
                    } else {
                      this.$refs.dataForm.validateField('endTime');
                      callback()
                    }
                  }
                }
                var checkEndTime = (rule, value, callback) => {
                  if (!this.dataForm.startTime) {
                    callback()
                  } else {
                    if (this.dataForm.startTime > value) {
                      callback(new Error("结束日期应该大于开始日期"));
                    } else {
                      callback()
            """;


    @Test
    @DisplayName("找到所有的引号内文字")
    public void test3() {

        Matcher matcher;
        String regex;
        regex = "'([\\u4e00-\\u9fa5]*?)'";
        Pattern pattern = Pattern.compile(regex);
        matcher = pattern.matcher(myh);
        while (matcher.find()) {
            String group = matcher.group();
            System.out.println(group);
        }
        regex = "\"([\\u4e00-\\u9fa5]*?)\"";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(myh);
        while (matcher.find()) {
            String group = matcher.group();
            System.out.println(group);
        }

    }


}
