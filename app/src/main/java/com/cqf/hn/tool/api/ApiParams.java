package com.cqf.hn.tool.api;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.ArrayMap;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * api相关信息
 * Created by XuYang on 2016/12/29.
 */

public class ApiParams {
    private boolean hasFile;
    private Map<String, ArrayList<String>> params;
    private Map<String, ArrayList<File>> files;

    public ApiParams() {
//        this(true);
    }

    public ApiParams(boolean isAddClientkey) {
//        if (isAddClientkey) {
//            addPassengerId();
//        }
    }

    /**
     * 添加请求参数
     *
     * @param key
     * @param value
     * @return
     */
    public synchronized ApiParams add(String key, String value) {
        if (!TextUtils.isEmpty(key) && value != null) {
            if (this.params == null) {
                this.params = new ArrayMap<String, ArrayList<String>>();
            }
            ArrayList<String> oldValues = this.params.get(key);
            if (oldValues == null) {
                oldValues = new ArrayList<String>();
                params.put(key, oldValues);
            }
            oldValues.add(value);
        }
        return this;
    }

    /**
     * 添加多个请求参数
     *
     * @param key
     * @param values
     */
    public synchronized ApiParams add(String key, ArrayList<String> values) {
        if (!TextUtils.isEmpty(key) && values != null) {
            if (this.params == null) {
                this.params = new ArrayMap<String, ArrayList<String>>();
            }
            ArrayList<String> oldValues = this.params.get(key);
            if (oldValues == null) {
                oldValues = new ArrayList<String>();
                this.params.put(key, oldValues);
            }
            oldValues.addAll(values);
        }
        return this;
    }

    /**
     * 添加文件
     *
     * @param key
     * @param file
     * @return
     */
    public synchronized ApiParams addFile(String key, File file) {
        if (!TextUtils.isEmpty(key) && file != null) {
            if (this.files == null) {
                this.files = new ArrayMap<String, ArrayList<File>>();
            }
            ArrayList<File> oldFiles = this.files.get(key);
            if (oldFiles == null) {
                oldFiles = new ArrayList<File>();
                this.files.put(key, oldFiles);
            }
            oldFiles.add(file);
        }
        return this;
    }

    /**
     * 添加多个文件
     *
     * @param key
     * @param files
     * @return
     */
    public synchronized ApiParams addFile(@NonNull String key, Collection<File> files) {
        if (!TextUtils.isEmpty(key) && files != null) {
            if (this.files == null) {
                this.files = new ArrayMap<String, ArrayList<File>>();
            }
            ArrayList<File> oldFiles = this.files.get(key);
            if (oldFiles == null) {
                oldFiles = new ArrayList<File>();
                this.files.put(key, oldFiles);
            }
            oldFiles.addAll(files);
        }
        return this;
    }

    public boolean isHasFile() {
        return hasFile;
    }

    public void setHasFile(boolean hasFile) {
        this.hasFile = hasFile;
    }

    public Map<String, ArrayList<String>> getParams() {
        return params;
    }

    public Map<String, ArrayList<File>> getFiles() {
        return files;
    }

    public void addPassengerId() {
//        add("passenger_id", UserHelper.getInstance().getPassengerId());
    }

    public void addToken() {
//        add("token", UserHelper.getInstance().getToken());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (params != null) {
            for (Map.Entry<String, ArrayList<String>> entry : params.entrySet()) {
                ArrayList<String> values = entry.getValue();
                if (values != null) {
                    for (int i = 0; i < values.size(); i++) {
                        String value = values.get(i);
                        if (value != null) {
                            builder.append(" ").append(entry.getKey()).append(" = ").append(value).append("\n");
                        }
                    }
                }
            }
        }
        if (files != null) {
            for (Map.Entry<String, ArrayList<File>> entry : files.entrySet()) {
                ArrayList<File> fileList = entry.getValue();
                if (fileList != null) {
                    for (int i = 0; i < fileList.size(); i++) {
                        File file = fileList.get(i);
                        if (file != null) {
                            builder.append(" ").append(entry.getKey()).append(" = ").append(file.getAbsolutePath()).append("\n");
                        }
                    }
                }
            }
        }
        return builder.toString();
    }
}
