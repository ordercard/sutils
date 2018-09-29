package com.spring.sutils.EDA;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 17:26 2018/9/28 2018
 * @Modify:
 */
public interface Message {

    Class<? extends Message> getType();
}
