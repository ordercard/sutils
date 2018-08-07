package com.spring.sutils.jaop.SpringAop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 上午11:05 2018/8/7 2018
 * @Modify:
 */
@Aspect
@Component
public class AdviceTest {

    @Pointcut("@annotation(com.spring.sutils.jaop.SpringAop.Timer)")
    public void  pp(){

    }

    @Before("pp()")
    public void before(){
        System.out.println("之前");
    }




}
