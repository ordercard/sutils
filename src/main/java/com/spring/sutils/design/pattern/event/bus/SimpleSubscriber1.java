package com.spring.sutils.design.pattern.event.bus;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 19:00 2018/9/25 2018
 * @Modify:
 */
public class SimpleSubscriber1 {

    @SubScribe
    public  void method1(String message){
        System.out.println("方法1");
    }

    @SubScribe(topic = "test")
    public  void method2(String message){
        System.out.println("方法2");
    }

}
