package com.spring.sutils.design.pattern.event.bus;

import java.io.IOException;
import java.nio.file.*;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 20:17 2018/9/25 2018
 * @Modify:
 */
public class DirectoryTargetMonitor {

    private  WatchService watchService;

    private  final EventBus eventBus;

    private volatile  boolean start=false;

    private final Path path;

    public DirectoryTargetMonitor(EventBus eventBus, String path) {
     this(eventBus,path,"");
    }

    public DirectoryTargetMonitor(EventBus eventBus, String path, String ...s) {
        this.eventBus=eventBus;
        this.path= Paths.get(path,s);
    }

    public void startMonitor() throws IOException {
        this.watchService= FileSystems.getDefault().newWatchService();
            //为该路径注册感兴趣的事件
        this.path.register(watchService,StandardWatchEventKinds.ENTRY_MODIFY,StandardWatchEventKinds.ENTRY_CREATE);
        this.start=true;
        while (start){
            WatchKey watchKey = null;

            try {
                watchKey=watchService.take();
                watchKey.pollEvents().forEach(event->{
                WatchEvent.Kind<?> kind=event.kind();
                Path path =(Path) event.context();
                Path child = DirectoryTargetMonitor.this.path.resolve(path);
                //提交FileChangeEvent 到EventBus
                    eventBus.post(new FileChangeEvent(child,kind));

                });
            } catch (InterruptedException e) {
                this.start=false;
                e.printStackTrace();
            }finally {
                if(watchKey!=null){
                    watchKey.reset();
                }
            }

        }
    }

    public void stopMonitor() throws IOException {


        System.out.println("结束监控目录");
        Thread.currentThread().interrupt();
        this.start=false;
        this.watchService.close();
        System.out.println("关闭完成");

    }




}
