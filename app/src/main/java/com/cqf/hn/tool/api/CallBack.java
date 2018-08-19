package com.cqf.hn.tool.api;

/**
 * Created by Admin on 2018/4/4.
 */

public interface CallBack {

    /**
     * @param tag 方法名
     */
    void onApiBefore(String tag);

    /**
     * @param tag    方法名
     * @param result 接口数据的基类
     */
    void onApiSuccess(String tag, BaseResponse result);

    /**
     * @param tag 方法名
     * @param tip 提示语
     * @param e   错误异常
     */
    void onApiFailure(String tag, String tip, Exception e);

    /**
     * @param tag    方法名
     * @param cls    解析生成的类名
     * @param result 解析的数据
     */
    void onParseError(String tag, String cls, String result);
}
