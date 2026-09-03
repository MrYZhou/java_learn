package common;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class JdbcUrlParser {

    public static Map<String, String> parseJdbcUrlParams(String jdbcUrl) {
        Map<String, String> params = new HashMap<>();

        int questionMarkIndex = jdbcUrl.indexOf('?');
        if (questionMarkIndex == -1) {
            return params;
        }

        String queryString = jdbcUrl.substring(questionMarkIndex + 1);
        String[] pairs = queryString.split("&");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length >= 1) {
                try {
                    String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8.name());
                    String value = keyValue.length == 2 ?
                            URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8.name()) : "";
                    params.put(key, value);
                } catch (Exception e) {
                    // 如果解码失败，使用原始值
                    params.put(keyValue[0], keyValue.length == 2 ? keyValue[1] : "");
                }
            }
        }

        return params;
    }
}
