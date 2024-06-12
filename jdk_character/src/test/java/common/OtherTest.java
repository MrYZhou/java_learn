package common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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


}
