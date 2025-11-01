package exec;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


import java.util.function.Consumer;

public class ExecUtil {

    /**
     * 异步执行命令（带回调通知）
     * @param command 完整命令字符串
     * @param timeout 超时时间（毫秒）
     * @param callback 完成回调函数
     * @return CompletableFuture用于进一步处理
     */
    public static CompletableFuture<ExecResult> execAsync(String command, long timeout, Consumer<ExecResult> callback) {
        return execAsync(command.split(" "), timeout, callback);
    }

    /**
     * 异步执行命令（带回调通知）
     * @param commandParts 命令各部分
     * @param timeout 超时时间（毫秒）
     * @param callback 完成回调函数
     * @return CompletableFuture用于进一步处理
     */
    public static CompletableFuture<ExecResult> execAsync(String[] commandParts, long timeout, Consumer<ExecResult> callback) {
        return CompletableFuture.supplyAsync(() -> {
            ProcessBuilder processBuilder = new ProcessBuilder(commandParts);
            Process process = null;

            try {
                process = processBuilder.start();

                // 异步读取输出流
                CompletableFuture<String> outputFuture = readStreamAsync(process.getInputStream());
                CompletableFuture<String> errorFuture = readStreamAsync(process.getErrorStream());

                boolean completed = process.waitFor(timeout, TimeUnit.MILLISECONDS);

                String output = outputFuture.get(1000, TimeUnit.MILLISECONDS);
                String error = errorFuture.get(1000, TimeUnit.MILLISECONDS);

                if (!completed) {
                    process.destroy();
                    if (process.isAlive()) {
                        process.destroyForcibly();
                    }
                    ExecResult result = new ExecResult(-1, output, error + "Process timeout after " + timeout + "ms");
                    if (callback != null) callback.accept(result);
                    return result;
                }

                int exitCode = process.exitValue();
                ExecResult result = new ExecResult(exitCode, output, error);
                if (callback != null) callback.accept(result);
                return result;

            } catch (Exception e) {
                ExecResult result = new ExecResult(-1, "", "Execution failed: " + e.getMessage());
                if (callback != null) callback.accept(result);
                return result;
            } finally {
                if (process != null && process.isAlive()) {
                    process.destroy();
                }
            }
        });
    }

    /**
     * 异步执行命令（不带回调）
     * @param command 完整命令字符串
     * @param timeout 超时时间（毫秒）
     * @return CompletableFuture
     */
    public static CompletableFuture<ExecResult> execAsync(String command, long timeout) {
        return execAsync(command, timeout, null);
    }

    /**
     * 异步读取流
     * @param inputStream 输入流
     * @return 读取结果的CompletableFuture
     */
    private static CompletableFuture<String> readStreamAsync(InputStream inputStream) {
        return CompletableFuture.supplyAsync(() -> {
            StringBuilder content = new StringBuilder();
            System.setProperty("file.encoding","UTF-8");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    content.append(line).append("\n");
                }
            } catch (IOException e) {
                content.append("Error reading stream: ").append(e.getMessage()).append("\n");
            }
            return content.toString();
        });
    }

    /**
     * 异步执行Java程序
     * @param javaHome Java home路径
     * @param jarPath jar文件路径
     * @param vmArgs VM参数数组
     * @param timeout 超时时间（毫秒）
     * @param callback 完成回调函数
     * @return CompletableFuture
     */
    public static CompletableFuture<ExecResult> execJavaProgramAsync(String javaHome, String jarPath, String[] vmArgs, long timeout, Consumer<ExecResult> callback) {
        String javaExecutable = javaHome.endsWith("/") || javaHome.endsWith("\\") ?
                javaHome + "bin/java" : javaHome + "/bin/java";

        // 构建命令数组
        String[] command = new String[3 + (vmArgs != null ? vmArgs.length : 0)];
        command[0] = javaExecutable;

        int index = 1;
        if (vmArgs != null) {
            for (String vmArg : vmArgs) {
                command[index++] = vmArg;
            }
        }

        command[index++] = "-jar";
        command[index] = jarPath;

        return execAsync(command, timeout, callback);
    }




}
