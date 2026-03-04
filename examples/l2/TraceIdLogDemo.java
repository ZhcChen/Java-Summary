import java.util.UUID;

public class TraceIdLogDemo {
    public static void main(String[] args) {
        String traceId = UUID.randomUUID().toString();
        log(traceId, "gateway", "request received");
        log(traceId, "order-service", "query order");
        log(traceId, "payment-service", "check payment status");
    }

    private static void log(String traceId, String service, String msg) {
        System.out.printf("traceId=%s service=%s msg=%s%n", traceId, service, msg);
    }
}
