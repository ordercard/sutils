package com.spring.sutils.processbean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 下午2:11 2018/7/23 2018
 * @Modify:
 */
public class anotest {

    public static void main(String[] args) {

        List<Class<?>> classes = Arrays.asList(new Class<?>[]{MyBean1ano.class, MyBean2ano.class});

        List<Object> ins = new BeanFactoryano().createBeans(classes);

        System.out.println("Result:" + ins.toString());

    }

}
@Target({ElementType.METHOD})

@Retention(RetentionPolicy.RUNTIME)

@interface before {}



@Target({ElementType.METHOD})

@Retention(RetentionPolicy.RUNTIME)

@interface after{}


//预设的Bean1

class MyBean1ano {

    @Override
    public String toString() {

        return "MyBean1 Ins";

    }



    @before

    public void init() {

        System.out.println("Before Init:" + this.toString());

    }

}



//预设的Bean2

class MyBean2ano {

    @Override
    public String toString() {

        return "MyBean2 Ins";

    }



    @after

    public void post() {

        System.out.println("After Init:" + this.toString());

    }

}