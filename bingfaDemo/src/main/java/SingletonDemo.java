import java.sql.SQLOutput;

/**
 * 单例模式
 */

public class SingletonDemo {
    private static SingletonDemo instance = null;
    private  SingletonDemo(){
        System.out.println(Thread.currentThread().getName()+"\t 我是构造方法SingletonDemo");

    }
    /**
     * 解决方法1 ：引入synchronized关键字能够解决高并发下的单例模式问题
     * 但是synchronized关键字太过重量级，保证数据一致性，而降低了并发性
     */
//    public synchronized  static SingletonDemo getInstance(){
//        if(instance==null){
//            instance = new SingletonDemo();
//        }
//        return instance;
//    }

    /**
     * 解决方法2；
     * 通过引入DCL Double Check Lock双端检索机制
     * 在进来和出去的时候进行检测
     * 问题是双端检索机制不一定是线程安全的，原因是有指令重排的存在
     *
     * @return
     */
    public static SingletonDemo getInstance(){
        if(instance==null){
            //同步代码段的时候，进行检测
            synchronized (SingletonDemo.class){
                if(instance==null){
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }


    public static void main(String[] args) {
//        //这里==比较的是内存地址
//        System.out.println(SingletonDemo.getInstance()==SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance()==SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance()==SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance()==SingletonDemo.getInstance());
        for(int i=0;i<10;i++){
            new Thread(() ->{
                SingletonDemo.getInstance();
            },String.valueOf(i)).start();
        }
    }
}