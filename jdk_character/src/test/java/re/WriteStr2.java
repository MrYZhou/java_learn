package re;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WriteStr2 {

    Pattern pattern;
    Matcher matcher;


    @Test
    @DisplayName("特殊符号的汉字串")
    public void test10() throws IOException {
        String html = """
                @change="handleChange">
                <div class="avatar-box">
                  <a-avatar :size="50" :src="apiUrl + user.avatar" class="avatar" v-if="user.avatar" />
                  <div class="avatar-hover">更换头像</div>
                </div>
                """;
        html =  this.addReplaceKeyDirect(">(\\s*[\\u4e00-\\u9fa5|，|？|、|！|：|\\w|\\s+|,|?]+)<", html,"{{TransText('","')}}");

        pattern = Pattern.compile(">(\\s*[\\u4e00-\\u9fa5|，|？|、|！|：|\\w|\\s+|,|?]+)<");

        System.out.println(html);
    }
    private String addReplaceKeyDirect(String patternStr, String html, String prefix, String suffix) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            String key = matcher.group(1);
            if(key == null || key.isEmpty() || key.contains("\n") || key.contains("\r")) continue;
            String value = "测试";
            return html.replace(key, prefix + value+ suffix);
        }
        return html;
    }

}
