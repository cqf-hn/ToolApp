package com.cqf.hn.tool.event;

import com.cqf.hn.tool.util.EventBusUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * @author cqf
 * @time 2018/8/19 0019  下午 4:17
 * @desc ${TODD}
 */
public class SelectFileEvent {

    private ArrayList<File> data;

    private SelectFileEvent() {
    }

    public ArrayList<File> getData() {
        return data;
    }

    public void setData(ArrayList<File> data) {
        this.data = data;
    }

    public static SelectFileEvent getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void send(ArrayList<File> data) {
        this.data = data;
        EventBusUtil.post(this);
    }

    private static class SingletonHolder {
        private static final SelectFileEvent INSTANCE = new SelectFileEvent();
    }
}
