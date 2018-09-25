package com.spring.sutils.design.pattern.event.bus;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 15:00 2018/9/25 2018
 * @Modify:
 */
public interface EventExceptionHandler {

    void handle(Throwable cause,EventContext context);
}
