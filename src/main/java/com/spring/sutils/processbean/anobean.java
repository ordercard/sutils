package com.spring.sutils.processbean;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 下午2:09 2018/7/23 2018
 * @Modify:
 */
public class anobean {



}
class BeanFactoryano {

    public List<Object> createBeans(List<Class<?>> clslist){

      return  clslist.stream().map(s->{ return  createBean(s);}).collect(Collectors.toList());

    }

    Object createBean(Class<?> cls)  {

        BeanWrapper wrapper = null;

        Object ins = null;
        try {
            ins = cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        /**这里增加了一个Handle对象。

         Handle会对注解进行处理，确定添加容器前后的执行方法。*/

        Handle handle = processBeforeAndAfterHandle(ins);

        try {
            handle.exeBefore();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        wrapper = new BeanWrapper(ins);

        try {
            handle.exeAfter();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return wrapper;

    }



    // 通过反射来确定Bean被添加到容器前后的执行方法。

    private Handle processBeforeAndAfterHandle(Object obj) {

        Method[] methods = obj.getClass().getDeclaredMethods();

        Handle handle = new Handle(obj);

        for(Method method : methods) {

            Annotation bef = method.getAnnotation(before.class);

            Annotation aft = method.getAnnotation(after.class);

            if(null != bef) {
                handle.setBefore(method);
            }

            if(null != aft) {
                handle.setBefore(method);
            }

        }

        return handle;

    }

}

class Handle{

    Object instance;

    Method before;

    Method after;

    Handle(Object ins){

        this.instance = ins;

    }

    void setBefore(Method method) {

        this.before = method;

    }

    void setAfter(Method method) {

        this.after = method;

    }

    void exeBefore() throws InvocationTargetException, IllegalAccessException {

        if(null != this.before) {

            this.before.invoke(this.instance, null);

        }

    }

    void exeAfter() throws InvocationTargetException, IllegalAccessException {

        if(null != this.after) {

            this.after.invoke(this.instance, null);

        }

    }

}



