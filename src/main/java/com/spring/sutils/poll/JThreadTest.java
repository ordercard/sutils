package com.spring.sutils.poll;

import com.spring.sutils.poll.factory.JBasicThreadPool;

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
        final JThreadPool jThreadPool = new JBasicThreadPool(2, 10, 7, 1000);

        for (int i = 0; i < 20; i++) {
            jThreadPool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println(Thread.currentThread().getName() + "： 正在执行和完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        }
            for (; ; ) {

                System.out.println("活动线程数" + jThreadPool.getActiveCount());
                System.out.println("任务池的任务数" + jThreadPool.getQueueSize());
                System.out.println("活动最大数core" + jThreadPool.getCoreSize());
                System.out.println("活动最大数max" + jThreadPool.getMaxSize());
                System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-");


                TimeUnit.SECONDS.sleep(5);
            }




    }
}
