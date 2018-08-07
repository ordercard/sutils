package com.spring.sutils.jaop;

import java.lang.reflect.Method;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 上午10:08 2018/8/7 2018
 * @Modify:
 */
public abstract class AfterHandler extends AbstractHandler {

    /**
     * Handles after the execution of method.
     *
     * @param proxy the proxy
     * @param method the method
     * @param args the args
     */
    public abstract void handleAfter(Object proxy, Method method, Object[] args);

    /* (non-Javadoc)
     * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object result = method.invoke(getTargetObject(), args);
        handleAfter(proxy, method, args);
        return result;
    }
}
