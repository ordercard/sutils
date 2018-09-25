package com.spring.sutils.design.pattern.event.bus;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 15:28 2018/9/25 2018
 * @Modify:
 */
public class EventBusTest {


    public static void main(String[] args) {

        Bus  bus =new EventBus("TestBus");
        bus.register(new SimpleSubscriber1());
        bus.register(new SimpleSubscriber2());
        bus.post("hello");
        System.out.println("---------------");
        bus.post("Hello","test");

    }
}
