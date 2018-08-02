package my.defined.thread.poll;

import my.defined.thread.poll.dency.JdencyPolicy;

import java.util.LinkedList;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 下午5:28 2018/8/2 2018
 * @Modify:
 */
public class JLinkedRunableQueue implements  JRunableQueue {
    //最大容量
    private final  int limit;
    //拒绝侧罗  如果任务队列中的任务已经满了，则需要执行拒绝策略
    private final JdencyPolicy jdencyPolicy;

    private final LinkedList<Runnable>  runnableLinkedList =new LinkedList<>();

    private  final  JThreadPool threadPool;

    public JLinkedRunableQueue(int limit, JdencyPolicy jdencyPolicy, JThreadPool threadPool) {
        this.limit = limit;
        this.jdencyPolicy = jdencyPolicy;
        this.threadPool = threadPool;
    }

    @Override
    public void offer(Runnable runnable) {
        synchronized (runnableLinkedList){
            System.out.println("进入同步加入方法");

       if (runnableLinkedList.size()>=limit){
           jdencyPolicy.reject(runnable,threadPool);
       }
       else{
           runnableLinkedList.addLast(runnable);
           runnableLinkedList.notifyAll();

       }}
    }


    @Override
    public Runnable take() throws InterruptedException {
        synchronized (runnableLinkedList) {
          while(runnableLinkedList.isEmpty()){
            try {
                System.out.println("size为"+runnableLinkedList.size());
                //如果任务队列中没有可以执行的任务，则当前线程将会挂起，进入runablelist关联的一个对象监视器monitor waitset中等待被唤醒， 这种不和io阻塞和没获取到锁一样，这种可以响应中断并且，被唤醒的方式也不一样
                System.out.println(Thread.currentThread().getName()+"正在等待！");
                runnableLinkedList.wait();
            } catch (InterruptedException e) {
                System.out.println("抛出异常");
                throw e;
            }
        }
            return runnableLinkedList.removeFirst();
        }

    }



    @Override
    public int size() {
        return runnableLinkedList.size();
    }
}
