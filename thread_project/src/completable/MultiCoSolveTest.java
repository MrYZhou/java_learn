package completable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多任务合并执行
 * 1.allOf 全部完成
 * 2.anyOf 任意一个完成
 */
public class MultiCoSolveTest {

    public static void main(String[] args) {

//        allOf();
        anyOf();
    }



    public static void allOf()  {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "apple", executorService);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "orange", executorService);

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "banana", executorService);

        List<CompletableFuture<String>> list = new ArrayList<>(){{
            add(future1);
            add(future2);
            add(future3);
        }} ;
        CompletableFuture[] futures = list.toArray(new CompletableFuture[]{});
        CompletableFuture<Void> all = CompletableFuture.allOf(futures);

    }

    public static void anyOf()  {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "apple", executorService);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "orange", executorService);

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "banana", executorService);

        List<CompletableFuture<String>> list = new ArrayList<>(){{
            add(future1);
            add(future2);
            add(future3);
        }} ;
        CompletableFuture[] futures = list.toArray(new CompletableFuture[]{});
        CompletableFuture<Object> objectCompletableFuture = CompletableFuture.anyOf( futures);
        Object o = null;
        try {
            o = objectCompletableFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(o);
        executorService.shutdown();
    }
}
