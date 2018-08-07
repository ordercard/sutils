package com.spring.sutils.jaop;

import java.lang.reflect.Method;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 上午10:10 2018/8/7 2018
 * @Modify:
 */
public class AfterHandlerImpl extends AfterHandler {

    /* (non-Javadoc)
     * @see com.ddlab.rnd.aop.AfterHandler#handleAfter(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
     */
    @Override
    public void handleAfter(Object proxy, Method method, Object[] args) {
        //Provide your own cross cutting concern
        System.out.println("Handling after actual method execution ........");
    }

}
