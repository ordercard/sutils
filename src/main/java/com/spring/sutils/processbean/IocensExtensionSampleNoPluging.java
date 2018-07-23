package com.spring.sutils.processbean;

import java.util.Arrays;
import java.util.List;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 下午2:07 2018/7/23 2018
 * @Modify:
 */
//执行

public class IocensExtensionSampleNoPluging {

    public static void main(String[] args) {

        List<Class<?>> classes = Arrays.asList(new Class<?>[]{MyBean1.class, MyBean2.class});

        List<Object> ins = new ModifyBeanFactory().createBeans(classes);

        System.out.println("Result:" + ins.toString());

    }

}



//新建一个BeanFactory的派生类，并修改createBean的实现，添加使用者的处理逻辑

class ModifyBeanFactory extends BeanFactory {

    @Override
    Object createBean(Class<?> cls){

        Object ins = null;
        try {
            ins = cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //添加容器之前的处理

        BeanWrapper wrapper = new BeanWrapper(ins);

        //添加容器之后的处理

        return wrapper;

    }}