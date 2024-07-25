package common;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OtherTest {

    @Test
    @DisplayName("输出文本块jdk15")
    public void test() {
        String sqlBlock = """
                SELECT
                    *
                FROM
                    sys_user0
                WHERE
                    user_name = 'abc'
                """;
        System.out.println(sqlBlock);
    }

    @Test
    @DisplayName("测试时间")
    public void test2() {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        String timeFormat = "YYYY/MM/dd";
        timeFormat = timeFormat.replaceAll("YYYY","yyyy");
        timeFormat = timeFormat.replaceAll("DD", "dd");
        // 定义格式化器，其中 "yyyy-MM-dd" 表示四位年份、两位月份（不足两位补零）、两位日期
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
        // 格式化当前日期为字符串
        String currentDateStr = currentDate.format(formatter);

        System.out.println("Current date as string (with leading zeros for month): " + currentDateStr);
    }


    @Test
    @DisplayName("测试")
    public void test23() throws IOException {
        File fileObj = new File("aa.txt");


        byte[] yozoFiles = new byte[]{1,2,3,4,5,6,7,8,9};
        try (FileOutputStream fos = new FileOutputStream(fileObj)) {
            // 将字节数组写入文件
            fos.write(yozoFiles);
            // 刷新缓冲区并确保所有数据已写入文件
            fos.flush();
            String randomTempFilePaht = "" + "temp"+".docx";
            File fileObjNew = new File(randomTempFilePaht);
            FileUtils.copyFile(fileObj,fileObjNew);


//            fileObj.renameTo(fileObjNew);


        }finally {
//            fileObj.delete();
        }

    }

}
