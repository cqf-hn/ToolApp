package com.cqf.hn.tool.util;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.util.ArrayMap;

import java.util.Map;

public class SpUtil {
    private static boolean isAtLeastGingerbread;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            isAtLeastGingerbread = true;
        }
    }

    private SpUtil() {
    }

    private static class Singleton {
        private static final SpUtil instance = new SpUtil();
    }

    public static SpUtil getInstance() {
        return Singleton.instance;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void apply(SharedPreferences.Editor editor) {
        if (isAtLeastGingerbread) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    public static void setProperty(SharedPreferences sp, String key, int value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        apply(editor);
    }

    public static void setProperty(SharedPreferences sp, String key, boolean value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        apply(editor);
    }

    public static void setProperty(SharedPreferences sp, String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        apply(editor);
    }

    public static void setProperty(SharedPreferences sp, String key, long value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        apply(editor);
    }

    public static void setProperty(SharedPreferences sp, String key, float value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value);
        apply(editor);
    }

    public static void setProperty(SharedPreferences sp, ArrayMap<String, Object> map) {
        SharedPreferences.Editor editor = sp.edit();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                editor.putString(key, (String) value);
            } else if (value instanceof Boolean) {
                editor.putBoolean(key, ((Boolean) value).booleanValue());
            } else if (value instanceof Float) {
                editor.putFloat(key, ((Float) value).floatValue());
            } else if (value instanceof Integer) {
                editor.putInt(key, ((Integer) value).intValue());
            } else if (value instanceof Long) {
                editor.putLong(key, ((Long) value).longValue());
            }
        }
        apply(editor);
    }

    public static void removeProperty(SharedPreferences sp, String key) {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    public static boolean getProperty(SharedPreferences sp, String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public static String getProperty(SharedPreferences sp, String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public static int getProperty(SharedPreferences sp, String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public static long getProperty(SharedPreferences sp, String key, long defValue) {
        return sp.getLong(key, defValue);
    }

    public static float getProperty(SharedPreferences sp, String key, float defValue) {
        return sp.getFloat(key, defValue);
    }

    /**
     */
    public static void clearData(SharedPreferences sp) {
        SharedPreferences.Editor shareEditor = sp
                .edit();
        // CreateSharePre(context);
        shareEditor.clear();
        apply(shareEditor);
    }
}
