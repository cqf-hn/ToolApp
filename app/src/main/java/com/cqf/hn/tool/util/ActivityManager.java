package com.cqf.hn.tool.util;

import android.content.Context;

import com.cqf.hn.tool.base.BaseActivity;

import java.util.Stack;

public class ActivityManager {

    private final static Stack<BaseActivity> activityStack = new Stack<>();

    private ActivityManager() {
    }

    private static class Singleton {
        private static final ActivityManager instance = new ActivityManager();
    }

    /**
     * 单一实例
     */
    public static ActivityManager getAppManager() {
        return Singleton.instance;
    }

    public void closeActivtyForExitLogin(Class notClostActivity) {
        BaseActivity act = null;
        for (BaseActivity activity : activityStack) {
            if (activity.getClass().equals(notClostActivity)) {
                act = activity;
                continue;
            }
            activity.finish();
        }
        activityStack.clear();
        if (act != null) {
            activityStack.add(act);
        }
    }


    /**
     * 获取指定的Activity
     *
     * @author kymjs
     */
    public static BaseActivity getActivity(Class<?> cls) {
        if (activityStack != null)
            for (BaseActivity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        return null;
    }

    public BaseActivity getActivity(String className) {
        for (BaseActivity activity : activityStack) {
            if (activity.getClass().getSimpleName().equals(className)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(BaseActivity activity) {
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public BaseActivity currentActivity() {
        try {
            BaseActivity activity = activityStack.lastElement();
            return activity;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        try {
            BaseActivity activity = activityStack.lastElement();
            finishActivity(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(BaseActivity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(BaseActivity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (BaseActivity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                finishActivity(activityStack.get(i));
                break;
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void appExit(Context context) {
        try {
            finishAllActivity();
        } catch (Exception e) {
        }
    }

    public Stack<BaseActivity> getActivityStack() {
        return activityStack;
    }
}
