import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//单线程的情况下，ArrayList是不会有问题的

/**
 * public class ArrayListNotSafeDemo {
 *     public static void main(String[] args) {
 *         List<String> list = new ArrayList<>();
 *         list.add("a");
 *         list.add("b");
 *         list.add("c");
 *         for(String element:list){
 *             System.out.println(element);
 *         }
 *
 *     }
 * }
 */



//多线程则不然，是因为在写操作的情况下，为了保证并发性，没有添加Synchronized关键字，
//在并发写的时候就会出现问题
public class ArrayListNotSafeDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
//1.解决方法 用vector，因为vector里面加了synchronized关键字
//2.Collections.synchronized()
//3.采用JUC里面的方法CopyOnWriteArrayList  读写分离 的思想