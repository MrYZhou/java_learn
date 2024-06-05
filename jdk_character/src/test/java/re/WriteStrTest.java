package re;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Cleanup;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Triple;
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

    @Test
    public void wrapTag23() throws IOException {
        String txt ="\"countdown\": {\n" +
                "            \"normalText\": \"获取验证码\", \"sendText\": \"{0}秒后重新获取\"\n" +
                "        }";
        cn.hutool.json.JSONObject entries = JSONUtil.parseObj(txt);
        System.out.println(entries);
    }
    @Test
    @DisplayName("替换中文")
    public void wrapTag2() throws IOException {
        String key ="common.ka";
//        key="哈哈a";
        Matcher matcher;
        String regex;
        // 替换单引号字符串
        regex = "([a-z|.]*)";
        Pattern pattern = Pattern.compile(regex);
        matcher = pattern.matcher(key);
        while (matcher.find()) {
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
        regex = "('[\\u4e00-\\u9fa5|，|？|、|！|：|\\w|\\s+|,|?|&]*?')";
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
        pattern = Pattern.compile(":label=\"([一-龥]+)\"");
        matcher = pattern.matcher(content);
        while (matcher.find()) {
            content = content.replace(matcher.group(1), "'" + matcher.group(1) + "'");
        }
        System.out.println(content);
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
    @DisplayName("把所有格式 >$t()< 增加{{}}")
    public void test2() {
        String html = """
                                 <span v-if="scope.row.type==1">$t('notice.bulletin')</span>
                                  <span v-if="scope.row.type==2">$t('notice.workflow')</span>
                                  <span v-if="scope.row.type==3">$t('notice.system')</span>
                """;

        html = html.replaceAll(">\\$t\\('(.*)'\\)<", ">{{\\$t('($1)')}}<");
        System.out.println(html);
    }

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

    @Test
    @DisplayName("验证是否成功")
    public void test311() throws IOException {
        File file = new File("E:\\Code\\Git\\develop\\java\\overtime\\jnpf-resources\\Language\\java\\en-US.json");
        @Cleanup FileInputStream fileInputStream = new FileInputStream(file);
        JSONObject jsonObject = JSONObject.parseObject(fileInputStream, LinkedHashMap.class);
        System.out.println(jsonObject);
    }


}
