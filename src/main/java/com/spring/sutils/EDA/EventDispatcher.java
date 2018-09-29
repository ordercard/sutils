package com.spring.sutils.EDA;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 17:33 2018/9/28 2018
 * @Modify:
 */
public class EventDispatcher implements DynamicRouter<Message> {


    private final Map<Class<? extends Message>,Channel> routerTable;



    @Override
    public void registerChannel(Class<? extends Message> messageType, Channel<? extends Message> channel) {

        this.routerTable.put(messageType,channel);
    }

    @Override
    public void dispatch(Message msg) {
        if (routerTable.containsKey(msg.getType())){
            routerTable.get(msg.getType()).dispatch(msg);
        }else {

            throw  new MessageMatcherException("type can,t match the chanel for"+msg.getType());
        }
    }

    public EventDispatcher() {

        this.routerTable=new HashMap<>();
    }
}
