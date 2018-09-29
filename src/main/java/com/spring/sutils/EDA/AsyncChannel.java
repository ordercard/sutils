package com.spring.sutils.EDA;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 11:46 2018/9/29 2018
 * @Modify:
 */
public abstract class AsyncChannel implements  Channel<Event> {

    private  final ExecutorService executorService;


    public AsyncChannel(ExecutorService executorService) {
        this.executorService = executorService;
    }
    public AsyncChannel() {
        this(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2));
    }

    @Override
    public final  void dispatch(Event msg) {
        executorService.submit(()->handle(msg));
    }

    protected   abstract  void handle(Event msg);




    void  stop(){

        if (null!=executorService && ! executorService.isShutdown()){
            executorService.shutdown();
        }

    }



}
