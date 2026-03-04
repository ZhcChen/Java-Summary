import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericsWildcardDemo {
    public static void main(String[] args) {
        List<Integer> ints = Arrays.asList(1, 2, 3);
        List<Number> numbers = new ArrayList<>();

        printSum(ints); // ? extends Number
        addIntegers(numbers); // ? super Integer
        System.out.println("numbers=" + numbers);
    }

    private static void printSum(List<? extends Number> list) {
        double sum = 0;
        for (Number n : list) {
            sum += n.doubleValue();
        }
        System.out.println("sum=" + sum);
        // list.add(1); // compile error: extends list is producer
    }

    private static void addIntegers(List<? super Integer> list) {
        list.add(10);
        list.add(20);
    }
}
