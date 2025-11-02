package exec;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ExecTest {
    @Test
    @DisplayName("运行java")
    public void test2(){
        System.out.println("开始异步执行Java版本检查...");

        // 使用回调方式 - 执行完成后自动通知
        ExecUtil.execAsync("la help", 0, result -> {
            System.out.println("=== 执行完成通知 ===");
            System.out.println(result);
            System.out.println("=== 通知结束 ===\n");

            // 可以在这里继续执行其他任务
            System.out.println("继续执行其他业务逻辑...");
        });

        // 主线程可以继续执行其他任务，不会被阻塞
        System.out.println("主线程继续执行，不会等待进程完成...");

        // 保持程序运行以便看到回调结果
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    @DisplayName("运行java")
    public void test() throws ExecutionException, InterruptedException {
        // java执行器，端口检测
//        // 测试执行Java程序
        String javaHome = System.getProperty("java.home");
        String[] vmArgs = {"-Xmx512m", "-Xms256m", "-Dconfig.file=app.properties","-Dserver.port=8084"};
        ExecUtil.execJavaProgramAsync(javaHome,
                "C:\\Users\\lg\\Desktop\\cloud\\book\\borrow-server\\target\\borrow-0.0.1-SNAPSHOT.jar",
                vmArgs,
                10000, result -> {
                    System.out.println(result);
                }
        );
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    @Test
    public  void test3(){
        String javaHome = System.getProperty("java.home");
        String jdkPath = javaHome.substring(0, javaHome.lastIndexOf(File.separator));
        System.out.println(javaHome);

    }
}
