package com.cqf.hn.tool.api;

import android.text.TextUtils;

import com.cqf.hn.tool.util.LogUtils;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class LoggerInterceptor implements Interceptor {
    public static final String TAG = "OkHttpUtils";
    private boolean showResponse;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        return logForResponse(response);
    }

    private Response logForResponse(Response response) {
        try {
            LogUtils.d("========Response start========");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            LogUtils.d("url : ".concat(clone.request().url().toString()));
            LogUtils.d("code : ".concat(Integer.valueOf(clone.code()).toString()));
            LogUtils.d("protocol : ".concat(clone.protocol().toString()));
            if (!TextUtils.isEmpty(clone.message()))
                LogUtils.d("message : ".concat(clone.message()));

            if (showResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        LogUtils.d("responseBody's contentType : ".concat(mediaType.toString()));
                        if (isText(mediaType)) {
                            String resp = body.string();
                            LogUtils.d("responseBody's content : ".concat(resp));
                            body = ResponseBody.create(mediaType, resp);
                            return response.newBuilder().body(body).build();
                        } else {
                            LogUtils.d("responseBody's content : maybe [file part] , too large too print , ignored!");
                        }
                    }
                }
            }
            LogUtils.d("========Response end========");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private void logForRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            LogUtils.d("========Request start========");
            LogUtils.d("method : ".concat(request.method()));
            LogUtils.d("url : ".concat(url));
            if (headers != null && headers.size() > 0) {
                LogUtils.d("headers : ".concat(headers.toString()));
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    LogUtils.d("requestBody's contentType : ".concat(mediaType.toString()));
                    if (isText(mediaType)) {
                        LogUtils.d("requestBody's content : ".concat(bodyToString(request)));
                    } else {
                        LogUtils.d("requestBody's content : maybe [file part] , too large too print , ignored!");
                    }
                }
            }
            LogUtils.d("========Request end========");
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")
                    )
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
