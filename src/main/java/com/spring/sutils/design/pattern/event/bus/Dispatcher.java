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

    public Dispatcher(Executor executorService, EventExceptionHandler eventExceptionHandler) {
        this.executorService = executorService;
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

        final Method  subscribeMethod = subscriber.getSubscribeMethod();
        final Object  subscibeObject =subscriber.getSubscribeObject();
        executorService.execute(()->{
            try {
                subscribeMethod.invoke(subscibeObject,event);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                if (null!=eventExceptionHandler){
                    eventExceptionHandler.handle(e,new BaseEventContext(eventBus.getBusName(),subscriber,event));
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
    private static class SeqExecutorService   implements Executor {
        private  final  static SeqExecutorService INSTANCE = new SeqExecutorService();

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }

    private static class PreThreadExecutorService implements Executor{
        private final static PreThreadExecutorService  INSTANCE= new PreThreadExecutorService();

        @Override
        public void execute(Runnable command) {
            new Thread(command).start();

        }
    }


    private static  class BaseEventContext implements EventContext {

        private final String eventBusName;

        private final Subscriber subscriber;

        private final Object event;

        public BaseEventContext(String eventBusName, Subscriber subscriber, Object event) {
            this.eventBusName = eventBusName;
            this.subscriber = subscriber;
            this.event = event;
        }



        @Override
        public String getSource() {
            return this.eventBusName;
        }

        @Override
        public Object getSuvscriber() {
            return subscriber != null ?subscriber.getSubscribeObject():null;
        }

        @Override
        public Method getSubscribe() {
            return subscriber != null ? subscriber.getSubscribeMethod():null;
        }

        @Override
        public Object getEvent() {
            return this.event;
        }
    }



}
