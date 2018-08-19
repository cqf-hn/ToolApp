package com.cqf.hn.tool.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.cqf.hn.tool.base.BaseApplication;

/**
 * @author cqf
 * @time 2018/8/19 0019  上午 12:59
 * @desc ${TODD}
 */
public class SpHelper {
        public static SharedPreferences getTTS() {
            return BaseApplication.getInstance().getSharedPreferences(SpConst.TTS.spName, Context.MODE_PRIVATE);
        }

}
