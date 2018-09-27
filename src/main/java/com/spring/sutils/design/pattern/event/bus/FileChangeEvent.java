package com.spring.sutils.design.pattern.event.bus;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 17:48 2018/9/27 2018
 * @Modify:
 */
public class FileChangeEvent {
    private final Path path;

    private final  WatchEvent.Kind<?> kind;

    public FileChangeEvent(Path child, WatchEvent.Kind<?> kind) {
        this.path=child;
        this.kind=kind;

    }

    public Path getPath() {
        return path;
    }

    public WatchEvent.Kind<?> getKind() {
        return kind;
    }
}
