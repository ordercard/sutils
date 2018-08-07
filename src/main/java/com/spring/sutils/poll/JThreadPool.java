package com.spring.sutils.poll;

/**
 * @Auther :huiqiang
 * @Description :  自定义线程池
 * @Date: Create in 下午2:14 2018/8/2 2018
 * @Modify:
 */
public interface JThreadPool {
    /**
     *
     *@描述提交任务到线程池

     *@参数  [runnable]

     *@返回值  void

     *@创建人  慧强

     *@创建时间  2018/8/2

     *@修改人和其它信息

     */
    void execute(Runnable runnable);
    /**
     *
     *@描述 关闭线程池
     *@参数
     *@返回值
     *@创建人 慧强
     *@创建时间 2018/8/2
     *@修改人和其它信
     */
    void shutdown();



    int  getInitSize();



    int  getCoreSize();



    int  getMaxSize();

    /**
     *
     *@描述 获取线程池中用于做缓存任务队列的大小
     *@参数 []
     *@返回值 int
     *@创建人 慧强
     *@创建时间 2018/8/2
     *@修改人和其它信息

     */
    int getQueueSize();


    int getActiveCount();


    boolean isShutDown();




}
