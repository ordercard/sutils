package com.spring.sutils.design.pattern.event.bus;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 10:10 2018/9/21 2018
 * @Modify:
 */
public class Registry {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<Subscriber>>  subscriberContainer= new ConcurrentHashMap<>();
    public void  bind(Object sub){
        List<Method> subscribethods= getSubscriberMethods(sub);
        subscribethods.forEach(m->tierSubscriber(sub,m));
        
    }

    private void tierSubscriber(Object sub, Method m) {

        final SubScribe subScribe =m.getDeclaredAnnotation(SubScribe.class);
        String topic = subScribe.topic();
        subscriberContainer.computeIfAbsent(topic,key->new ConcurrentLinkedQueue<>());
        subscriberContainer.get(topic).add(new Subscriber(sub,m));
    }

    private List<Method> getSubscriberMethods(Object sub) {
        final List<Method> methods =new ArrayList<>();
        Class<?> temp = sub.getClass();
        while (temp!=null){
            Method [] declareMethods =temp.getDeclaredMethods();
            Arrays.stream(declareMethods).filter(m->m.isAnnotationPresent(SubScribe.class) && m.getParameterCount()==1 && m.getModifiers() == Modifier.PUBLIC).forEach(methods::add);
            temp= temp.getSuperclass();

        }
        return  methods;
    }

    public void unBind(Object object){

        subscriberContainer.forEach((k,queue)->queue.forEach(s->{
            if (s.getSubscribeObject()==object){

                s.setDisable(true);
            }
        }));
    }
    public ConcurrentLinkedQueue<Subscriber> scanSubscriber(final  String topic){
        return subscriberContainer.get(topic);
    }




}
