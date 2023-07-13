import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WriteStr2 {
    String a = "<template>\n" +
            "<el-dialog title=\"详情\"\n" +
            "           :close-on-click-modal=\"false\" append-to-body\n" +
            "           :visible.sync=\"visible\" class=\"JNPF-dialog JNPF-dialog_center\" lock-scroll\n" +
            "           width=\"600px\">\n" +
            "<el-row :gutter=\"15\" class=\"\">\n" +
            "<el-form ref=\"elForm\" :model=\"dataForm\" size=\"small\" label-width=\"100px\" label-position=\"right\" >\n" +
            "    <template v-if=\"!loading\">\n" +
            "            <el-col :span=\"24\"  >\n" +
            "                <el-form-item  label=\"##结束后，流程不再进行委托！##\"  \n" +
            " prop=\"name\" >\n" +
            "                        <p>{{dataForm.name}}</p>\n" +
            "                </el-form-item>\n" +
            "            </el-col>\n" +
            "    </template>\n" +
            "</el-form>\n" +
            "    </el-row>\n" +
            "    <span slot=\"footer\" class=\"dialog-footer\">\n" +
            "        <el-button @click=\"visible = false\"> 取 消</el-button>\n" +
            "    </span>\n" +
            "    <Detail v-if=\"detailVisible\" ref=\"Detail\" @close=\"detailVisible = false\" />\n" +
            "    </el-dialog>\n" +
            "</template>\n";
    Pattern pattern;
    Matcher matcher;

    @Test
    @DisplayName("获取##中的中文")
    public void testrepstr() {

        pattern = Pattern.compile("#!([\\S|\\s]+?)!#");

        matcher = pattern.matcher(a);

        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }


    @Test
    @DisplayName("替换$t的包裹")
    public void testrepstr2() {
        String input = """
                 <el-tab-pane label="$t('profile.menu-bindSet')" name="justAuth" v-if="useSocials">
                                            <JustAuth ref="justAuth" v-if="visible.justAuth" />
                                          </el-tab-pane>
                                          <el-tab-pane label="啊飒飒" name="authorize" class="el-tab-pane-authorize">
                                            <Authorize ref="authorize" v-if="visible.authorize" />
                                          </el-tab-pane>
                                          <el-tab-pane label="$t('route.system-log')" name="sysLog">
                                            <SysLog ref="sysLog" v-if="visible.sysLog" /
                """;
        input = input.replaceAll("label=\"([^\\u4e00-\\u9fa5]*?)\"", ":label=\"$1\"");
        System.out.println(input);
    }

    @Test
    @DisplayName("查找字符串")
    public void testrepstr3() {

        String kk = " @click=\"addOrUpdateHandle(scope.row.id)\" >编辑";

        pattern = Pattern.compile(">(.*)");

        matcher = pattern.matcher(kk);

        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }

    @Test
    @DisplayName("替换")
    public void testrepstr4() {

        String html = """
                <el-form-item label="时间">
                            <el-date-picker v-model="pickerVal" type="daterange" start-placeholder="开始日期"
                              end-placeholder="结束日期" :picker-options="pickerOptions" value-format="timestamp"
                              :editable="false" clearable>
                """;

//        html = html.replaceAll("start-placeholder=\"([^\\u4e00-\\u9fa5]*?)\"" , ":start-placeholder=\"$1\"");
//        html = html.replaceAll("end-placeholder=\"([^\\u4e00-\\u9fa5]*?)\"" , ":end-placeholder=\"$1\"" );
//        System.out.println(html);

        pattern = Pattern.compile("start-placeholder=\"([^\\u4e00-\\u9fa5]*?)\"");

        matcher = pattern.matcher(html);

        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }

    @Test
    @DisplayName("替换")
    public void testrepstr5() throws IOException {
        File file = new File("D:/Users/JNPF/Desktop/qz/qz-web/src/views/basic/profile/index.vue");
        String content = FileUtils.readFileToString(file, "UTF-8");
        System.out.println(content);
    }

    @Test
    @DisplayName("替换2")
    public void testrepstr6() throws IOException {

        String html = """
                   content-position="left"  :closable= "false"  title ="#!$t('route.workFlow-form')!#"  content ="$t('profile.emergencyContact')" >
                    </groupTitle>
                </el-form-item>
                </el-col>
                          <el-col :span="24"  >
                          <el-divider   content-position="center" >
                {{ TransText('我是分割线') }}         </el-divider>
                          </el-col>
                          <el-col :span="24">
                              <el-tabs  v-model="activedxonff"  tab-position="top" class="mb-20">
                          </el-tab-pane >
                              <el-tab-pane  label="#!$t('route.workFlow-form')!#">  
                """;

        pattern = Pattern.compile("label=\"#!([^\\u4e00-\\u9fa5]*?)!#\"");
        matcher = pattern.matcher(html);
        while (matcher.find()) {
            html = html.replace(matcher.group(), ":" + matcher.group());
        }

        pattern = Pattern.compile("title[\\s|\\S]*=\"#!([^\\u4e00-\\u9fa5]*?)!#\"");
        matcher = pattern.matcher(html);
        while (matcher.find()) {
            html = html.replace(matcher.group(), ":" + matcher.group());

        }

        pattern = Pattern.compile("content[\\s|\\S]*=\"#!([^\\u4e00-\\u9fa5]*?)!#\"");
        matcher = pattern.matcher(html);
        while (matcher.find()) {
            System.out.println(matcher.group());
            html = html.replace(matcher.group(), ":" + matcher.group());
        }

        html = html.replaceAll("#!", "");
        html = html.replaceAll("!#", "");
        System.out.println(html);
    }


}
