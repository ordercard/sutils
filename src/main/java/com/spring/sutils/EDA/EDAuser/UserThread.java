package com.spring.sutils.EDA.EDAuser;

import com.spring.sutils.EDA.AsyncEventDispatcher;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 15:59 2018/9/29 2018
 * @Modify:
 */
public class UserThread extends  Thread {

    private  final  User user;

    private final AsyncEventDispatcher asyncEventDispatcher;


    public  UserThread(User user,AsyncEventDispatcher asyncEventDispatcher){
        super (user.getUsername());
        this.user = user;
        this.asyncEventDispatcher = asyncEventDispatcher;
    }

    @Override
    public void  run(){

        asyncEventDispatcher.dispatch(new UserOnLineEvent(user));
        IntStream.range(0,5).forEach(x->{asyncEventDispatcher.dispatch(new UserChatEvent(user,getName()+"hello"+x));});
        try {
            TimeUnit.SECONDS.sleep(current().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            asyncEventDispatcher.dispatch(new UserOFFLineEvent(user));
        }
    }

    private Random current() {
        return  new Random();
    }
}
