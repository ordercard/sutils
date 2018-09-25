package com.spring.sutils.design.pattern.event.bus;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 15:30 2018/9/20 2018
 * @Modify:
 */
public class AsyncEventBus extends  EventBus {

    AsyncEventBus(String busName, EventExceptionHandler eventExceptionHandler, ThreadPoolExecutor executor){
        super(busName,eventExceptionHandler,executor);
    }

    public  AsyncEventBus(String busName, ThreadPoolExecutor executor){
        this(busName,null,executor);
    }
    public  AsyncEventBus( ThreadPoolExecutor executor){
        this("default-async",null,executor);
    }
    public  AsyncEventBus(EventExceptionHandler eventExceptionHandler, ThreadPoolExecutor executor){
        super("default-async",eventExceptionHandler,executor);
    }

}
