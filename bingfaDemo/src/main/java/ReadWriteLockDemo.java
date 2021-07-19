import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

////资源类
//class MyCache{
//    private volatile Map<String,Object> map = new HashMap<>();
//    /**
//     * 定义写操作
//     * 满足原子+独占
//     */
//    public void put(String key,Object value){
//        System.out.println(Thread.currentThread().getName()+"\t 正在写入："+key);
//        try{
//            TimeUnit.MILLISECONDS.sleep(300);
//        }catch(InterruptedException e){
//            e.printStackTrace();
//        }
//        map.put(key,value);
//        System.out.println(Thread.currentThread().getName()+"\t 写入完成");
//    }
//    public void get(String key){
//        System.out.println(Thread.currentThread().getName()+"\t 正在读取：");
//        try{
//            TimeUnit.MILLISECONDS.sleep(300);
//        }catch(InterruptedException e){
//            e.printStackTrace();
//        }
//        Object value = map.get(key);
//        System.out.println(Thread.currentThread().getName()+"\t 读取完成" + value);
//    }
//
//}
//
//
//
//
//public class ReadWriteLockDemo {
//    public static void main(String[] args) {
//        MyCache myCache = new MyCache();
//        //线程操作资源类，5个线程写
//        for(int i=0;i<5;i++){
//            final  int tempInt = i;
//            new Thread(()->{
//                myCache.put(tempInt+"",tempInt+"");
//            },String.valueOf(i)).start();
//        }
//        //5个线程读
//        for(int i=0;i<5;i++){
//            final  int tempInt = i;
//            new Thread(()->{
//                myCache.get(tempInt+"");
//            },String.valueOf(i)).start();
//        }
//
//    }
//}

/**
 * 输出为
 * 3	 正在读取：
 * 2	 正在读取：
 * 4	 正在读取：
 * 0	 正在读取：
 * 1	 正在读取：
 * 4	 正在写入：4
 * 0	 正在写入：0
 * 1	 正在写入：1
 * 2	 正在写入：2
 * 3	 正在写入：3
 * 4	 写入完成
 * 2	 写入完成
 * 0	 写入完成
 * 1	 写入完成
 * 2	 读取完成null
 * 3	 写入完成
 * 3	 读取完成null
 * 0	 读取完成0
 * 4	 读取完成null
 * 1	 读取完成null
 * 在写入的时候，写操作都没其它线程打断了，这就造成了还没有写完，其它线程又开始写，造成数据不一致
 * 那么这个时候可以通过读写锁来解决问题
 */

//资源类
class MyCache{
    private volatile Map<String,Object> map = new HashMap<>();
    /**
     * 定义写操作
     * 满足原子+独占
     */
    /**
     * 创建一个读写锁
     * @param key
     * @param value
     */
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    /**
     * 定义写操作
     * @param key
     * @param value
     */
    public void put(String key,Object value){
        //创建一个写锁
        rwLock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t 正在写入："+key);
            try{
                //模拟网络拥堵0.3秒
                TimeUnit.MILLISECONDS.sleep(300);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t 写入完成");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //写锁释放
            rwLock.writeLock().unlock();
        }
    }


    /**
     * 获取
     * @param key
     */
    public void get(String key){
        //读锁
        rwLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t 正在读取：");
            try{
                //模拟网络拥堵，延迟0.3秒
                TimeUnit.MILLISECONDS.sleep(300);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            Object value = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取完成"+ value);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //读锁释放
            rwLock.readLock().unlock();
        }
    }
    //清空缓存
    public void clean(){
        map.clear();
    }

}




public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        //线程操作资源类，5个线程写
        for(int i=1;i<=5;i++){
            final  int tempInt = i;
            new Thread(()->{
                myCache.put(tempInt+"",tempInt+"");
            },String.valueOf(i)).start();
        }
        //5个线程读
        for(int i=1;i<=5;i++){
            final  int tempInt = i;
            new Thread(()->{
                myCache.get(tempInt+"");
            },String.valueOf(i)).start();
        }

    }
}

/**
 * 5	 正在读取：
 * 4	 正在读取：
 * 2	 正在读取：
 * 1	 正在读取：
 * 3	 正在读取：
 * 5	 读取完成null
 * 4	 读取完成null
 * 3	 读取完成null
 * 2	 读取完成null
 * 1	 读取完成null
 * 4	 正在写入：4
 * 4	 写入完成
 * 1	 正在写入：1
 * 1	 写入完成
 * 3	 正在写入：3
 * 3	 写入完成
 * 2	 正在写入：2
 * 2	 写入完成
 * 5	 正在写入：5
 * 5	 写入完成
 * 由最后结果可以看到，写入操作是一个一个线程进行执行的，并且中间不会被打断，
 * 而读操作是5个线程进入然后并发进行读取
 */
