package larry.poitl;

import com.deepoove.poi.XWPFTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;


public class PoiTlApplication {

    public static void main(String[] args) throws IOException {
        //  PoiTl是一个word模板生成库.这里的地址可以根据具体的情况来获取。
        XWPFTemplate template = XWPFTemplate.compile("C:\\Users\\JNPF\\Desktop\\project\\java\\java_learn\\poi-tl\\src\\main\\java\\larry\\poitl\\template.docx").render(
                new HashMap<String, Object>(){{
                    put("title", "word生成");
                }});
        template.writeAndClose(new FileOutputStream("C:\\Users\\JNPF\\Desktop\\project\\java\\java_learn\\poi-tl\\src\\main\\java\\larry\\poitl\\output.docx"));

    }

}
