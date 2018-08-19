package com.cqf.hn.tool.util;

import android.os.Environment;
import android.support.annotation.StringDef;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/**
 * @author cqf
 * @time 2018/8/19 0019  上午 12:53
 * @desc ${TODD}
 */
public class FileUtils {

    private static final ArrayList<String> voiceType = new ArrayList<String>() {{
        add("mp4");
        add("3gp");
        add("avi");
        add("mkv");
        add("wmv");
        add("mpg");
        add("vob");
        add("flv");
        add("swf");
        add("mov");
    }};

    private static final ArrayList<String> wordType = new ArrayList<String>() {{
        add("doc");
        add("docx");
        add("wps");
        add("wpt");
        add("dot");
    }};

    public static boolean createFileByDeleteOldFile(final File file) {
        if (file == null)
            return false;
        if (file.exists() && !file.delete())
            return false;
        if (!createOrExistsDir(file.getParentFile()))
            return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean createOrExistsDir(final File file) {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 获取外部存储目录
     *
     * @return /storage/emulated/0
     */
    public static File getExternalStorageDirectory() {
        return Environment.getExternalStorageDirectory().getAbsoluteFile();
    }

    public static
    @FileType
    String getFileType(String name) {
        if (name.lastIndexOf(".") != -1 && name.length() > name.lastIndexOf(".")) {
            String type = name.substring(name.lastIndexOf(".")).toLowerCase();
            if ("txt".equals(type)) {
                return FileType.TYPE_TXT;
            } else if ("mp3".equals(type)) {
                return FileType.TYPE_MUSIC;
            } else if ("pdf".equals(type)) {
                return FileType.TYPE_PDF;
            } else if (wordType.contains(type)) {
                return FileType.TYPE_WORD;
            } else if (voiceType.contains(type)) {
                return FileType.TYPE_VIDEO;
            }
        }
        return FileType.TYPE_UN_KNOW;
    }

    @StringDef({
            FileType.TYPE_MUSIC,
            FileType.TYPE_VIDEO,
            FileType.TYPE_TXT,
            FileType.TYPE_WORD,
            FileType.TYPE_PDF,
            FileType.TYPE_UN_KNOW
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface FileType {
        String TYPE_MUSIC = "music";//音乐
        String TYPE_VIDEO = "video";//视频
        String TYPE_TXT = "txt";//文本
        String TYPE_UN_KNOW = "un_know";//未知文件
        String TYPE_WORD = "word";//未知文件
        String TYPE_PDF = "pdf";//未知文件
    }
}
