import java.util.HashSet;
import java.util.Set;

public class MqIdempotentConsumerDemo {
    private static final Set<String> PROCESSED = new HashSet<>();

    public static void main(String[] args) {
        consume("msg-1", "order:1001");
        consume("msg-1", "order:1001");
        consume("msg-2", "order:1002");
    }

    private static void consume(String messageId, String payload) {
        if (!PROCESSED.add(messageId)) {
            System.out.println("skip duplicate: " + messageId);
            return;
        }
        System.out.println("consume payload=" + payload);
    }
}
