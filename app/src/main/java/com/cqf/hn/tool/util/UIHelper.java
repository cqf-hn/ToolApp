package com.cqf.hn.tool.util;

import android.app.Activity;
import android.content.Intent;

import com.cqf.hn.tool.activity.FileActivity;
import com.cqf.hn.tool.activity.TTSActivity;
import com.cqf.hn.tool.activity.TTSSettingActivity;

/**
 * @author cqf
 * @time 2018/8/18 0018  下午 11:19
 * @desc ${TODD}
 */
public class UIHelper {

    public static void showTTS(Activity activity) {
        Intent intent = new Intent(activity,TTSActivity.class);
        activity.startActivity(intent);
    }

    public static void showTTSSetting(Activity activity) {
        Intent intent = new Intent(activity,TTSSettingActivity.class);
        activity.startActivity(intent);
    }

    public static void showFile(Activity activity) {
        Intent intent = new Intent(activity,FileActivity.class);
        activity.startActivity(intent);
    }
}
