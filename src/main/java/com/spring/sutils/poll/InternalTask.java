package com.spring.sutils.poll;

/**
 * @Auther :huiqiang
 * @Description :  用于线程池内部，该类用到JrunableQueue，然后从中取得某个runable 并执行其run（）
 * @Date: Create in 下午5:09 2018/8/2 2018
 * @Modify:
 */
public class InternalTask implements  Runnable {
    private  final JRunableQueue jRunableQueue;
    private  volatile  boolean runing =true;

    public  InternalTask(JRunableQueue jRunableQueue){
        this.jRunableQueue=jRunableQueue;
    }

    /**
     *
     *@描述 循环读取
     *@参数 []
     *@返回值 void
     *@创建人 慧强
     *@创建时间 2018/8/2
     *@修改人和其它信息

     */
    @Override
    public void run() {
        while(runing&&!Thread.currentThread().isInterrupted()){
        try{
            //队列中拿出一个，并且删除当前位置的她
            Runnable runnable =jRunableQueue.take();
            System.out.println("task-----------》");
            runnable.run();
        }catch (InterruptedException i){
            runing =false;
            break;
        }



        }

    }

    /**
     *
     *
     * 终止线程的执行和 当前线程执行中断方法
     */

    public  void stop(){
        this.runing=false;
    }
}
