import org.w3c.dom.ls.LSOutput;

import javax.print.DocFlavor;
import java.awt.*;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 运行后我们发现，线程2可以成功进行修改，这就是ABA问题
 */

//public class ABADemo {
//
//    /**
//     * 普通的原子引用包装类t
//     */
//    static AtomicReference<Integer> atomicReference  = new AtomicReference<>(100);
//
//    public static void main(String[] args) {
//        new Thread(() ->{
//            //ABA
//            atomicReference.compareAndSet(100,101);
//            atomicReference.compareAndSet(101,100);
//        },"t1").start();
//
//        new  Thread(() ->{
//            try{
//                //睡眠2秒，保证t1线程完成aba操作
//                TimeUnit.SECONDS.sleep(2);
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }
//            System.out.println(atomicReference.compareAndSet(100,2019)+"\t"+atomicReference.get());
//        },"t2").start();
//
//    }
//}


//解决ABA问题
//1、可以引入版本号机制，类似于时间戳的概念
//2、时间戳原子引用，在每次更新的时候需要比较期望值和当前值以及期望版本号和当前版本号
public  class  ABADemo{
    static AtomicReference<Integer> atomicReference  = new AtomicReference<>(100);
    static AtomicStampedReference atomicStampedReference  = new AtomicStampedReference<>(100,1);

    public static void main(String[] args) {
//        System.out.println("以下是ABA问题的产生++++++++++++++");
//        //t1线程
//        new Thread(() ->{
//            //ABA
//            atomicReference.compareAndSet(100,101);
//            atomicReference.compareAndSet(101,100);
//        },"t1").start();
//
//        //t2线程
//        new  Thread(() ->{
//            try{
//                //睡眠2秒，保证t1线程完成aba操作
//                TimeUnit.SECONDS.sleep(2);
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }
//            System.out.println(atomicReference.compareAndSet(100,2019)+"\t"+atomicReference.get());
//        },"t2").start();

        System.out.println("以下是ABA问题的解决++++++++++++++");
        //t3线程
        new Thread(() ->{
            //获取版本号
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 第一次的版本号" + stamp);
            //暂停t3线程1秒钟
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            //传入4个值，期望值，更新值，期望版本号，更新版本号
            atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName() + "\t 第二次的版本号" + atomicStampedReference.getStamp());

            atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName() + "\t 第三次的版本号" + atomicStampedReference.getStamp());
        },"t3" ).start();

        //t4线程
        new Thread(() ->{
            //获取版本号
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() +"\t 第一次版本号" + stamp);
            //暂停t4三秒钟，保证t3完成ABA操作
            try{
                TimeUnit.SECONDS.sleep(3);

            }catch (InterruptedException e){
                e.printStackTrace();
            }
            boolean result = atomicStampedReference.compareAndSet(100,2019,stamp,stamp+1);
            System.out.println(Thread.currentThread().getName() +"\t修改成功与否："+result+"\t当前最新实际版本号:"+atomicStampedReference.getStamp());
            System.out.println(Thread.currentThread().getName()+"\t 当前实际最新值为 " + atomicStampedReference.getReference());

        },"t4").start();



    }
}
