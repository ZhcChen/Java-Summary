public class JvmMemoryRegionDemo {
    public static void main(String[] args) {
        int local = 42; // stack variable
        byte[] heapObj = new byte[1024]; // heap object
        System.out.println("local=" + local + ", heap size=" + heapObj.length);
        new JvmMemoryRegionDemo().instanceMethod();
    }

    private void instanceMethod() {
        System.out.println("method executes with this=" + this.getClass().getSimpleName());
    }
}
