/**
 * volatile不保证原子性
 * 禁止指令重排
 */

import java.util.Enumeration;

/**
 * 用volatile修饰以后，不能保证原子性
 */

/**
 * 在多线程的环境下，++操作是不安全的
 *
 */

public class volatileYuanZiXing {
    public static void main(String[] args) {
        MyData  myData = new MyData();
        //创建了10个线程，每个线程循环1000次
        for(int i=0;  i<10; i++){
            new Thread(() ->{
                for(int j=0;j<1000;j++){
                    myData.addPlusPlus();
                }
            },String.valueOf(i)).start();
        }
        //默认有两个线程，一个main线程，一个gc线程
        while(Thread.activeCount()>2){
            //yield表示不执行
            Thread.yield();
        }
        //查看最终的值，如果volatile保证原子性，则应该输出为10000
        System.out.println(Thread.currentThread().getName()+"\t finally number value :"+myData.number);

    }

    /**
     * 运行输出发现每一次的结果都不一样，都不是10000，说明volatile修饰不保证原子性
     */
    /**
     * 如何解决？在addplusplus方法中，加入sychronized关键字
     */

}
