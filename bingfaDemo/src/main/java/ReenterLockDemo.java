import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Phone1 implements Runnable{
    Lock lock = new ReentrantLock();

    /**
     * set进去的时候就加锁，调用set方法的时候能否访问另外一个加锁的set方法
     */
    public void getLock(){
        //lock.lock();
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t get lock");
            setLock();

        }finally {
            lock.unlock();
            //lock.unlock();
        }
    }
    public void setLock(){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t set lock");
        }finally {
            lock.unlock();
        }
    }
    @Override
    public void run(){
        getLock();
    }
}
public class ReenterLockDemo {
    public static void main(String[] args) {
        Phone1 phone1 = new Phone1();
        /**
         * 因为Phone1实现了Runnable接口
         */
        Thread t3 = new Thread(phone1,"t3");
        Thread t4 = new Thread(phone1,"t4");
        t3.start();
        t4.start();

    }
}
/**
 * 输出为
 * t3	 get lock
 * t3	 set lock
 * t4	 get lock
 * t4	 set lock
 * 在外层方法获取锁之后
 * 线程能够直接进入里层
 */
