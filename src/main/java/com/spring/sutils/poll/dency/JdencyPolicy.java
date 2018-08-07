package com.spring.sutils.poll.dency;

import com.spring.sutils.poll.JThreadPool;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 下午3:32 2018/8/2 2018
 * @Modify:
 */
public interface JdencyPolicy {
    void  reject(Runnable runnable, JThreadPool threadPool);


       /**
        *
        *@描述 该策略会向任务提交者抛出异常
        *@参数
        *@返回值
        *@创建人 慧强
        *@创建时间 2018/8/2
        *@修改人和其它信息

        */
    class  AbortDenyPlicy  implements  JdencyPolicy{


        @Override
        public void reject(Runnable runnable, JThreadPool threadPool) {
            throw new RunableDenyException("the runable"+ runnable+"will abort");

        }
    }

    /**
     *
     *@描述 丢弃
     *@参数
     *@返回值
     *@创建人 慧强
     *@创建时间 2018/8/2
     *@修改人和其它信息

     */
    class  DiscardDenyPlicy  implements  JdencyPolicy{


        @Override
        public void reject(Runnable runnable, JThreadPool threadPool) {
            System.out.println("任务已经被丢弃");
        }
    }

    /**
     *
     *@描述 该拒绝策略会使得任务在提交者所在的线程中执行任务，交给调用者的线程直接运行runnable，而不是被加入到线程池中。
     *@参数
     *@返回值
     *@创建人 慧强
     *@创建时间 2018/8/2
     *@修改人和其它信息

     */
    class  RunnerDenyPlicy  implements  JdencyPolicy{


        @Override
        public void reject(Runnable runnable, JThreadPool threadPool) {
            if(!threadPool.isShutDown()){
                runnable.run();
            }




        }
    }

}
