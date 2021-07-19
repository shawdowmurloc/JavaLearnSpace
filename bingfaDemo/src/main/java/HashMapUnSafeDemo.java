import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 在多线程的情况下，HashMap也是非线程安全的
 *
 */
public class HashMapUnSafeDemo {
    public static void main(String[] args) {
        Map<String,String> map = new ConcurrentHashMap<>();
        for(int i=0;i<10;i++){
            new Thread(()->{
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0,8));
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }
}
/**
 * 解决方法
 * 1.使用Collections.synchronizedMap(new HashMap<>())
 * 2.使用ConcurrentHashMap
 */
