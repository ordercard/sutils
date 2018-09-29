package com.spring.sutils.EDA;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 17:29 2018/9/28 2018
 * @Modify:
 */
public interface DynamicRouter<E extends  Message> {

    void  registerChannel(Class<? extends E> messageType,Channel<? extends  E> channel);

    /*
    为相应的Channel分配message
     */
    void  dispatch(E msg);
}
