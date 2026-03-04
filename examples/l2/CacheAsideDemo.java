import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheAsideDemo {
    private static final Map<String, String> CACHE = new ConcurrentHashMap<>();
    private static final Map<String, String> DB = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        DB.put("user:1", "v1");

        System.out.println(read("user:1"));
        System.out.println(read("user:1"));

        write("user:1", "v2");
        System.out.println(read("user:1"));
    }

    // 读流程：先查缓存，未命中再回源数据库并回填缓存
    private static String read(String key) {
        String cacheValue = CACHE.get(key);
        if (cacheValue != null) {
            return "cache:" + cacheValue;
        }

        String dbValue = DB.get(key);
        if (dbValue != null) {
            CACHE.put(key, dbValue);
        }
        return "db:" + dbValue;
    }

    // 写流程：先更新数据库，再删除缓存（避免脏缓存长期驻留）
    private static void write(String key, String value) {
        DB.put(key, value);
        CACHE.remove(key);
    }
}
