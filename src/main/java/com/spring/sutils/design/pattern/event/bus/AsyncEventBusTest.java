package com.spring.sutils.design.pattern.event.bus;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 19:42 2018/9/25 2018
 * @Modify:
 */
public class AsyncEventBusTest {

    public static void main(String[] args) {
        Bus bus = new AsyncEventBus("TestBus",(ThreadPoolExecutor) Executors.newFixedThreadPool(10));

        bus.register(new SimpleSubscriber1());
        bus.register(new SimpleSubscriber2());

        bus.post("Hello");
        System.out.println("---------------");
        bus.post("Hello","test");
    }
}
