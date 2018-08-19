package com.cqf.hn.tool.event;

import com.cqf.hn.tool.util.EventBusUtil;

import java.io.File;

/**
 * @author cqf
 * @time 2018/8/19 0019  下午 2:53
 * @desc ${TODD}
 */
public class ShowNewFileEvent {

    private File file;

    private ShowNewFileEvent() {
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public static ShowNewFileEvent getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void send(File file) {
        this.file = file;
        EventBusUtil.post(this);
    }

    private static class SingletonHolder {
        private static final ShowNewFileEvent INSTANCE = new ShowNewFileEvent();
    }
}
