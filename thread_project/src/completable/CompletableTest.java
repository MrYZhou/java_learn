package completable;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.concurrent.*;
import java.util.function.BiConsumer;

/**
 * 1.runAsyc 无返回值
 * 2. supplierAsyc 有返回值
 */
public class CompletableTest {


    public static void main(String[] args) {
        //runAsync
//        runAsyc();
        // supplyAsync
        supplierAsyc();
    }

    public static void runAsyc(){
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        CompletableFuture.runAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },executorService);

        System.out.println("main complete");
    }

    public static void supplierAsyc() {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 123;
        }, executorService);

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
