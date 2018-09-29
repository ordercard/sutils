package com.spring.sutils.EDA;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 17:28 2018/9/28 2018
 * @Modify:
 */
public interface Channel<E extends  Message> {

    void  dispatch(E msg);
}

