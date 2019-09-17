/**
 * 多线程
 *
 *      基本概念：
 *          进程：一个包含自身地址的独立执行的程序，系统可以分配给每个进程一段有限的使用CPU的时间（时间片），
 *                CPU在这段时间执行这个程序，然后下一个时间又跳至另一个进程中去执行，由于CPU转换较快，所以
 *                使得进程好像是同时执行。
 *          线程：进程中的执行流程，一个进程中可以同时包括多个线程，每个线程可以得到一小段程序的执行时间。
 *          任务：线程在启动时执行的工作，工作的功能代码被写在run()方法中。
 *
 *      线程的实现方式：
 *          1、继承java.lang.Thread：实质上是Thread类实现了Runnable接口
 *              Thread类常用的构造方法：
 *                  public Thread(): 创建一个新的线程对象；
 *                  public Thread(String threadName)：创建一个名为threadName的线程对象。
 *              线程逻辑实现：run()方法。
 *              线程的执行：调用实例的start()方法，会自动执行run()方法。如果start()调用一个已启动的线程，系统将
 *                          抛出IllegalThreadStateException异常。
 *          2、实现java.lang.Runnable：在继承了其他类的同时，也需要使用多线程的情况下，通过实现Runnable接口来
 *                                     实现多线程
 *              构造方法：
 *                  public Thread(Runnable target)：参数为Runnable实例
 *                  public Thread(Runnable target, String name)：创建名为name的线程对象
 *              逻辑实现：通过实现run()方法实现
 *              线程启动步骤：
 *                  1、建立Runnable对象，
 *                  2、使用参数为Runnable对象的构造方法创建Thread实例，
 *                  3、调用start()方法启动线程。
 *
 *          3、线程的生命周期：
 *              1、出生状态：start()执行之前。
 *              2、就绪状态：调用start()之后。
 *              3、运行状态：线程得到系统资源后，就进入到运行状态。
 *              4、等待状态：运行状态下的线程调用Thread类的wait()方法，就进入到等待状态，
 *                           等待状态下的线程需要调用notify()才能被唤醒，notifyAll()将所有处于等待状态下的线程唤醒。
 *              5、休眠状态：线程调用Thread类中的sleep()方法时，会进入到休眠状态。sleep()方法通常在run方法内部使用，
 *
 *              6、阻塞状态：线程在运行状态发出输入/输出请求，该线程就进入阻塞状态，在其等待输入/输出结束时线程
 *                           就进入就绪状态，对于阻塞的线程来说，即使系统资源空闲，线程依旧不能回到运行状态。
 *              7、死亡状态：线程的run()执行完毕时，就进入到死亡状态。
 *
 *          4、线程的操作：
 *              1、线程加入：join()，在一个线程A的run方法中通过另一个线程B的实例调用join方法，将B线程加入到
 *                           线程A中，A线程会等到B线程执行完再执行。
 *              2、线程的中断：
 *                  1、以往的版本中使用stop方法来中断线程，但当前版本已经弃用，当前版本提倡在run方法中使用
 *                     无限循环的形式，然后用一个布尔标记来控制循环的停止。
 *                  2、使用interrupted()方法使线程离开run()方法， 同时结束线程，但会抛出InterruptedException异常，
 *                     用户可以在异常的处理时完成业务的中断处理。
 *
 *          5、线程的优先级：优先级越高的线程，资源分配的几率越高，可以通过setPriority()来设置线程的优先级。
 *                           线程的优先级在1-10内，如果不在范围内，则会发生IllegalArgumentException异常。
 *          6、线程同步：
 *              1、线程安全：当多个线程需要访问同一资源时，就会存在冲突问题
 *              2、线程同步机制：解决资源共享问题
 *                  1、同步块：使用synchronized关键字建立临界区，synchronized(Object){}，Object为任意对象，每个
 *                             对象都存在一个标志位，分别有两个值0和1，如果为0，表示此同步块中存在其他线程在运行，
 *                             这时该线程处于就绪状态。直到同步块中的线程执行完同步块中的代码，对象标志被设置为1
 *                             时，该线程才能执行同步块中的代码，并将Object对象的标志设置为0。
 *                  2、同步方法：即用synchronized关键字修饰的方法，当某个对象调用了同步方法时，该对象上的其他同步
 *                               方法必须等到该同步方法执行完毕后才能被执行(即使同一个方法多次调用，也必须等到本
 *                               次执行完成之后才能进行下一次执行)，必须将每个能访问共享资源的方法修饰为synchronized，
 *                               否则就会出错。
 *
 */
