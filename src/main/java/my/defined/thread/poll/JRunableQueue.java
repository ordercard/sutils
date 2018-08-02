package my.defined.thread.poll;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 下午3:23 2018/8/2 2018
 * @Modify:
 */
public interface JRunableQueue {

    /* 任务队列，主要用于缓存提交到线程池中的任务 */
    void offer(Runnable runnable);

    /**
     *
     *@描述  工作线程从队列中获取相应的任务
     *@参数 []
     *@返回值 java.lang.Runnable
     *@创建人 慧强
     *@创建时间 2018/8/2
     *@修改人和其它信息

     */


    Runnable take() throws InterruptedException;



    /**
     *
     *@描述 用户获取当前队列的RTunnable的数量
     *@参数 []
     *@返回值 int
     *@创建人 慧强
     *@创建时间 2018/8/2
     *@修改人和其它信息

     */
    int  size();


}
