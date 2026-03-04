import java.util.ArrayList;
import java.util.List;

public class ValuePassingDemo {
    static class User {
        String name;

        User(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
        int num = 10;
        changeInt(num);
        System.out.println("int after call = " + num); // still 10

        User user = new User("Alice");
        changeUserName(user);
        System.out.println("user name after call = " + user.name); // Bob

        List<String> list = new ArrayList<>();
        replaceList(list);
        System.out.println("list size after call = " + list.size()); // 0
    }

    private static void changeInt(int value) {
        value = 99;
    }

    private static void changeUserName(User value) {
        value.name = "Bob";
    }

    private static void replaceList(List<String> value) {
        value = new ArrayList<>();
        value.add("new item");
    }
}
