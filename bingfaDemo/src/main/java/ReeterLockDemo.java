/**
 * 可重入锁也叫递归锁，
 * 指的是同一个线程外层函数获得锁之后，内层递归函数仍然能获取到该锁的代码
 * 在同一个线程在外层方法获取锁的时候，在进入内层方法会自动获取锁
 * 也就是说，线程可以进入任何一个它已经拥有的锁所同步的代码块
 */

/**
 * 资源类
 */
class Phone{
    public synchronized void sendSMS() throws Exception{
        System.out.println(Thread.currentThread().getName()+"\t invoke sendSMS()");
        //在同步方法中调用另一个同步方法
        sendEmail();
    }
    public synchronized void sendEmail() throws Exception{
        System.out.println(Thread.currentThread().getName()+"\t invoke sendEmail()");
    }
}
public class ReeterLockDemo {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(()->{
            try{
                phone.sendSMS();
            }catch (Exception e){
                e.printStackTrace();
            }
        },"t1").start();
        new Thread(()->{
            try{
                phone.sendSMS();
            }catch (Exception e){
                e.printStackTrace();
            }
        },"t2").start();
    }
}
/**
 * 最后输出为
 * t1	 invoke sendSMS() t1线程在外层方法获取锁的时候
 * t1	 invoke sendEmail() t1在进入内层方法会自动获取锁
 * t2	 invoke sendSMS()   t2线程在外层方法获取锁的时候
 * t2	 invoke sendEmail()  t2在进入内层方法会自动获取锁
 * 说明t1线程进入SMS的时候，拥有了一把锁，t2线程无法进入
 * 直到t1线程拿着锁，执行力SendEmail方法以后才释放锁
 */