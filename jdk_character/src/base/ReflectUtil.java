package base;


import java.lang.reflect.Method;

public class ReflectUtil {


    public static Method getAccessibleMethod(final Class<?> clazz, final String methodName, final Class<?>... parameterTypes) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException e) {// NOSONAR
            // Method不在当前类定义,继续向上转型
        }
        return null;
    }

    public static Object invokeFlowMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes, final Object[] args) {
        Method method = getAccessibleMethod(obj.getClass(), methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
        }
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            return false;
        }
    }
}

