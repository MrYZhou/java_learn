package thread;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 虚拟线程jdk19
 */
public class VirtualThreadTest {

    @Test
    @DisplayName("虚拟线程修改")
    public void test1() {
//        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
//
////        synchronized 改为 ReentrantLock，以减少虚拟线程被固定到平台线程。
//        final ReentrantLock lock = new ReentrantLock();
//        lock.lock();  // block until condition holds
//        try {
//            executorService.execute(() -> {
//                System.out.println(Thread.currentThread().getName());
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//        } finally {
//            lock.unlock();
//        }


    }

    public void m() {

    }
}
