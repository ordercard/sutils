package com.spring.sutils.design.pattern.event.bus;

/**
 * @Auther :huiqiang
 * @Description : 定义了EventBus所有使用方法
 * @Date: Create in 11:18 2018/9/20 2018
 * @Modify:
 */
public interface Bus {

    /*
     *
     *@描述将某种对象进行注册到BUS上，从此后该类成为SUBsriber

     *@参数  [subscriber]

     *@返回值  void

     *@创建人  慧强

     *@创建时间  2018/9/20

     *@修改人和其它信息

     */
    void register(Object subscriber);

    /*
     *
     *@描述 从这个bus取消注册，取消注册后不会接收到来自bus的任何消息

     *@参数  [subciber]

     *@返回值  void

     *@创建人  慧强

     *@创建时间  2018/9/20

     *@修改人和其它信息

     */
    void unregister(Object subciber);

    /**
     * @描述提交事件到指定的topic
     * @参数
     * @返回值
     * @创建人 慧强
     * @创建时间 2018/9/20
     * @修改人和其它信息
     */

    void post(Object event, String topic);

    /**
     * @描述提交事件到默认的topic
     * @参数
     * @返回值
     * @创建人 慧强
     * @创建时间 2018/9/20
     * @修改人和其它信息
     */

    void post(Object event);

    void  close();

    String  getBusName();
}

