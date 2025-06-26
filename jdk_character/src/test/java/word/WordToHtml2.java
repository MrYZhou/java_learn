package word;


import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;

public class WordToHtml2 {
    @Test
    @DisplayName("转换")
    public void test() throws IOException {
       String data =  convertDocxToHtml("D:\\Users\\JNPF\\Desktop\\project\\java_learn\\jdk_character\\src\\test\\java\\word\\123.docx");
        System.out.println(data);
    }


    public  String convertDocxToHtml(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             XWPFDocument doc = new XWPFDocument(fis)) {

            StringWriter htmlWriter = new StringWriter();
            htmlWriter.append("<html><body>");

            // 处理段落
            for (XWPFParagraph paragraph : doc.getParagraphs()) {
                String htmlPara = processParagraph(paragraph);
                htmlWriter.append(htmlPara);
            }

            // 处理表格（示例）
            for (XWPFTable table : doc.getTables()) {
                htmlWriter.append("<table border='1'>");
                for (XWPFTableRow row : table.getRows()) {
                    htmlWriter.append("<tr>");
                    for (XWPFTableCell cell : row.getTableCells()) {
                        htmlWriter.append("<td>").append(cell.getText()).append("</td>");
                    }
                    htmlWriter.append("</tr>");
                }
                htmlWriter.append("</table>");
            }

            htmlWriter.append("</body></html>");
            return htmlWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException("转换失败", e);
        }
    }

    private String processParagraph(XWPFParagraph paragraph) {
        StringBuilder paraBuilder = new StringBuilder("<p>");
        for (XWPFRun run : paragraph.getRuns()) {
            String text = run.getText(0);
            if (text == null) continue;

            // 处理基本样式
            if (run.isBold()) paraBuilder.append("<strong>");
            if (run.isItalic()) paraBuilder.append("<em>");
            paraBuilder.append(text);
            if (run.isItalic()) paraBuilder.append("</em>");
            if (run.isBold()) paraBuilder.append("</strong>");
        }
        return paraBuilder.append("</p>").toString();
    }
}
