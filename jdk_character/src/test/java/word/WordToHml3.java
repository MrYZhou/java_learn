package word;

import fr.opensagres.poi.xwpf.converter.core.FileImageExtractor;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WordToHml3 {

    @Test
    @DisplayName("转换")
    public void test() throws Exception {
        File source = new File("D:\\Users\\JNPF\\Desktop\\project\\java_learn\\jdk_character\\src\\test\\java\\word\\988.docx");
        File outputHtml = new File("D:\\Users\\JNPF\\Desktop\\project\\java_learn\\jdk_character\\src\\test\\java\\word\\1.thml");
        convertDocxToHtml(source, outputHtml);
    }
    public  void convertDocxToHtml(File docxFile, File htmlFile) throws Exception {
        try (FileInputStream fis = new FileInputStream(docxFile);
             XWPFDocument document = new XWPFDocument(fis);
             FileOutputStream fos = new FileOutputStream(htmlFile)) {

            // 配置转换选项（如图片处理）
            XHTMLOptions options = XHTMLOptions.create();
            options.setExtractor(new FileImageExtractor(new File("images")));  // 图片保存路径

            // 转换为 HTML
            XHTMLConverter.getInstance().convert(document, fos, options);
        }
    }
}
