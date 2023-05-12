package larry.poitl;

import com.deepoove.poi.XWPFTemplate;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

class PoiTlApplicationTests {

    @Test
    void contextLoads() throws IOException {
        //  PoiTl是一个word模板生成库.这里的地址可以根据具体的情况来获取。
        XWPFTemplate template = XWPFTemplate.compile("template.docx").render(
                new HashMap<String, Object>() {{
                    put("title", "word生成");
                }});
        template.writeAndClose(new FileOutputStream("output.docx"));
    }

}
