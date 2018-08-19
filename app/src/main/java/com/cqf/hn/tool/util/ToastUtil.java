package com.cqf.hn.tool.util;

import android.app.Activity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cqf.hn.tool.R;
import com.cqf.hn.tool.base.BaseApplication;

public class ToastUtil {
    private static String lastToast = "";
    private static long lastToastTime;
    private static Toast toast;

    private ToastUtil() {
    }

    private static class Singleton {
        private static final ToastUtil instance = new ToastUtil();
    }

    public static ToastUtil getInstance() {
        return Singleton.instance;
    }

    public static ToastUtil init() {
        return getInstance();
    }

    /**
     * 显示toast信息
     *
     * @param message
     */
    public static void showToast(int message) {
        showToast(BaseApplication.getInstance().getResources().getString(message));
    }

    /**
     * 显示toast信息
     *
     * @param message
     */
    public static void showToast(final String message) {
        Activity currentActivity = ActivityManager.getAppManager().currentActivity();
        if (currentActivity != null) {
            currentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!TextUtils.isEmpty(message)) {
                        LogUtils.d(message);
                        long time = System.currentTimeMillis();
                        if (!message.equalsIgnoreCase(lastToast) || Math.abs(time - lastToastTime) > 2000) {
                            if (toast == null) {
                                View view = LayoutInflater.from(BaseApplication.getInstance()).inflate(R.layout.widget_toast, null);
                                toast = new Toast(BaseApplication.getInstance());
                                toast.setView(view);
                                toast.setGravity(Gravity.BOTTOM, 0, 100);
                                toast.setDuration(Toast.LENGTH_SHORT);
                            }
                            ((TextView) toast.getView().findViewById(R.id.title)).setText(message);
                            toast.getView().findViewById(R.id.icon).setVisibility(View.GONE);
                            toast.show();
                            lastToast = message;
                            lastToastTime = System.currentTimeMillis();
                        }
                    }
                }
            });
        }
    }

//    public static void showToastWithIcon(int message) {
//        showToastWithIcon(string(message), R.mipmap.ic_toast_ok);
//    }
//
//    public static void showToastWithIcon(final String message) {
//        showToastWithIcon(message, R.mipmap.ic_toast_ok);
//    }

    public static void showToastWithIcon(final String message, final int iconResId) {
        Activity currentActivity = ActivityManager.getAppManager().currentActivity();
        if (currentActivity != null) {
            currentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!TextUtils.isEmpty(message)) {
                        LogUtils.d(message);
                        long time = System.currentTimeMillis();
                        if (!message.equalsIgnoreCase(lastToast) || Math.abs(time - lastToastTime) > 2000) {
                            if (toast == null) {
                                View view = LayoutInflater.from(BaseApplication.getInstance()).inflate(R.layout.widget_toast, null);
                                toast = new Toast(BaseApplication.getInstance());
                                toast.setView(view);
                                toast.setGravity(Gravity.BOTTOM, 0, 100);
                                toast.setDuration(Toast.LENGTH_SHORT);
                            }
                            TextView toastText = (TextView) toast.getView().findViewById(R.id.title);
                            if (toastText != null) {
                                toastText.setText(message);
                            }
                            ImageView icon = (ImageView) toast.getView().findViewById(R.id.icon);
                            icon.setVisibility(View.VISIBLE);
                            icon.setImageResource(iconResId);
                            toast.show();
                            lastToast = message;
                            lastToastTime = System.currentTimeMillis();
                        }
                    }
                }
            });
        }
    }
}
