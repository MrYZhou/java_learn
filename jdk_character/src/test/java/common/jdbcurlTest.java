package common;

import org.junit.jupiter.api.Test;
import java.util.Map;
public class jdbcurlTest {
    @Test
    public void test(){
        String jdbcUrl = "jdbc:mysql://localhost:3306/testdb?user=root&password=123456&useSSL=false&serverTimezone=UTC&dbMode=mysql";

        Map<String, String> params = JdbcUrlParser.parseJdbcUrlParams(jdbcUrl);



        // 获取特定参数
        String user = params.get("dbMode");
        String password = params.get("password");
        String useSSL = params.get("useSSL");

        System.out.println("User: " + user);
        System.out.println("Password: " + password);
        System.out.println("Use SSL: " + useSSL);
    }
}
