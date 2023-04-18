package thread.completable;

import java.util.concurrent.*;
import java.util.function.BiConsumer;

/**
 * 任务依赖执行
 *  1.thenApplyAsync 有返回值
 *  2.handleAsync 处理带异常，有返回值
 *  3.thenAcceptAsync 无返回值
 *  4.thenRunAsync 无返回值，继续执行,即使上个任务失败，不接收上个任务的返回值
 */
public class TaskRelationTest {

    public static void main(String[] args) {
//        thenApplyAsync();
//        handleAsync();
//        thenAcceptAsync();
        thenRunAsync();
    }
    public static void thenRunAsync() {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        CompletableFuture<Void> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 123;
        }, executorService).thenRunAsync(()->{
            System.out.println(Thread.currentThread().getName());
            System.out.println("默认执行");
        },executorService);

        System.out.println("main complete");

    }

    public static void thenAcceptAsync() {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        CompletableFuture<Void> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 123;
        }, executorService).thenAcceptAsync(val->{
            System.out.println(Thread.currentThread().getName());
            System.out.println(val+100);
        },executorService);

        System.out.println("main complete");

    }
    public static void thenApplyAsync() {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 123;
        }, executorService).thenApplyAsync(val->{
            System.out.println(Thread.currentThread().getName());
            System.out.println(val+100);
            return val;
        },executorService);

        System.out.println("main complete");
        Integer integer = null;
        try {
            integer = integerCompletableFuture.get();

            // 执行回调
            integerCompletableFuture.whenCompleteAsync(new BiConsumer<Integer, Throwable>() {
                @Override
                public void accept(Integer integer, Throwable throwable) {
                    System.out.println(integer+123);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(integer);
    }

    public static void handleAsync() {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 123;
        }, executorService).handleAsync((val,throwable)->{
            System.out.println(Thread.currentThread().getName());
            if(throwable!=null){
                System.out.println(throwable.getMessage());
            }else{
                val = val *10;
            }
            System.out.println(val);
            return val;
        },executorService);

        System.out.println("main complete");
        Integer integer = null;
        try {
            integer = integerCompletableFuture.get();

            // 执行回调
            integerCompletableFuture.whenCompleteAsync(new BiConsumer<Integer, Throwable>() {
                @Override
                public void accept(Integer integer, Throwable throwable) {
                    System.out.println(integer+123);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(integer);
    }
}
