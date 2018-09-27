package com.spring.sutils.design.pattern.event.bus;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 17:59 2018/9/27 2018
 * @Modify:
 */
public class DictoryFileTest {


    public static void main(String[] args) throws IOException {

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
        final EventBus  eventBus =new AsyncEventBus((ThreadPoolExecutor)executor);
        eventBus.register(new FileChangeListener());
        DirectoryTargetMonitor monitor =new DirectoryTargetMonitor(eventBus,"/Users/hq/");
        monitor.startMonitor();

    }
}
