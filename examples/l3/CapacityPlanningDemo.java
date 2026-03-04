public class CapacityPlanningDemo {
    public static void main(String[] args) {
        long dailyRequests = 8_640_000; // 100 req/s average
        int peakFactor = 8;
        int singleNodeQps = 1200;
        double redundancy = 1.3;

        long peakQps = dailyRequests / (24 * 3600) * peakFactor;
        int nodes = (int) Math.ceil((peakQps / (double) singleNodeQps) * redundancy);

        System.out.println("peakQps=" + peakQps);
        System.out.println("required nodes=" + nodes);
    }
}
