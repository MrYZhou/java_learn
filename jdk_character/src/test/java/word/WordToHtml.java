package word;


import fr.opensagres.poi.xwpf.converter.core.FileImageExtractor;
import fr.opensagres.poi.xwpf.converter.core.FileURIResolver;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class WordToHtml {
    @Test
    @DisplayName("转换")
    public void test() throws IOException, ParserConfigurationException, TransformerException {
        System.out.println(docxtoHtml("D:\\Users\\JNPF\\Desktop\\project\\java_learn\\jdk_character\\src\\test\\java\\word\\988.docx", "D:\\Users\\JNPF\\Desktop\\project\\java_learn\\jdk_character\\src\\test\\java\\word\\222.docx"));
    }


    public String docxtoHtml(String fileName, String outPutFile) throws TransformerException, IOException, ParserConfigurationException {
        long startTime = System.currentTimeMillis();
        XWPFDocument document = new XWPFDocument(new FileInputStream(fileName));

        // 用于存储目录内容
        StringBuilder toc = new StringBuilder();
        toc.append("<div id='toc'>\n<ul>\n");  // 直接从 <ul> 开始，表示目录

        // 遍历文档中的段落，查找目录项
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        int tocLevel = 0; // 目录的当前级别，1代表一级目录，2代表二级目录，3代表三级目录
        boolean tocStarted = false; // 标记目录是否开始

        for (XWPFParagraph paragraph : paragraphs) {
            String style = paragraph.getStyle();  // 获取段落样式
            String text = paragraph.getText();  // 获取段落文本

            // 根据段落的样式级别来识别目录项，假设标题样式为 Heading 1, Heading 2, Heading 3
            if (style != null) {
                if (style.equals("Heading 1")) {  // 一级标题
                    if (tocStarted) {
                        toc.append("</ul>\n"); // 关闭上一级目录
                    }
                    toc.append("<ul>\n");  // 开始一个新的无序列表
                    toc.append("<li><a href='#" + text.hashCode() + "'>" + text + "</a></li>\n");
                    tocLevel = 1;
                    tocStarted = true;
                } else if (style.equals("Heading 2")) {  // 二级标题
                    if (tocLevel == 1) {
                        toc.append("<ul>\n");  // 开始二级目录
                    }
                    toc.append("<li><a href='#" + text.hashCode() + "'>" + text + "</a></li>\n");
                    tocLevel = 2;
                } else if (style.equals("Heading 3")) {  // 三级标题
                    if (tocLevel == 2) {
                        toc.append("<ul>\n");  // 开始三级目录
                    }
                    toc.append("<li><a href='#" + text.hashCode() + "'>" + text + "</a></li>\n");
                    tocLevel = 3;
                }
            }

            // 在目录项前插入锚点
            if (style != null && (style.equals("Heading 1") || style.equals("Heading 2") || style.equals("Heading 3"))) {
                String anchor = "<a name='" + text.hashCode() + "'></a>";
                String modifiedText = anchor + text;  // 在目录项文本前添加锚点

                // 更新段落中的文本
                for (XWPFRun run : paragraph.getRuns()) {
                    run.setText(modifiedText, 0); // 更新段落内容
                }
            }
        }

        // 如果目录结束后，确保关闭所有的<ul>标签
        if (tocLevel > 0) {
            toc.append("</ul>\n");
        }

        toc.append("</ul>\n</div>\n");  // 关闭最外层的 <ul> 和 <div>

        // 设置XHTMLOptions
        XHTMLOptions options = XHTMLOptions.create().indent(4);
        File imageFolder = new File("D:\\Users\\JNPF\\Desktop\\project\\java_learn\\jdk_character\\src\\test\\java\\word\\");  // 图片临时文件夹路径
        options.setExtractor(new FileImageExtractor(imageFolder));
        options.URIResolver(new FileURIResolver(imageFolder));

        // 使用 `XHTMLConverter` 进行 DOCX 到 HTML 的转换
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        XHTMLConverter.getInstance().convert(document, byteArrayOutputStream, options);

        System.out.println("Generate " + outPutFile + " with " + (System.currentTimeMillis() - startTime) + " ms.");

        // 获取转换后的HTML内容
        String htmlContent = new String(byteArrayOutputStream.toByteArray(), "UTF-8");

        // 将TOC插入到HTML的开头
        htmlContent = toc + htmlContent;

        // 处理分页符：将分页符添加到HTML中
        htmlContent = htmlContent.replaceAll("<!-- PAGE_BREAK -->", "<div class='page-break'></div>");

        // 添加表格样式（边框）
        htmlContent = htmlContent.replaceAll("<table>", "<table style='border: 1px solid black !important; border-collapse: collapse; width: 100%;'>");
        htmlContent = htmlContent.replaceAll("<td>", "<td style='border: 1px solid black !important; padding: 5px; text-align: left;'>");
        htmlContent = htmlContent.replaceAll("<th>", "<th style='border: 1px solid black !important; padding: 5px; text-align: left;'>");
        htmlContent = htmlContent.replaceAll("<tr>", "<tr style='border: 1px solid black !important;'>");
        htmlContent = htmlContent.replaceAll("<thead>", "<thead style='border: 1px solid black !important;'>");
        htmlContent = htmlContent.replaceAll("<tbody>", "<tbody style='border: 1px solid black !important;'>");
        htmlContent = htmlContent.replaceAll("<tfoot>", "<tfoot style='border: 1px solid black !important;'>");

        // 增加全局CSS样式（确保表格和目录样式正确）
        String style = "<style>\n" +
                "table { border: 1px solid black !important; border-collapse: collapse; width: 100%; }\n" +
                "td, th { border: 1px solid black !important; padding: 5px; text-align: left; }\n" +
                "tr { border: 1px solid black !important; }\n" +
                "ul { list-style-type: none; padding: 0; }\n" + // 去掉默认的列表样式
                "li { margin: 5px 0; }\n" + // 设置目录项的间距
                "</style>\n";

        htmlContent = style + htmlContent;


        return htmlContent;
    }
}
