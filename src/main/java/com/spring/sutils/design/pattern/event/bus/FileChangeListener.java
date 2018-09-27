package com.spring.sutils.design.pattern.event.bus;


/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 17:54 2018/9/27 2018
 * @Modify:
 */
public class FileChangeListener {

    @SubScribe
    public void  onChange(FileChangeEvent fileChangeEvent){
        System.out.printf("%s-%s\n",fileChangeEvent.getPath(),fileChangeEvent.getKind());

    }


}
