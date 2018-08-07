package com.spring.sutils.jaop.SpringAop;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 上午10:32 2018/8/7 2018
 * @Modify:
 */
public aspect TXAspectj {
    void  around():call( void  Hello.sayHello()){
        System.out.println("开始事务");
        proceed();
        System.out.println("结束事务");

    }

}
