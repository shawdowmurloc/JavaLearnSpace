import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {
    public static void main(String[] args) {
        //创建一个原子类
        AtomicInteger atomicInteger = new AtomicInteger(5);
        atomicInteger.getAndIncrement();
        //一个是期望值，一个是更新值，他们两个相同以后才能进行更改
        //假设三秒前我拿的是5，也就是expect为5，然后要更新成2019
        System.out.println(atomicInteger.compareAndSet(5,2019)+"\t current data:"+atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5,1024)+"\t current data:"+atomicInteger.get());

    }
}
/**
 * 输出为
 * true	 current data:2019
 * false	 current data:2019
 * 这是在执行第一次的时候，期望值和原本值的满足的，因此此u该成功
 * 第二次主内存值已经修改成2019了，所以更改失败
 */