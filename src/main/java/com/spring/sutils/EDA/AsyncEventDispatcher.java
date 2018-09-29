package com.spring.sutils.EDA;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 14:44 2018/9/29 2018
 * @Modify:
 */
public class AsyncEventDispatcher implements  DynamicRouter<Event> {


    private final Map<Class<? extends Event>,AsyncChannel> routerTable;



    public AsyncEventDispatcher() {
        this.routerTable =  new HashMap<>();
    }

    @Override
    public void registerChannel(Class<? extends Event> messageType, Channel<? extends Event> channel) {

        if (!(channel instanceof AsyncChannel)){
            throw  new IllegalArgumentException("不能进行强值类型装换 channel");
        }
        this.routerTable.put(messageType,(AsyncChannel) channel);
    }


    @Override
    public void dispatch(Event msg) {

        if (routerTable.containsKey(msg.getType())){
            routerTable.get(msg.getType()).dispatch(msg);
        }else {

            throw  new MessageMatcherException("type can,t match the chanel for"+msg.getType());
        }

    }

    public void shutDown(){


        routerTable.values().forEach(AsyncChannel::stop);
    }
}
