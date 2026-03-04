public class VolatileVisibilityDemo {
    private static volatile boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            long count = 0;
            while (running) {
                count++;
            }
            System.out.println("worker stopped, count=" + count);
        });

        worker.start();
        Thread.sleep(200);
        running = false;
        worker.join();
        System.out.println("main exits");
    }
}
