package common;

import cn.hutool.core.date.StopWatch;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandleExample {

    public static void main(String[] args) throws Throwable {

        MethodHandles.Lookup lookup = MethodHandles.lookup();

        // 定义方法类型
        MethodType methodType = MethodType.methodType(void.class, String.class,String.class);

        // 获取 MethodHandle，指定方法名、方法类型
        MethodHandle methodHandle = lookup.findVirtual(MethodHandleExample.class, "printMessage", methodType);

        // 创建实例
        MethodHandleExample example = new MethodHandleExample();

        // MethodHandle调用 printMessage 方法
        methodHandle.invokeExact(example, "Hello"," World!");
    }

    // 示例方法
    public void printMessage(String message,String message2) {
        System.out.println(message+message2);
    }
}
