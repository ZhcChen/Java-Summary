public class CompensationSagaDemo {
    public static void main(String[] args) {
        boolean stockOk = reserveStock();
        if (!stockOk) {
            System.out.println("fail at stock step");
            return;
        }

        boolean paymentOk = pay();
        if (!paymentOk) {
            cancelStock();
            System.out.println("payment failed, stock compensated");
            return;
        }

        System.out.println("saga success");
    }

    private static boolean reserveStock() {
        System.out.println("reserve stock");
        return true;
    }

    private static boolean pay() {
        System.out.println("pay");
        return false; // simulate failure
    }

    private static void cancelStock() {
        System.out.println("cancel stock");
    }
}
