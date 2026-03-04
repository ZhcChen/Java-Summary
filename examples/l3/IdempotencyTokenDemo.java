import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IdempotencyTokenDemo {
    private static final Map<String, Long> PROCESSED = new ConcurrentHashMap<>();
    private static final long TTL_MILLIS = 60_000;

    public static void main(String[] args) {
        String token = "order-create:1001";

        System.out.println(handleRequest(token)); // accepted
        System.out.println(handleRequest(token)); // duplicate

        PROCESSED.put(token, Instant.now().minusMillis(TTL_MILLIS + 1).toEpochMilli());
        cleanupExpiredTokens();
        System.out.println(handleRequest(token)); // accepted again after ttl
    }

    private static String handleRequest(String token) {
        cleanupExpiredTokens();
        long now = Instant.now().toEpochMilli();
        Long existing = PROCESSED.putIfAbsent(token, now);
        if (existing != null) {
            return "duplicate request blocked";
        }

        // 业务处理逻辑
        return "accepted";
    }

    private static void cleanupExpiredTokens() {
        long now = Instant.now().toEpochMilli();
        PROCESSED.entrySet().removeIf(e -> now - e.getValue() > TTL_MILLIS);
    }
}
