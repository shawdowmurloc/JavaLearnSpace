//1.当局部变量和成员变量重名的时候，为了区分局部变量和成员变量，在方法中使用this来进行区分
/**
 *  输出为：
 *  这是局部变量
 *  这是成员变量
class Demo{
    String str = "这是成员变量";
    void fun(String str1){
        System.out.println(str1);
        System.out.println(str);
    }
}
public class demoThis{
    public static void main(String args[]){
        Demo demo = new Demo();
        demo.fun("这是局部变量");
    }
}

*/

/**
 输出为：
 这是局部变量
 这是成员变量
 这是局部变量

class Demo{
    String str = "这是成员变量";
    void fun(String str){
        System.out.println(str);
        System.out.println(this.str);
        this.str = str;
        System.out.println(this.str);
    }
}
public class demoThis{
    public static void main(String args[]){
        Demo demo = new Demo();
        demo.fun("这是局部变量");
    }
}
 */
//2.用this关键字把当前对象传递给其它方法

/**
 * 在一个类中需要把该类的对象传递给其它类的方法时，
 * 需要使用this对象作为其它方法的参数


class Person{
    public void eat(Apple apple){
        Apple peeled = apple.getPeeled();
        System.out.println("Yummy");
    }
}
class Peeler{
    static Apple peel(Apple apple){
        //....remove peel
        return apple;
    }
}
class Apple{
    Apple getPeeled(){
        return Peeler.peel(this);
    }
}
public class demoThis{
    public static void main(String args[]){
        new Person().eat(new Apple());
    }
}
 */

//3.当需要返回当前对象的引用时，就常常在方法中写return this
/**返回i=4
public class demoThis{
    int i = 0;
    demoThis increment(){
        i += 2;
        return this;
    }
    void print(){
        System.out.println("i = " + i);
    }
    public static void main(String args[]){
        demoThis x = new demoThis();
        x.increment().increment().print();
    }
}
 */

//4.在在构造器中调用构造器需要使用this

/**
 * 输出为
 * 只有String类型的参数的构造函数s = hi
 * 有String类型和int参数类型的构造函数
 * 默认的构造函数
 * petalCount=122s=hi
 *
 */
public class demoThis{
    int petalCount = 0;
    String s = "initial value";
    demoThis(int petals){
        petalCount = petals;
        System.out.println("只有int类型的参数的构造函数"+"petalCount = "+petalCount);

    }
    demoThis(String ss){
        s = ss;
        System.out.println("只有String类型的参数的构造函数"+"s = " + s);
    }
    demoThis(String s,int petals){
        this(s);  //自动匹配带一个参数的构造函数
        this.petalCount = petals;
        System.out.println("有String类型和int参数类型的构造函数");
    }
    demoThis(){
        //这个无参的构造函数去调用有两个参数的构造函数
        this("hi",122);
        System.out.println("默认的构造函数");
    }
    void print(){
        System.out.println("petalCount=" + petalCount + "\t s=" + s);
    }

    public static void main(String[] args) {
        demoThis demo = new demoThis();
        demo.print();
    }
}

/**
 * １、表示对当前对象的引用！
 *
 * ２、表示用类的成员变量，而非函数参数。
 *
 * ３、用于在构造方法中引用满足指定参数类型的构造器（其实也就是构造方法）。但是这里必须非常注意：只能引用一个构造方法且必须位于开始！
 *
 * 4、很明显this不能用在static方法中，因为this指代当前对象，而static则无对象之说。
 */