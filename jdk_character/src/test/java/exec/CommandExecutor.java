package exec;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandExecutor {

    public static void main(String[] args) {
        // 定义要执行的命令
        String javaExecutable = "C:\\Users\\JNPF\\.jdks\\azul-21\\bin\\java";
        String jarFilePath = "f:/jnpf-example-5.2.0-RELEASE.jar";

        // 构建命令参数列表
        ProcessBuilder processBuilder = new ProcessBuilder(
                javaExecutable,
                "--add-opens=java.base/java.lang=ALL-UNNAMED",
                "-jar",
                jarFilePath
        );

        // 设置工作目录（可选）
//        processBuilder.directory(new File("C:\\Users\\JNPF"));

        // 合并标准输出和标准错误流
        processBuilder.redirectErrorStream(true);

        try {
            // 启动进程
            Process process = processBuilder.start();

            // 读取进程输出
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            String line;
            System.out.println("命令执行输出：");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 等待进程执行完成
            int exitCode = process.waitFor();
            System.out.println("命令执行完成，退出码: " + exitCode);

        } catch (IOException e) {
            System.err.println("执行命令时发生IO异常: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.err.println("进程被中断: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
