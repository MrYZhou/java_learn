package excel.delaycompute;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class Index {
    @Test
    public void test() {
        Workbook workbook =new XSSFWorkbook();
        ExcelHelper compute = new ExcelHelper(workbook);
        compute.addPreHandle((data,params)->{
            System.out.println(1);
        });
        compute.addPreHandle((data,params)->{
            System.out.println(2);
        });
        compute.doPreHandle();
        compute.doPostHandle();
    }
    @FunctionalInterface
    interface ExcelFunction {
        void apply(Index.ExcelHelper compute, Map<String, Object> params);
    }

    @Data
    @NoArgsConstructor
    public static class ExcelHelper{
        private Workbook workbook;
        private List<ExcelFunction> list = new ArrayList<>();
        private List<ExcelFunction> listPost = new ArrayList<>();

        public ExcelHelper(Workbook workbook) {
            this.workbook = workbook;
            this.init();
        }

        private void init() {
            addPreHandle(this::addComment);
            addPostHandle((data,params)->{
                System.out.println(2);
            });
        }
        public void addComment(ExcelHelper compute, Map<String, Object> params) {
            System.out.println(3);
        }

        public void doPreHandle(){
            list.forEach(item->item.apply(this,new HashMap<>()));
        }
        private ExcelHelper addPreHandle(ExcelFunction item){
            list.add(item);
            return this;
        }
        public void doPostHandle(){
            listPost.forEach(item->item.apply(this,new HashMap<>()));
        }
        private ExcelHelper addPostHandle(ExcelFunction item){
            listPost.add(item);
            return this;
        }

        // excel增强
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

    }
}
