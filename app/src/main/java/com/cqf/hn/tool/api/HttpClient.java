package com.cqf.hn.tool.api;

/**
 * Created by Admin on 2018/4/8.
 */

public class HttpClient {

    private HttpClient() {
    }

    public static HttpClient getClient() {
        return SingletonHolder.INSTANCE;
    }



    private static class SingletonHolder {
        private static final HttpClient INSTANCE = new HttpClient();
    }

    public void cancelTag(String tag) {
        ApiImpl.getApi().cancelTag(tag);
    }
}
