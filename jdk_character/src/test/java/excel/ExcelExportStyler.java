package excel;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.entity.params.ExcelForEachParams;
import cn.afterturn.easypoi.excel.export.styler.IExcelExportStyler;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

public class ExcelExportStyler implements IExcelExportStyler {

    private static final short FONT_SIZE_TWELVE = 12;

    private Workbook workbook;

    public ExcelExportStyler(Workbook workbook) {
        this.workbook = workbook;
    }

    @Override
    public CellStyle getHeaderStyle(short color) {
        CellStyle style = getBaseCellStyle(workbook);
        return style;
    }


    @Override
    public CellStyle getTitleStyle(short color) {
        CellStyle style = getBaseCellStyle(workbook);
        // 背景颜色
        style.setFont(getFont(workbook,FONT_SIZE_TWELVE,false));
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private void setBlackBorder(CellStyle style) {
        // 设置边框样式为细线
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        // 设置单元格颜色
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
    }


    @Override
    public CellStyle getStyles(boolean parity, ExcelExportEntity entity) {
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        this.setBlackBorder(style);
        // 单元格内容水平居中
        style.setAlignment(HorizontalAlignment.CENTER);
        // 单元格内容垂直居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }


    @Override
    public CellStyle getStyles(Cell cell, int dataRow, ExcelExportEntity entity, Object obj, Object data) {
        return getStyles(true, entity);
    }

    @Override
    public CellStyle getTemplateStyles(boolean isSingle, ExcelForEachParams excelForEachParams) {
        return null;
    }

    /**
     * 单元格样式
     *
     * @return
     */
    private CellStyle getBaseCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        // 设置单元格底部边框为细线
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        // 设置单元格颜色
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        // 单元格内容水平居中
        style.setAlignment(HorizontalAlignment.CENTER);
        // 单元格内容垂直居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        // 单元格内的文本超出单元格宽度时自动换行
        style.setWrapText(true);
        return style;
    }

    /**
     * 字体修改
     *
     * @param size 字号
     * @param isBold 加粗
     * @return
     */
    private Font getFont(Workbook workbook, short size, boolean isBold) {
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setBold(isBold);
        font.setFontHeightInPoints(size);
        return font;
    }
}
