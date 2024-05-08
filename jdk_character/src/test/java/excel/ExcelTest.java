package excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import excel.delaycompute.Index;
import lombok.Cleanup;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelTest {

    String sheetName = "Sheet1";

    @Test
    @DisplayName("加批注测试")
    public void test1() {
        String filePath = "D:\\Users\\JNPF\\Desktop\\project\\java_learn\\jdk_character\\src\\test\\java\\excel\\2.xlsx";
        String sheetName = "Sheet1";
        int columnIndex = 0;
        String comment = "增加批注内容";

        try {

            @Cleanup Workbook workbook = new XSSFWorkbook(filePath);
            Sheet sheet = workbook.getSheet(sheetName);
            sheet.createFreezePane(0,0);
            addComment(workbook, sheetName, columnIndex, comment);
            @Cleanup FileOutputStream fileOut = new FileOutputStream("108.xlsx");
            workbook.write(fileOut);
            System.out.println("批注添加成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCommentToHeader(String filePath, String sheetName, int columnIndex, String comment) throws IOException {
        Workbook workbook = new XSSFWorkbook(filePath);
        Sheet sheet = workbook.getSheet(sheetName);
        Row headerRow = sheet.getRow(0);
        Cell headerCell = headerRow.getCell(columnIndex);
        // 判断是否存在批注
        Comment cellComment = headerCell.getCellComment();
        if (!Objects.isNull(cellComment)) {
            return;
        }
        Drawing<?> drawing = sheet.createDrawingPatriarch();
        CreationHelper factory = workbook.getCreationHelper();
        ClientAnchor anchor = factory.createClientAnchor();
        Comment headerComment = drawing.createCellComment(anchor);
        RichTextString str = factory.createRichTextString(comment);
        headerComment.setString(str);
        headerCell.setCellComment(headerComment);

        FileOutputStream fileOut = new FileOutputStream("110.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }

    public List getData() {
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> dataMap1 = new HashMap<>();
        dataMap1.put("alias1", "数据1");
        dataMap1.put("alias2", "数据2");
        dataList.add(dataMap1);
        Map<String, Object> dataMap2 = new HashMap<>();
        dataMap2.put("alias1", "数据3");
        dataMap2.put("alias2", "数据4");
        dataList.add(dataMap2);
        return dataList;
    }

    public List getExcelEntity() {
        List<ExcelExportEntity> entitys = new ArrayList<>();
        ExcelExportEntity export = new ExcelExportEntity("*字段名1", "alias1"); // 示例字段，需根据实际情况填写
        entitys.add(export);
        export = new ExcelExportEntity("字段名2421412312", "alias2");
        entitys.add(export);
        return entitys;
    }

    @Test
    @DisplayName("样式红色")
    public void test2() throws IOException {
        // 准备ExcelExportEntity列表，定义导出列的信息
        List<ExcelExportEntity> entitys = getExcelEntity();
        // 示例数据列表
        List<Map<String, Object>> dataList = getData();

        // 初始化Workbook，这里使用HSSFWorkbook导出为.xls格式
        @Cleanup Workbook workbook = new HSSFWorkbook();
        // 准备导出参数
        ExportParams exportParams = new ExportParams(null, sheetName);
        exportParams.setStyle(ExcelExportStyler.class);
        // 导出Excel
        workbook = ExcelExportUtil.exportExcel(exportParams, entitys, dataList);

        Sheet sheet = workbook.getSheet(sheetName);
//        sheet.createFreezePane(1,2);
        Row headerRow = sheet.getRow(0);
        Cell cell = headerRow.getCell(0);
        CellStyle originalCellStyle = cell.getCellStyle();
        Font font = workbook.createFont();
        font.setColor(IndexedColors.RED.getIndex());
        CellStyle redCellStyle = workbook.createCellStyle();
        redCellStyle.cloneStyleFrom(originalCellStyle); // 复制原有样式避免覆盖其他设置
        redCellStyle.setFont(font);
        cell.setCellStyle(redCellStyle);

        // 写出到文件
        try (FileOutputStream fos = new FileOutputStream("109.xls")) {
            workbook.write(fos);
        }

        System.out.println("Excel文件导出成功！");
    }

    @Test
    @DisplayName("添加下拉数据")
    public void test3() throws IOException {

        // 导出Excel
        String fileName = "D:\\Users\\JNPF\\Desktop\\project\\java_learn\\jdk_character\\109.xls";
        @Cleanup Workbook workbook = new XSSFWorkbook(new FileInputStream(fileName));

        // 打开已生成的Excel文件并添加下拉列表
        String[] options = {"Option1", "Option2", "Option3"};

        addDataValidation(workbook, sheetName, 0, options);

        // 保存修改后的Excel文件
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            workbook.write(fos);
        } catch (Exception e) {

        }
    }

    /**
     * workbook增加数据验证
     *
     * @param workbook    工作簿
     * @param sheetName   工作表名称
     * @param columnIndex 列索引,从0开始
     * @param options     校验数据项
     */
    public void addDataValidation(Workbook workbook, String sheetName, Integer columnIndex, String[] options) {
        Sheet sheet = workbook.getSheet(sheetName);
        // 设置范围
        CellRangeAddressList addressList = new CellRangeAddressList(1, sheet.getLastRowNum(), columnIndex, columnIndex);
        // 添加数据验证助手
        DataValidationHelper helper = new XSSFDataValidationHelper((XSSFSheet) sheet);
        XSSFDataValidationConstraint constraint = (XSSFDataValidationConstraint) helper.createExplicitListConstraint(options);
        DataValidation validation = helper.createValidation(constraint, addressList);
        validation.setShowErrorBox(true);
        sheet.addValidationData(validation);
    }

    /**
     * workbook增加标题批注
     *
     * @param workbook    工作簿
     * @param sheetName   工作表名称
     * @param columnIndex 列索引,从0开始
     * @param comment     批注内容
     */
    public void addComment(Workbook workbook, String sheetName, int columnIndex, String comment) {
        Sheet sheet = workbook.getSheet(sheetName);
        Row headerRow = sheet.getRow(0);
        Cell headerCell = headerRow.getCell(columnIndex);
        // 判断是否存在批注
        Comment cellComment = headerCell.getCellComment();
        if (!Objects.isNull(cellComment)) {
            headerCell.setCellComment(null);
        }
        Drawing<?> drawing = sheet.createDrawingPatriarch();
        CreationHelper factory = workbook.getCreationHelper();
        ClientAnchor anchor = factory.createClientAnchor();
        Comment headerComment = drawing.createCellComment(anchor);
        RichTextString str = factory.createRichTextString(comment);
        headerComment.setString(str);
        headerCell.setCellComment(headerComment);
    }


    @Test
    @DisplayName("整合案例")
    public void test4() throws IOException {
        String name = "单行输入(name)";
        Pattern pattern = Pattern.compile("(.*)\\((.*?)\\)");
        Matcher matcher = pattern.matcher(name);

        if (matcher.find()) {
            System.out.println("匹配到的前缀: " + matcher.group(1)); // 输出：单行输入
            System.out.println("匹配到的内容: " + matcher.group(2)); // 输出：name
        }
    }

    Map<String,String> keyMap = new LinkedHashMap(){{
        put("organizeId","所属组织");
        put("fullName","岗位名称");
        put("enCode","岗位编码");
        put("type","岗位类型");
        put("sortCode","排序");
        put("enabledMark","状态");
        put("description","说明");
    }};

    @Test
    @DisplayName("测试")
    public void test5() throws IOException {
        System.out.println(keyMap);
    }
}
