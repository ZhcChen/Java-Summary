import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolQuickDemo {
    public static void main(String[] args) throws InterruptedException {
        // 固定大小线程池，适合稳定负载场景
        ExecutorService pool = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 6; i++) {
            int taskId = i;
            pool.submit(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("task-" + taskId + " running on " + threadName);
            });
        }

        pool.shutdown();
        if (!pool.awaitTermination(3, TimeUnit.SECONDS)) {
            pool.shutdownNow();
        }
    }
}
