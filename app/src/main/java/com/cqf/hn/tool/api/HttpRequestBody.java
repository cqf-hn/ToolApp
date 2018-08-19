package com.cqf.hn.tool.api;

import android.text.TextUtils;

import com.cqf.hn.tool.util.LogUtils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created by Bob on 2016/7/20 22:56
 * SkySeraph00@126.com
 */
public class HttpRequestBody extends RequestBody {

    private String mData;
    private byte[] mBytes;

    public HttpRequestBody(String json) {
        mData = json;
    }

    public String getJson() {
        return mData;
    }

    public void setJson(String json) {
        mData = json;
    }

    public void setBytes(byte[] bytes) {
        mBytes = bytes;
    }


    @Override
    public MediaType contentType() {
        return MediaType.parse("application/json; charset=utf-8");
    }

    @Override
    public long contentLength() throws IOException {
        String json = mData;
        if (TextUtils.isEmpty(json)) {
            return 0;
        }
        return json.getBytes("utf-8").length;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        String json = mData;
        if (TextUtils.isEmpty(json)) {
            return;
        }
        sink.writeUtf8(json);
        LogUtils.d("HttpRequestBody params="+mData.toString());
    }
}