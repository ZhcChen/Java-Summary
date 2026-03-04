import java.math.BigDecimal;
import java.math.RoundingMode;

public class StringBigDecimalPitfallsDemo {
    public static void main(String[] args) {
        String s1 = "java";
        String s2 = "ja" + "va";
        System.out.println("string equals: " + s1.equals(s2));

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            builder.append(i);
        }
        System.out.println("string builder result: " + builder);

        BigDecimal wrong = new BigDecimal(0.1);
        BigDecimal right = new BigDecimal("0.1");
        System.out.println("new BigDecimal(0.1)  = " + wrong);
        System.out.println("new BigDecimal(\"0.1\") = " + right);

        BigDecimal amount = new BigDecimal("10");
        BigDecimal ratio = new BigDecimal("3");
        BigDecimal result = amount.divide(ratio, 2, RoundingMode.HALF_UP);
        System.out.println("10 / 3 with scale=2: " + result);
    }
}
