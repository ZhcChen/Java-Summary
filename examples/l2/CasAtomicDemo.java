import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class CasAtomicDemo {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        int threads = 4;
        int perThread = 5000;
        CountDownLatch latch = new CountDownLatch(threads);

        for (int i = 0; i < threads; i++) {
            new Thread(() -> {
                for (int j = 0; j < perThread; j++) {
                    counter.incrementAndGet();
                }
                latch.countDown();
            }).start();
        }

        latch.await();
        System.out.println("counter=" + counter.get());
    }
}
