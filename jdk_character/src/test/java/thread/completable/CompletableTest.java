package thread.completable;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class CompletableTest {


    @Test
    @DisplayName("runAsync无返回值")
    public void runAsync() {
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        try  {
//            for (int i = 0; i < 50; i++) {
//                CompletableFuture.runAsync(
//                        () -> {
//                            System.out.println(Thread.currentThread().getName());
//                        },
//                        executorService);
//            }
//        }finally {
//            executorService.close();
//        }
    }

    @Test
    @DisplayName("supplierAsync有返回值")
    public void supplierAsync() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        CompletableFuture<Integer> integerCompletableFuture =
                CompletableFuture.supplyAsync(
                        () -> {
                            try {
                                TimeUnit.SECONDS.sleep(3);
                                System.out.println(Thread.currentThread().getName());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return 123;
                        },
                        executorService);

        System.out.println("main complete");
        Integer integer = null;
        try {
            integer = integerCompletableFuture.get();

            // 执行回调
            integerCompletableFuture.whenCompleteAsync((integer1, throwable) -> System.out.println(integer1 + 123));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(integer);
    }
}
