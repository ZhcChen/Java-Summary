public class DataTypeAndWrapperDemo {
    public static void main(String[] args) {
        int primitive = 100;
        Integer wrapper = primitive; // autoboxing

        Integer a = 127;
        Integer b = 127;
        Integer c = 128;
        Integer d = 128;

        System.out.println("primitive=" + primitive + ", wrapper=" + wrapper);
        System.out.println("127 compare with == : " + (a == b)); // usually true (cache)
        System.out.println("128 compare with == : " + (c == d)); // usually false

        Integer nullable = null;
        try {
            int x = nullable; // auto-unboxing NPE
            System.out.println(x);
        } catch (NullPointerException e) {
            System.out.println("auto-unboxing null triggers NPE");
        }
    }
}
