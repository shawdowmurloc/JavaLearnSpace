import java.sql.SQLOutput;
import java.util.concurrent.TimeUnit;

/**
 * 假设是主物理内存
 */

//class MyData{
//    int number = 0;
//    public void addTo60(){
//        this.number = 60;
//    }
//}

/**
 * 用volatile修饰以后，增加了主线程和线程之间的可见性，只要有一个线程修改了内存中的值，其它线程能立马感知到
 */
class MyData{
    volatile int number = 0;
    public void addTo60(){
        this.number = 60;
    }
    public synchronized void addPlusPlus(){
        number ++;
    }
}


/**
 * 验证volatile可见性
 * 假设int num = 0;,num变量之前没有/添加volatile关键字修饰
 */
public class volatileDemo {
    public static void main(String[] args) {
        //资源类
        final MyData  myData = new MyData();
        //AAA线程实现Runnable接口，lambda表达式
        new Thread(() ->{
            System.out.println(Thread.currentThread().getName()+"\t come in");
            try{
                //线程睡眠三秒，假装进行计算
                TimeUnit.SECONDS.sleep(3);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            //修改num的值
            myData.addTo60();
            //输出修改后的值
            System.out.println(Thread.currentThread().getName()+"\t update number value:"+myData.number);
        },"AAA").start();
        while(myData.number == 0){
            //main线程就一直在这里等待循环，直到number的值不为0

        }
        //如果能输出这句话说明AAA线程在睡眠三秒后，更新的number的值重新写入到主内存，并且被main线程感知到
        System.out.println(Thread.currentThread().getName()+"\t mission is over");

    }

    /**
     * 没有volatile修饰以后
     * 最后输出为
     * AAA	 come in
     * AAA	 update number value:60
     * 最后线程并没有停止，并行没有输出mission is over这句话，说明没有volatile这句话修饰，并没有可见性
     */
    /**
     * 当有volatile修饰，
     * 最后输出为
     * AAA	 come in
     * main	 mission is over
     * AAA	 update number value:60
     */

}
