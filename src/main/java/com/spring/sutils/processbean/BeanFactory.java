package com.spring.sutils.processbean;

import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 下午1:20 2018/7/23 2018
 * @Modify:
 */
public class BeanFactory {
    public List<Object>  createBeans(List<Class<?>> ls){

         return  ls.stream().map(s->{return  createBean(s);}).collect(Collectors.toList());
    }
//创建一个Bean

    Object createBean(Class<?> cls){
        //添加到容器
        Object s= null;
        try {
            s = cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return new BeanWrapper(s);

    }

}
//包装代理

class BeanWrapper {

    private Object bean;

    public BeanWrapper(Object bean) {

        this.bean = bean;

    }

    @Override

    public String toString() {

        return "Wrapper(" + this.bean.toString() + ")";

    }}
class Processor implements BeanPostProcessor {

    //初始化之前

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {

        return bean;

    }

    //初始化之后

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {

        System.out.println("Bean '" + beanName + "' created : " + bean.toString());

        return bean;

    }}