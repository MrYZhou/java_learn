

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflectTest {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?>[] aClass = new Class[]{Map.class};
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("jdd",2);
        Object[] aObject = new Object[]{map};
        Method localTask1 = ReflectTest.class.getDeclaredMethod("localTask", Map.class);
//        localTask1.setAccessible(true);
//        localTask1.invoke(new ReflectTest(),map);
    Object o = ReflectTest.class;
//        Method localTask = ReflectTest.class.getDeclaredMethod(methodName, parameterTypes);
        Object localTask = base.ReflectUtil.invokeFlowMethod(1, "localTask", aClass, aObject);
//        System.out.println(localTask1);
        System.out.println(localTask);

    }

    public Boolean localTask(Map<String,Object> params){

        System.out.println(params);
        return true;
    }
}
