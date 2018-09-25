package com.spring.sutils.design.pattern.event.bus;

import java.lang.reflect.Method;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 14:58 2018/9/25 2018
 * @Modify:
 */
public interface EventContext {
    String getSource();

    Object getSuvscriber();

    Method getSubscribe();

    Object getEvent();


}
