import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolSizingDemo {
    public static void main(String[] args) throws InterruptedException {
        int cpuCores = Runtime.getRuntime().availableProcessors();

        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(4);
        RejectedExecutionHandler rejectPolicy = new ThreadPoolExecutor.CallerRunsPolicy();

        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                cpuCores,
                cpuCores * 2,
                30,
                TimeUnit.SECONDS,
                queue,
                rejectPolicy);

        for (int i = 0; i < 20; i++) {
            int taskId = i;
            pool.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(120);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("task=" + taskId + " thread=" + Thread.currentThread().getName());
            });

            // 观察运行时指标是线程池调优的第一步
            System.out.printf("active=%d, queue=%d, completed=%d%n",
                    pool.getActiveCount(), pool.getQueue().size(), pool.getCompletedTaskCount());
        }

        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);
    }
}
