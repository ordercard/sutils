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
/*

不用进行同步的原因是 我们的event时间对象被设计成不可变的对象，因为event在经过每一个channel的时间都会创建一个全新的event ，多个线程之间不会出现资源竞争，因此不需要进行同步的保护。 联想生产者消费者模式
如果使用 EDA框架进行修改。就不需要多个线程之间的交互，也不需要多个线程之间进行同步。有消息提交，消费的handler自然而然的就会触发消费Message。
end

 */
}
