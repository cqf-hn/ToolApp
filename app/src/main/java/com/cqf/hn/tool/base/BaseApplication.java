package com.cqf.hn.tool.base;

import android.app.Application;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.squareup.leakcanary.LeakCanary;

/**
 * @author cqf
 * @time 2018/5/15 0015  上午 10:03
 * @desc ${TODD}
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;
    private Handler handler = new Handler();

    public static BaseApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LeakCanary.install(this);
        MultiDex.install(this);
    }

    public void post(Runnable runnable) {
        handler.post(runnable);
    }
}