/* 通过继承Thread来实现多线程 */
class ThreadTest extends java.lang.Thread {

    private int count = 10;

    @Override
    public void run() {
        while (true) {
            System.out.print(count + " ");
            if (--count == 0) {
                return;
            }
            try {
                java.lang.Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Parent {
    public void printParent() {
        System.out.println("parent");
    }
}

/* 通过实现Runnable接口来实现多线程 */
class RunnableTest extends Parent implements Runnable {

    private char tag = 'a';

    @Override
    public void run() {
        while (true) {
            System.out.println(tag + " ");
            tag = (char)(tag + 1);
            if (tag > 'g') {
                return;
            }
            try {
                java.lang.Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/* 不安全的线程 */
class UnsafeCountThread implements Runnable {
    private int count = 10;

    public void run() {
        while (true) {
            if (count > 0) {
                try {
                    java.lang.Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(count--);
            } else {
                break;
            }
        }
    }
}

/* 通过synchronized关键字来实现同步块，防止资源冲突 */
class SafeCountThread implements Runnable {
    private int count = 10;

    @Override
    public void run() {
        while(true) {
            synchronized("") {
                if (count > 0) {
                    try {
                        java.lang.Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(count--);
                } else {
                    return;
                }
            }
        }
    }
}

class SynchronizedFunCountThread implements Runnable{

    private int count = 10;

    synchronized void doit() {
        if (count > 0) {
            try {
                java.lang.Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(count--);
        }
    }

    @Override
    public void run() {
        while (true) {
            doit();
        }
    }
}

public class Thread {
    private static java.lang.Thread threadA;
    private static java.lang.Thread threadB;

    static void unsafeThread() {
        UnsafeCountThread t = new UnsafeCountThread();
        java.lang.Thread t1 = new java.lang.Thread(t);
        java.lang.Thread t2 = new java.lang.Thread(t);
        java.lang.Thread t3 = new java.lang.Thread(t);
        java.lang.Thread t4 = new java.lang.Thread(t);

        System.out.println("不安全的线程");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

    static void safeThread() {
        SafeCountThread t = new SafeCountThread();
        java.lang.Thread t1 = new java.lang.Thread(t);
        java.lang.Thread t2 = new java.lang.Thread(t);
        java.lang.Thread t3 = new java.lang.Thread(t);
        java.lang.Thread t4 = new java.lang.Thread(t);

        System.out.println("安全的线程：");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

    static void synchronizedFunSafeThread() {
        SynchronizedFunCountThread t = new SynchronizedFunCountThread();
        java.lang.Thread t1 = new java.lang.Thread(t);
        java.lang.Thread t2 = new java.lang.Thread(t);
        java.lang.Thread t3 = new java.lang.Thread(t);
        java.lang.Thread t4 = new java.lang.Thread(t);

        System.out.println("同步方法实现线程安全：");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

    public static void main(String[] args){
        ThreadTest thread = new ThreadTest();
        java.lang.Thread runnableThread = new java.lang.Thread(new RunnableTest());
        thread.setPriority(java.lang.Thread.MIN_PRIORITY); // 设置优先级1
        runnableThread.setPriority(java.lang.Thread.MAX_PRIORITY); // 设置优先级10
        /* 线程优先级 */
        // thread.start();
        // runnableThread.start();

        threadA = new java.lang.Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("A线程开始执行");
                System.out.println("B线程加入：");
                try {
                    threadB.start();
                    threadB.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("A线程继续执行");
            }
        });
        threadB = new java.lang.Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("加入线程先执行");
                threadB.interrupt();
                System.out.println("加入线程睡眠1s");
                try {
                    java.lang.Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("加入线程中断");
                    // e.printStackTrace();
                    return;
                }
                for (int i = 0; i < 10; i++) {
                    System.out.print(i + " ");
                }
                System.out.println();
                System.out.println("加入线程执行结束");
            }
        });
        /* 线程加入 */
        // threadA.start();
        // threadB.start();
        // threadB.interrupt();

        /* 不安全的线程 */
        // unsafeThread();

        /* 安全的线程 */
        // safeThread();

        /* 同步方法实现安全线程 */
        synchronizedFunSafeThread();
    }
}
