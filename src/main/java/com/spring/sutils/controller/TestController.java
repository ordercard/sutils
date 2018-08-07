package com.spring.sutils.controller;

import com.spring.sutils.jaop.SpringAop.Persion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 上午11:27 2018/8/7 2018
 * @Modify:
 */
@RestController
public class TestController {
    @Autowired
    Persion persion;

    @GetMapping("/test")
    public  void test(){

        System.out.println("听");
        persion.sayHello();
        System.out.println(persion.getClass());


    }



}
