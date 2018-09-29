package com.spring.sutils.EDA;

import java.util.concurrent.TimeUnit;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 15:04 2018/9/29 2018
 * @Modify:
 */
public class AsyncDispatchExample {


    static  class  AEHandle extends  AsyncChannel{

        private  final  AsyncEventDispatcher asyncEventDispatcher;

        AEHandle( AsyncEventDispatcher asyncEventDispatcher){
            this.asyncEventDispatcher=asyncEventDispatcher;
        }

        @Override
        protected void handle(Event msg) {

            EventDispatcherExample.InputEvent inputEvent =(EventDispatcherExample.InputEvent) msg;
            System.out.println("y="+inputEvent.getY()+"  x="+inputEvent.getX());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            asyncEventDispatcher.dispatch(new EventDispatcherExample.ResultEvent(inputEvent.getX()+inputEvent.getY()));
        }
    }



    static  class  AsResHandle extends  AsyncChannel{


        @Override
        protected void handle(Event msg) {
              EventDispatcherExample.ResultEvent resultEvent= (EventDispatcherExample.ResultEvent)msg;
            System.out.println(resultEvent.getRes());


        }
    }


    public static void main(String[] args) {
        AsyncEventDispatcher asyncEventDispatcher =new AsyncEventDispatcher();
        asyncEventDispatcher.registerChannel(EventDispatcherExample.InputEvent.class,new AEHandle(asyncEventDispatcher));
        asyncEventDispatcher.registerChannel(EventDispatcherExample.ResultEvent.class, new AsResHandle());

        asyncEventDispatcher.dispatch(new EventDispatcherExample.InputEvent(1,2));
    }

}
