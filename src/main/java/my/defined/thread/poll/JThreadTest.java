package my.defined.thread.poll;

import my.defined.thread.poll.factory.JBasicThreadPool;

import java.util.concurrent.TimeUnit;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 下午8:36 2018/8/2 2018
 * @Modify:
 */
public class JThreadTest {
    public static void main(String[] args) throws InterruptedException {

        //初始值  核心   最大   队列大小
       final   JThreadPool  jThreadPool = new JBasicThreadPool(2,4,6,1000);

            for (int i=0;i<20;i++){
                System.out.println("开始创建"+i);
                jThreadPool.execute(()->{

                        System.out.println(Thread.currentThread().getName()+"： 正在执行和完成");
                });

                for(;;){

                    TimeUnit.SECONDS.sleep(10);
                    System.out.println("活动线程数"+jThreadPool.getActiveCount());
                    System.out.println("任务池的任务数"+jThreadPool.getQueueSize());
                    System.out.println("活动最大数core"+jThreadPool.getCoreSize());
                    System.out.println("活动最大数max"+jThreadPool.getMaxSize());
                    System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-");


                }


            }

    }
}
