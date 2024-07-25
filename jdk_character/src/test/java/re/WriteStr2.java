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
                !properties.hasOpinion && !properties.isCustomCopy) {
                                                                                                                        createConfirm({
                                                                                                                          iconType: 'warning',
                                                                                                                          title: '提示',
                                                                                                                          content: '此操作将通过该审批单，是否继续?',
                                                                                                                          onOk: () => {
                                                                                                                            handleBatchOperation();
                                                                                                                          },
                                                                                                                        });
                                                                                                                        return;
                                                                                                                      }
                """;
        html = this.addReplaceKeyDirect("\"('[\\\\u4e00-\\\\u9fa5|，|？|、|！|：|\\\\w|\\\\s+|,|?]+')\"", html, "{{TransText('", "')}}");

        System.out.println(html);
    }

    private String addReplaceKeyDirect(String patternStr, String html, String prefix, String suffix) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            String key = matcher.group(1);
            if (key == null || key.isEmpty() || key.contains("\n") || key.contains("\r")) continue;
            String value = "测试";
            return html.replace(key, prefix + value + suffix);
        }
        return html;
    }

    @Test
    @DisplayName("特殊符号的汉字串")
    public void test11() throws IOException {
        String html = """
                asicModal
                         v-bind="$attrs"
                         @register="registerModal"
                         :title="TransText('search.advancedQuery')"
                         :okText="TransText('common.queryText')"
                         @ok="handleSubmit"
                         destroyOnClose
                         class="jnpf-super-quer
                """;
        html = html.replaceAll(" okText=\"TransText\\('", " :okText=\"TransText('");
        System.out.println(html);

    }
    @Test
    @DisplayName("特殊符号的汉字串")
    public void test12() throws IOException {
        String[] split = "oa/20232/2121/33.doc".split("/");
        String name = split[split.length-1];
        System.out.println(name);

    }

}
