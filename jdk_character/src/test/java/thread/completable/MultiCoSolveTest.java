package thread.completable;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiCoSolveTest {

    @Test
    @DisplayName("allOf全部完成")
    public void allOf() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "apple", executorService);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "orange", executorService);

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "banana", executorService);

        List<CompletableFuture<String>> list = new ArrayList<CompletableFuture<String>>() {{
            add(future1);
            add(future2);
            add(future3);
        }};
        CompletableFuture[] futures = list.toArray(new CompletableFuture[]{});
        CompletableFuture<Void> all = CompletableFuture.allOf(futures);
        executorService.shutdown();

    }

    @Test
    @DisplayName("anyOf任意一个完成")
    public void anyOf() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "apple", executorService);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "orange", executorService);

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "banana", executorService);

        List<CompletableFuture<String>> list = new ArrayList<CompletableFuture<String>>() {{
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
