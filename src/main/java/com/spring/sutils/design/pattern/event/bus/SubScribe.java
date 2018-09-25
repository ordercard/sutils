package com.spring.sutils.design.pattern.event.bus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 14:10 2018/9/20 2018
 * @Modify:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SubScribe {

    String topic() default  "default-topic";
}
