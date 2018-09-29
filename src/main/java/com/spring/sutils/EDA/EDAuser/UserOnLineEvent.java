package com.spring.sutils.EDA.EDAuser;

import com.spring.sutils.EDA.Event;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 15:48 2018/9/29 2018
 * @Modify:
 */
public class UserOnLineEvent extends Event {

    private final  User user;

    public UserOnLineEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }



}
