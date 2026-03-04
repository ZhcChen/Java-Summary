public class ExceptionPracticeDemo {
    public static void main(String[] args) {
        try {
            createOrder(0);
        } catch (BizException e) {
            System.out.println("business error: " + e.getMessage());
        }

        try {
            createOrder(3);
            System.out.println("order created");
        } catch (BizException e) {
            System.out.println("business error: " + e.getMessage());
        }
    }

    private static void createOrder(int count) {
        if (count <= 0) {
            throw new BizException("count must be > 0");
        }
    }

    static class BizException extends RuntimeException {
        BizException(String message) {
            super(message);
        }
    }
}
