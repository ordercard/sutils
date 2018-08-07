package com.spring.sutils.jaop;

import java.lang.reflect.Method;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 上午10:11 2018/8/7 2018
 * @Modify:
 */
public class BeforeHandlerImpl extends BeforeHandler {

    /* (non-Javadoc)
     * @see com.ddlab.rnd.aop.BeforeHandler#handleBefore(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
     */
    @Override
    public void handleBefore(Object proxy, Method method, Object[] args) {
        //Provide your own cross cutting concern
        System.out.println("Handling before actual method execution ........");
    }
}