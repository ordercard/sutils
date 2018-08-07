package com.spring.sutils.poll.factory;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 下午3:30 2018/8/2 2018
 * @Modify:
 */
@FunctionalInterface
public interface JThreadFactory {

    Thread createThread(Runnable runnable);
}
