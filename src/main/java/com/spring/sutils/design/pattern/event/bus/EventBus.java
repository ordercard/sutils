package com.spring.sutils.design.pattern.event.bus;


import java.util.concurrent.Executor;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 13:57 2018/9/20 2018
 * @Modify:
 */
public class EventBus implements Bus {
    private final Registry registry = new Registry();
    private String busName;
    private final static String DEFAULT_BUS_NAME = "default";
    private final static String TOPIC_NAME = "default_topic";

    private final Dispatcher dispatcher;

    public EventBus() {
        this(DEFAULT_BUS_NAME, null, Dispatcher.SEQ_EXECUTOR_SERVICE);
    }

    public EventBus(String busName) {
        this(busName, null, Dispatcher.SEQ_EXECUTOR_SERVICE);
    }

    public EventBus(String busName, EventExceptionHandler eventExceptionHandler, Executor executor) {
        this.busName = busName;
        this.dispatcher = Dispatcher.newDispatcher(eventExceptionHandler, executor);
    }

    public EventBus(EventExceptionHandler eventExceptionHandler) {
        this(DEFAULT_BUS_NAME, eventExceptionHandler, Dispatcher.SEQ_EXECUTOR_SERVICE);
    }


    @Override
    public void register(Object subscriber) {
        this.registry.bind(subscriber);
    }

    @Override
    public void unregister(Object subciber) {
        this.registry.unBind(subciber);
    }

    @Override
    public void post(Object event, String topic) {
        this.dispatcher.dispatch(this, registry, event, topic);

    }

    @Override
    public void post(Object event) {
        this.post(event, TOPIC_NAME);
    }

    @Override
    public void close() {
        this.dispatcher.close();
    }

    @Override
    public String getBusName() {
        return this.busName;
    }
}
