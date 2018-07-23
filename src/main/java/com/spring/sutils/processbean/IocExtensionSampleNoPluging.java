package com.spring.sutils.processbean;

import java.util.Arrays;
import java.util.List;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 下午2:05 2018/7/23 2018
 * @Modify:
 */
public class IocExtensionSampleNoPluging {

    public static void main(String[] args) {

        List<Class<?>> classes = Arrays.asList(new Class<?>[]{MyBean1.class, MyBean2.class});

        List<Object> ins = new BeanFactory().createBeans(classes);

        System.out.println("Result:" + ins.toString());

    }

}
//Bean1，由使用者编码

class MyBean1 {

    @Override
    public String toString() {

        return "MyBean1 Ins";

    }

}



//Bean2，使用者编码

class MyBean2 {

    @Override
    public String toString() {

        return "MyBean2 Ins";

    }

}