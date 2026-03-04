import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CollectionSelectionDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        System.out.println("list[1]=" + list.get(1));

        Set<String> set = new HashSet<>();
        set.add("user-1");
        set.add("user-1");
        System.out.println("set size=" + set.size());

        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("b", 2);
        hashMap.put("a", 1);

        Map<String, Integer> treeMap = new TreeMap<>();
        treeMap.putAll(hashMap);

        System.out.println("hashMap order=" + hashMap.keySet());
        System.out.println("treeMap order=" + treeMap.keySet());
    }
}
