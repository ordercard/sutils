package com.spring.sutils.design.pattern.event.bus;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 20:30 2018/9/21 2018
 * @Modify:
 */
public class SiampleObject{
    @SubScribe(topic = "alex-topic")
    public  void  test( Integer x){

    }
    @SubScribe(topic = "test-topic")
    public  void  test2( Integer y){

    }
}
