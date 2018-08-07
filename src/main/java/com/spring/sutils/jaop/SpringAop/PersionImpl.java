package com.spring.sutils.jaop.SpringAop;

import org.springframework.stereotype.Component;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 上午10:39 2018/8/7 2018
 * @Modify:
 */
@Component

public class PersionImpl implements Persion {


    @Timer
    @Override
    public String sayHello() {

        System.out.println("hello");
        return "jhqhello";
    }
}
