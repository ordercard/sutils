package my.defined.thread.poll.factory;

import my.defined.thread.poll.InternalTask;
import my.defined.thread.poll.JLinkedRunableQueue;
import my.defined.thread.poll.JRunableQueue;
import my.defined.thread.poll.JThreadPool;
import my.defined.thread.poll.dency.JdencyPolicy;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 下午5:49 2018/8/2 2018
 * @Modify:
 */
public class JBasicThreadPool extends  Thread implements JThreadPool {

     private final int intsize;

     private final int maxSize;

      private final int coresize;

    private  int activeCount;


    private  final  JThreadFactory jThreadFactory;

    private  final JRunableQueue jRunableQueue;
    private volatile   boolean isShutDowm =false;


    //工作的集合
    private Queue<ThreadTask> queue = new ArrayDeque<>();

    private final  static JdencyPolicy defaultJdencypolicy =  new JdencyPolicy.DiscardDenyPlicy();

    private final  static JThreadFactory J_THREAD_FACTORY =new DefaultThreadFactory();
    private final  long keepAliveTime;
    private final  TimeUnit timeUnit;

    public JBasicThreadPool(int intsize, int maxSize, int coresize, int queuesize) {

        this(intsize,maxSize,coresize,J_THREAD_FACTORY,queuesize,defaultJdencypolicy,10,TimeUnit.SECONDS);
    }


    public JBasicThreadPool(int intsize, int maxSize, int coresize, JThreadFactory jThreadFactory, int queuesize, JdencyPolicy defaultJdencypolicy, long i, TimeUnit seconds) {
         this.intsize=intsize;
        this.maxSize = maxSize;
        this.coresize = coresize;
        this.jThreadFactory = jThreadFactory;
        this.jRunableQueue =  new JLinkedRunableQueue(queuesize,defaultJdencypolicy,this); //初始化线程池大小 。以及执行策略、  当前线程池
        this.keepAliveTime = i;
        this.timeUnit = seconds;
        this.init();
    }

    private void  init(){


        start();
        IntStream.range(0,intsize).forEach( x->newTread());
    }

    private void newTread() {

         InternalTask  internalTask =new InternalTask(this.jRunableQueue);

         Thread thread = this.jThreadFactory.createThread(internalTask);
        ThreadTask threadTask =new ThreadTask(thread,internalTask);
        queue.offer(threadTask);
         this.activeCount++;
         thread.start();

    }

    private void remeove(){
        ThreadTask ask = queue.remove();
        ask.internalTask.stop();
        this.activeCount--;
    }
    /**
     *
     *@描述 当前run方法主要用于维护线程数量 扩容 回收
     *@参数 []
     *@返回值 void
     *@创建人 慧强
     *@创建时间 2018/8/2
     *@修改人和其它信息

     */
    @Override
    public  void  run(){
      while (!isShutDowm && !isInterrupted()){
          try {
              timeUnit.sleep(keepAliveTime);

          } catch (InterruptedException e) {
              isShutDowm=true;
              e.printStackTrace();
          }
          synchronized (this){
              if (isShutDowm){

                  break;
              }
              //当前队列还有任务未处理，并且act<core才会继续扩容
              if (jRunableQueue.size()>0 && activeCount<coresize){

                  for (int i=intsize;i<coresize;i++){
                      newTread();
                  }

                  continue;

              }

              //当前队列还有任务未处理，并且act<max才会继续扩容
              if (jRunableQueue.size()>0 && activeCount<coresize){

                  for (int i=coresize;i<maxSize;i++){
                      newTread();
                  }


              }
              //当前队列没有任务处理， 需要回收，回收到coresize；
              if (jRunableQueue.size()>0 && activeCount<coresize){

                  for (int i=coresize;i<activeCount;i++){
                      remeove();
                  }


              }


          }
      }

    }


    /**
     *
     *@描述 创建执行线程并启动
     *@参数 [runnable]
     *@返回值 void
     *@创建人 慧强
     *@创建时间 2018/8/2
     *@修改人和其它信息

     */

    @Override
    public void execute(Runnable runnable) {
       if (this.isShutDowm){
            throw new  IllegalStateException("该线程池被销毁！");

       }
       this.jRunableQueue.offer(runnable);
    }

    @Override
    public synchronized void  shutdown() {
         if (isShutDowm) {
             return;
         }
    isShutDowm =true;
         queue.forEach(x->{
             x.internalTask.stop();
             x.thread.interrupt();

         });
         //中断当前线程，使得绑定中的 intertask中的run方法中的while循环条件判断是是否被中断！
   this.interrupt();

    }

    @Override
    public int getInitSize() {
        if (isShutDowm){
            throw new IllegalStateException("已经销毁啦线程池");
        }
        return this.intsize;

    }

    @Override
    public int getCoreSize() {
        if (isShutDowm){
            throw new IllegalStateException("已经销毁啦线程池");
        }
        return this.coresize;
    }

    @Override
    public int getMaxSize() {
        if (isShutDowm){
            throw new IllegalStateException("已经销毁啦线程池");
        }
        return this.maxSize;
    }

    @Override
    public int getQueueSize() {
        if (isShutDowm){
            throw new IllegalStateException("已经销毁啦线程池");
        }
        return this.jRunableQueue.size();
    }

    @Override
    public int getActiveCount() {
      synchronized (this){
          return  this.activeCount;

      }
    }

    @Override
    public boolean isShutDown() {
        return false;
    }

    private  static class  ThreadTask{
        Thread thread;
        InternalTask internalTask;


        public ThreadTask(Thread thread, InternalTask internalTask) {
            this.thread = thread;
            this.internalTask = internalTask;
        }
    }


}
