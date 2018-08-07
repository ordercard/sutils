package com.spring.sutils.poll.factory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 下午6:12 2018/8/2 2018
 * @Modify:
 */
public   class DefaultThreadFactory implements JThreadFactory {
     private static final AtomicInteger G= new AtomicInteger(1);
     private   static ThreadGroup threadGroup =new ThreadGroup("线程组"+G.getAndDecrement());
     private  static final AtomicInteger c =new AtomicInteger(0);

    @Override
    public Thread createThread(Runnable runnable) {

        return new Thread(threadGroup,runnable, "线程："+c.getAndDecrement());
    }
}
