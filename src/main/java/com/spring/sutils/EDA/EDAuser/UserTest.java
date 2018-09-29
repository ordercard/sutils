package com.spring.sutils.EDA.EDAuser;

import com.spring.sutils.EDA.AsyncEventDispatcher;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 16:07 2018/9/29 2018
 * @Modify:
 */
public class UserTest {
    public static void main(String[] args) {
        final AsyncEventDispatcher asyncEventDispatcher =new AsyncEventDispatcher();
        asyncEventDispatcher.registerChannel(UserOnLineEvent.class,new UserOnlineEventChannel());
        asyncEventDispatcher.registerChannel(UserOFFLineEvent.class,new UserOFFlineEventChannel());
        asyncEventDispatcher.registerChannel(UserChatEvent.class,new UserChatEventChannel());

        new UserThread(new User(" JIA "),asyncEventDispatcher).start();
        new UserThread(new User(" LI "),asyncEventDispatcher).start();
        new UserThread(new User(" HUIQIANG "),asyncEventDispatcher).start();
    }

}
