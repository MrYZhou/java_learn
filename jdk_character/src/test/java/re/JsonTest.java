import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;


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

    private static String getJsonValue(String jsonStr, String keyStr) {
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        String[] keys = keyStr.split("\\.");
        JSONObject temp = jsonObject;
        for (int i = 0; i < keys.length - 1; i++) {
            temp = temp.getJSONObject(keys[i]);
        }
        return temp.getString(keys[keys.length - 1]);
    }

    @Test
    public void test3() {
        String jsonStr = "{\"a\":{\"b\":\"1\"}}";
        String keyStr = "a.b";
        String value = this.getChineseByCode(jsonStr, keyStr);

        System.out.println(value);

    }

    private static Map<String, Object> flatten(Map<String, Object> map, String prefix) {
        Map<String, Object> flatMap = MapUtil.newHashMap();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = (prefix != null) ? prefix + "." + entry.getKey() : entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Map) {
                Map<String, Object> nestedMap = flatten((Map<String, Object>) value, key);
                flatMap.putAll(nestedMap);
            } else if (value instanceof JSONUtil) {
                String jsonString = JSONUtil.toJsonStr(value);
                flatMap.put(key, jsonString);
            } else {
                flatMap.put(key, value);
            }
        }
        return flatMap;
    }

    private static String toJSONString(Object obj) {
        if (obj instanceof JSONUtil) {
            return JSONUtil.toJsonStr(JSONUtil.parse(obj));
        } else {
            return JSONUtil.toJsonStr(obj);
        }
    }

    @Test
    @DisplayName("转扁平json")
    public void test4() {
        String jsonStr = "{\"a\":{\"b\":\"1\"}}";
        Map map1 = JSONObject.parseObject(jsonStr, Map.class);
        Map<String, Object> flatMap = flatten(map1, null);
        System.out.println(toJSONString(flatMap));
    }

    private String getChineseByCode(String jsonStr, String findKey) {
        try {
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            String[] list = findKey.split("\\.");
            JSONObject temp = (JSONObject) jsonObject.get(list[0]);
            for (int i = 1; i < list.length; i++) {
                Object res = temp.get(list[i]);
                if (i == list.length - 1) {
                    return res.toString();
                }
                temp = (JSONObject) res;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }


}
