package com.spring.sutils.EDA;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 17:32 2018/9/28 2018
 * @Modify:
 */
public class Event implements  Message {


    @Override
    public Class<? extends Message> getType() {
        return getClass();
    }

}
