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

    private final WatchService watchService;

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


    }
}
