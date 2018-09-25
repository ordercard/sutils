package com.spring.sutils.design.pattern.event.bus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 20:34 2018/9/21 2018
 * @Modify:
 */
public class Dispatcher {

    private final Executor executorService;
    private final EventExceptionHandler eventExceptionHandler;
    public  static final  Executor SEQ_EXECUTOR_SERVICE=SeqExecutorService.INSTANCE;
    public  static final  Executor  PRE_THREAD_EXECUTOR_SERVICE=PreThreadExecutorService.INSTANCE;

    public Dispatcher(Executor executorServicel, EventExceptionHandler eventExceptionHandler) {
        this.executorServicel = executorServicel;
        this.eventExceptionHandler = eventExceptionHandler;
    }

    public static Dispatcher newDispatcher(EventExceptionHandler eventExceptionHandler, Executor executor) {

          return  new Dispatcher(executor,eventExceptionHandler);
    }


    public void dispatch(Bus eventBus, Registry registry, Object event, String topic) {
        ConcurrentLinkedQueue<Subscriber> subscribers = registry.scanSubscriber(topic);
        if (null==subscribers){
            if (eventExceptionHandler!=null){

                eventExceptionHandler.handle(new IllegalArgumentException(),new BaseEventContext(eventBus.getBusName(),null,event));

            }
            return;
        }

        subscribers.stream().filter(subscriber -> !subscriber.isDisable()).filter(subscriber -> {
            Method  subscribeMethod = subscriber.getSubscribeMethod();
            Class<?>  aClass= subscribeMethod.getParameterTypes()[0];
            return (aClass.isAssignableFrom(event.getClass()));
        }).forEach(subscriber -> realInvokeSubscribe(subscriber,event,eventBus));


    }

    private void realInvokeSubscribe(Subscriber subscriber, Object event, Bus eventBus) {

        Method  subscribeMethod = subscriber.getSubscribeMethod();
        Object  subscibeObject =subscriber.getSubscribeObject();
        executorService.execute(()->{
            try {
                subscribeMethod.invoke(subscibeObject,event);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                if (null!=eventExceptionHandler){
                    eventExceptionHandler.handle(e,new BaseEventContext(eventBus.getBusName(),subScriber,event) );
                }
                e.printStackTrace();
            }
        });

    }




    public void close( ){

        if (executorService instanceof ExecutorService){
            ( (ExecutorService) executorService).shutdown();
        }

    }


    static  Dispatcher seqDispatcher(EventExceptionHandler  eventExceptionHandler){
        return  newDispatcher(eventExceptionHandler,SEQ_EXECUTOR_SERVICE);

    }

    static Dispatcher preDispatcher(EventExceptionHandler  eventExceptionHandler){
        return  newDispatcher(eventExceptionHandler,SEQ_EXECUTOR_SERVICE);
    }



}
