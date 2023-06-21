import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;


public class JsonTest {
    @Test
    @DisplayName("测试多级key")
    public void testmultiKey() {
        JSONObject jsonObject = new JSONObject();

        putObject(jsonObject, "a.b.c", 1);

        putObject(jsonObject, "a.c", 2);

        putObject(jsonObject, "c", 3);

        System.out.println(jsonObject);
    }

    public void putObject(JSONObject jsonObject, String key, Object value) {

        String[] keys = key.split("\\.");
        JSONObject temp = jsonObject;
        for (int i = 0; i < keys.length - 1; i++) {
            String k = keys[i];
            if (!temp.containsKey(k)) {
                temp.put(k, new JSONObject());
            }
            temp = temp.getJSONObject(k);
        }

        temp.put(keys[keys.length - 1], value);
    }

    @Test
    public void test2() {
        String filePath = "zh-CN.json";
        try {
            JSONObject jsonObject = JSONObject.parseObject(new String(Files.readAllBytes(Paths.get(filePath))));
            System.out.println(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
