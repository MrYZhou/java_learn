package common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

}
