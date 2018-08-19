package com.cqf.hn.tool.util;


import com.cqf.hn.tool.BuildConfig;

/**
 * Created by su on 2016/7/2 08:49
 * 619389279@qq.com
 */
class LogManager {

    static final int LOG_LEVEL_NO    = 0;
    static final int LOG_LEVEL_VERBOSE = 1;
    static final int LOG_LEVEL_DEBUG = 2;
    static final int LOG_LEVEL_INFO  = 3;
    static final int LOG_LEVEL_WARN  = 4;
    static final int LOG_LEVEL_ERROR = 5;
    static final int LOG_LEVEL_ALL   = 99;
    
    /**
     * 当前日志打印级别
     */
    private static int printLevel = LOG_LEVEL_ALL;

    /**
     * 指定日志级别是否达到打印级别
     * @param level
     * @return
     */
    static boolean canPrint(int level) {
        return printLevel >= level && BuildConfig.DEBUG;
    }
}
