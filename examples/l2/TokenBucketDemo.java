public class TokenBucketDemo {
    private int tokens;
    private final int capacity;

    public TokenBucketDemo(int capacity) {
        this.capacity = capacity;
        this.tokens = capacity;
    }

    public synchronized boolean allow() {
        if (tokens > 0) {
            tokens--;
            return true;
        }
        return false;
    }

    public synchronized void refill(int count) {
        tokens = Math.min(capacity, tokens + count);
    }

    public static void main(String[] args) {
        TokenBucketDemo bucket = new TokenBucketDemo(3);
        for (int i = 0; i < 5; i++) {
            System.out.println("req" + i + " allowed=" + bucket.allow());
        }
        bucket.refill(2);
        System.out.println("after refill allowed=" + bucket.allow());
    }
}
