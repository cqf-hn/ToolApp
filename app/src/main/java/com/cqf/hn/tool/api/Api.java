package com.cqf.hn.tool.api;

/**
 * Created by Admin on 2018/3/31.
 */

public interface Api {
    // get异步
    void getAsync(String url, ApiParams apiParams, Class<? extends BaseResponse> cls, CallBack callBack);

    // get同步
    BaseResponse getSync(String url, ApiParams apiParams, Class<? extends BaseResponse> cls);

    // post同步
    void postAsync(String url, ApiParams apiParams, Class<? extends BaseResponse> cls, CallBack callBack);

    // post同步
    BaseResponse postSync(String url, ApiParams apiParams, Class<? extends BaseResponse> cls);
}
