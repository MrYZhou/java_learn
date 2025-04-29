package common;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class ReflectTest {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?>[] aClass = new Class[]{Map.class};
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("jdd", 2);
        Object[] aObject = new Object[]{map};
        Method localTask1 = ReflectTest.class.getDeclaredMethod("localTask", Map.class);
//        localTask1.setAccessible(true);
//        localTask1.invoke(new ReflectTest(),map);
        Object o = ReflectTest.class;
//        Method localTask = ReflectTest.class.getDeclaredMethod(methodName, parameterTypes);
        Object localTask = ReflectUtil.invokeFlowMethod(1, "localTask", aClass, aObject);
//        System.out.println(localTask1);
        System.out.println(localTask);

    }

    public Boolean localTask(Map<String, Object> params) {

        System.out.println(params);
        return true;
    }

    @Test
    @DisplayName("jsonarray的转换")
    public void testparse() throws InvocationTargetException, IllegalAccessException {
        String[] greetings = {"Hello", "Hi", "Hey", "Greetings"};
        JSONArray jsonArray = new JSONArray(Arrays.asList(greetings));
        int length = jsonArray.size();
        Page page = new Page();
        page.setSelectKey(greetings);

        Class<?> type = page.getSelectKey().getClass().getComponentType();

        // 获取数组类型
        Class<?> arrayType = null;
        Object targetArray = null;
        boolean isArray = jsonArray instanceof JSONArray ? true : false;
        if (isArray) {
            arrayType = Array.newInstance(type, 0).getClass();
            targetArray = Array.newInstance(type, length);  // 动态创建数组实例
            for (int i = 0; i < length; i++) {
                Object element = jsonArray.get(i);
                // 处理不同类型转换逻辑
                if (type == String.class) {
                    Array.set(targetArray, i, element != null ? element.toString() : null);
                }
                else if (type == Integer.class || type == int.class) {
                    Array.set(targetArray, i, element != null ? Integer.valueOf(Integer.parseInt(element.toString())) : (type == int.class ? 0 : null));
                }
                else if (type == Boolean.class || type == boolean.class) {
                    String value = element != null ? element.toString() : "";
                    boolean boolValue = "true".equalsIgnoreCase(value);
                    Array.set(targetArray, i, boolValue);
                }
                else if (type == Double.class || type == double.class) {
                    Array.set(targetArray, i, element != null ? Double.valueOf(Double.parseDouble(element.toString())) : (type == double.class ? 0.0 : null));
                }
                else if (type == Long.class || type == long.class) {
                    Array.set(targetArray, i, element != null ? Long.valueOf(Long.parseLong(element.toString())) : (type == long.class ? 0L : null));
                }
            }
        }


        try {
            Method method = page.getClass().getMethod("setSelectKey", arrayType);
            method.invoke(page, targetArray);
            System.out.println(page);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

    }

    @Data
    class Page {
        String[] selectKey;
    }
}
