package thread.completable;


import java.util.concurrent.*;
import java.util.function.BiConsumer;

/**
 * 多任务合并执行
 *  1.thenCombineAsync 有返回值
 *  2.thenAcceptBothAsync 无返回值
 *  3.runAfterBothAsync 不关心任务的返回，只负责在全部任务完毕后执行
 *  4.applyToEitherAsync 其中一个好了即可 ,有返回值
 *  5.acceptEitherAsync  其中一个好了即可 ,无返回值
 */
public class CoSolveTest {

    public static void main(String[] args) {
//        thenCombineAsync();
//        thenAcceptBothAsync();
//        runAfterBothAsync();
//        applyToEitherAsync();
        runAfterEitherAsync();
    }


    public static void thenCombineAsync()  {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "hello", executorService);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "larry", executorService);

        future1.thenCombineAsync(future2, (f1, f2) -> f1 + " " + f2, executorService).whenCompleteAsync(
                (s, throwable) -> {
                    System.out.println(s);
                    executorService.shutdown();
                }
        );
    }

    public static void thenAcceptBothAsync()  {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "hello", executorService);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "larry", executorService);

        future1.thenAcceptBothAsync(future2, (f1, f2) -> {
            System.out.println(f1 + " " + f2);
        }, executorService);
    }

    public static void runAfterBothAsync()  {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "hello", executorService);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "larry", executorService);

        future1.runAfterBothAsync(future2, () -> {
            System.out.println("全部任务执行完毕");
        }, executorService);
    }

    public static void applyToEitherAsync()  {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "hello", executorService);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "larry", executorService);

        future1.applyToEitherAsync(future2, (val) -> {
            System.out.println(val);
            return val;
        }, executorService);
    }

    public static void acceptEitherAsync()  {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "hello", executorService);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "larry", executorService);

        future1.acceptEitherAsync(future2, (val) -> {
            System.out.println(val);
        }, executorService);
    }

    public static void runAfterEitherAsync()  {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "hello", executorService);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "larry", executorService);

        future1.runAfterEitherAsync(future2, () -> {
            System.out.println("直接执行");
            executorService.shutdown();
        }, executorService);
    }
}
