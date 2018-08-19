package com.cqf.hn.tool.api;

import android.net.Uri;
import android.os.Looper;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.cqf.hn.tool.BuildConfig;
import com.cqf.hn.tool.base.BaseApplication;
import com.cqf.hn.tool.util.GsonUtil;
import com.cqf.hn.tool.util.LogUtils;
import com.cqf.hn.tool.util.TDevice;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Admin on 2018/3/30.
 */
public class ApiImpl implements Api {

    public static final MediaType MEDIATYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIATYPE_OCTET_STREAM = MediaType.parse("application/octet-stream");

    private static OkHttpClient client;

    static {
        client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new LoggerInterceptor())
                /**
                 * cook持久化
                 * https://blog.csdn.net/fangaoxin/article/details/6952954/
                 * http://www.freebuf.com/articles/web/42802.html?utm_source=tuicool&utm_medium=referral
                 * https://blog.csdn.net/u010386612/article/details/52216146
                 */
                .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(BaseApplication.getInstance())))
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .build();

    }

    private ApiImpl() {
    }

    public static ApiImpl getApi() {
        return SingletonHolder.INSTANCE;
    }


    // get异步
    @Override
    public void getAsync(String url, @Nullable ApiParams params, final Class<? extends BaseResponse> cls, final CallBack callback) {
        final String tag = LogUtils.getMethodTag("HttpClient.java");
        final WeakReference<CallBack> callbackRef = new WeakReference<>(callback);
        final Uri.Builder builder = Uri.parse(url).buildUpon();
        if (params != null) {
            final Map<String, ArrayList<String>> paramsList = params.getParams();
            if (paramsList != null) {
                for (Map.Entry<String, ArrayList<String>> entry : paramsList.entrySet()) {
                    ArrayList<String> values = entry.getValue();
                    if (values != null) {
                        for (int i = 0; i < values.size(); i++) {
                            String value = values.get(i);
                            builder.appendQueryParameter(entry.getKey(), value);
                        }
                    }
                }
            }
        }
        Request request = new Request.Builder().tag(tag).url(builder.build().toString()).build();
        if (BuildConfig.DEBUG) {
            LogUtils.d(tag.concat(" Start: ").concat(builder.build().toString()));
        }
        Call call = client.newCall(request);
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            callback.onApiBefore(tag);
        } else {
            ((BaseApplication) BaseApplication.getInstance()).post(new Runnable() {
                @Override
                public void run() {
                    callback.onApiBefore(tag);
                }
            });
        }
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (BuildConfig.DEBUG) {
                    LogUtils.d(tag.concat(" Result: ").concat(result));
                }
                BaseResponse res;
                try {
                    res = GsonUtil.toBean(result, cls);
                    if (res == null) {
                        res = new BaseResponse();
                        res.setStatusCode("ERROR");
                        res.setMessage("数据异常");
                    }
                    final CallBack callback = callbackRef.get();
                    if (callback != null) {
                        final BaseResponse finalRes = res;
                        ((BaseApplication) BaseApplication.getInstance()).post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    callback.onApiSuccess(tag, finalRes);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    final CallBack callback = callbackRef.get();
                    if (callback != null) {
                        ((BaseApplication) BaseApplication.getInstance()).post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    callback.onParseError(tag, cls.toString(), result);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } finally {
                    response.body().close();
                }
            }

            @Override
            public void onFailure(Call call, final IOException e) {
                final CallBack callback = callbackRef.get();
                if (callback != null) {
                    ((BaseApplication) BaseApplication.getInstance()).post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (TDevice.hasInternet()) {
                                    callback.onApiFailure(tag, "服务器异常", e);
                                } else {
                                    callback.onApiFailure(tag, "网络错误", e);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    // get同步
    @Override
    public BaseResponse getSync(String url, @Nullable ApiParams params, final Class<? extends BaseResponse> cls) {
        BaseResponse res;
        final String tag = LogUtils.getMethodTag("HttpClient.java");
        final Uri.Builder builder = Uri.parse(url).buildUpon();
        if (params != null) {
            final Map<String, ArrayList<String>> paramsList = params.getParams();
            if (paramsList != null) {
                for (Map.Entry<String, ArrayList<String>> entry : paramsList.entrySet()) {
                    ArrayList<String> values = entry.getValue();
                    if (values != null) {
                        for (int i = 0; i < values.size(); i++) {
                            String value = values.get(i);
                            builder.appendQueryParameter(entry.getKey(), value);
                        }
                    }
                }
            }
        }
        Request request = new Request.Builder().tag(tag).url(builder.build().toString()).build();
        if (BuildConfig.DEBUG) {
            LogUtils.d(tag.concat(" Start: ").concat(builder.build().toString()));
        }
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            final String result = response.body().string();
            if (BuildConfig.DEBUG) {
                LogUtils.d(tag.concat(" Result: ").concat(result));
            }
            try {
                res = GsonUtil.toBean(result, cls);
                if (res == null) {
                    res = new BaseResponse();
                    res.setStatusCode("ERROR");
                    res.setMessage("数据异常");
                }
            } catch (Exception e) {
                e.printStackTrace();
                res = new BaseResponse();
                res.setStatusCode("ERROR");
                res.setMessage("解析异常");
            } finally {
                response.body().close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            res = new BaseResponse();
            res.setStatusCode("ERROR");
            res.setMessage("服务器异常");
        }
        return res;
    }

    @Override
    public void postAsync(String url, @Nullable ApiParams apiParams, final Class<? extends BaseResponse> cls, final CallBack callback) {
        postAsync(url, apiParams, cls, callback, Type.POST_FORM);
    }

    // post异步
    public void postAsync(String url, @Nullable ApiParams apiParams, final Class<? extends BaseResponse> cls, final CallBack callback, int type) {
        final String tag = LogUtils.getMethodTag("HttpClient.java");
        final WeakReference<CallBack> callbackRef = new WeakReference<>(callback);
        RequestBody body = buildBody(apiParams, type);
        if (BuildConfig.DEBUG) {
            LogUtils.d(tag.concat(" Url: ").concat(url).concat(body.toString()));
        }
        Request request = new Request.Builder().tag(tag).url(url).post(body).build();
        Call call = client.newCall(request);
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            callback.onApiBefore(tag);
        } else {
            ((BaseApplication) BaseApplication.getInstance()).post(new Runnable() {
                @Override
                public void run() {
                    callback.onApiBefore(tag);
                }
            });
        }
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                if (BuildConfig.DEBUG) {
                    LogUtils.d(tag.concat(" Result: ").concat(result));
                }
                BaseResponse res;
                try {
                    res = GsonUtil.toBean(result, cls);
                    if (res == null) {
                        res = new BaseResponse();
                        res.setStatusCode("ERROR");
                        res.setMessage("数据异常");
                    }
                    final CallBack callback = callbackRef.get();
                    if (callback != null) {
                        final BaseResponse finalRes = res;
                        ((BaseApplication) BaseApplication.getInstance()).post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    callback.onApiSuccess(tag, finalRes);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    final CallBack callback = callbackRef.get();
                    if (callback != null) {
                        ((BaseApplication) BaseApplication.getInstance()).post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    callback.onParseError(tag, cls.toString(), result);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } finally {
                    response.body().close();
                }
            }

            @Override
            public void onFailure(Call call, final IOException e) {
                final CallBack callback = callbackRef.get();
                if (callback != null) {
                    ((BaseApplication) BaseApplication.getInstance()).post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (TDevice.hasInternet()) {
                                    callback.onApiFailure(tag, "服务器异常", e);
                                } else {
                                    callback.onApiFailure(tag, "网络错误", e);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public BaseResponse postSync(String url, ApiParams apiParams, final Class<? extends BaseResponse> cls) {
        return postSync(url, apiParams, cls, Type.POST_FORM);
    }

    // post同步
    public BaseResponse postSync(String url, ApiParams apiParams, final Class<? extends BaseResponse> cls, int type) {
        BaseResponse res;
        final String tag = LogUtils.getMethodTag("HttpClient.java");
        RequestBody body = buildBody(apiParams, type);
        if (BuildConfig.DEBUG) {
            LogUtils.d(tag.concat(" Url: ").concat(url).concat(body.toString()));
        }
        Request request = new Request.Builder().tag(tag).url(url).post(body).build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            final String result = response.body().string();
            if (BuildConfig.DEBUG) {
                LogUtils.d(tag.concat(" Result: ").concat(result));
            }
            try {
                res = GsonUtil.toBean(result, cls);
                if (res == null) {
                    res = new BaseResponse();
                    res.setStatusCode("ERROR");
                    res.setMessage("数据异常");
                }
            } catch (Exception e) {
                e.printStackTrace();
                res = new BaseResponse();
                res.setStatusCode("ERROR");
                res.setMessage("解析异常");
            } finally {
                response.body().close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            res = new BaseResponse();
            res.setStatusCode("ERROR");
            res.setMessage("服务器异常");
        }
        return res;
    }

    public RequestBody buildBody(@Nullable ApiParams apiParams, int type) {
        if (apiParams != null) {
            if (type == Type.POST_STRING) {
                return new HttpRequestBody(GsonUtil.toGsonString(apiParams.getParams()).replace("[", "").replace("]", ""));
            } else {
                MultipartBody.Builder builder = new MultipartBody.Builder();
                builder.setType(MultipartBody.FORM);
                final Map<String, ArrayList<String>> paramsList = apiParams.getParams();
                if (paramsList != null) {
                    for (Map.Entry<String, ArrayList<String>> entry : paramsList.entrySet()) {
                        ArrayList<String> values = entry.getValue();
                        if (values != null) {
                            for (int i = 0; i < values.size(); i++) {
                                String value = values.get(i);
                                builder.addFormDataPart(entry.getKey(), value);
//                            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + entry.getKey() + "\""),
//                                    RequestBody.create(null, value));
                            }
                        }
                    }
                }
                Map<String, ArrayList<File>> files = apiParams.getFiles();
                if (files != null) {
                    for (Map.Entry<String, ArrayList<File>> entry : files.entrySet()) {
                        ArrayList<File> filesList = entry.getValue();
                        if (filesList != null) {
                            for (int i = 0; i < filesList.size(); i++) {
                                File file = filesList.get(i);
                                if (file != null && file.exists()) {
                                    RequestBody fileBody = RequestBody.create(MEDIATYPE_OCTET_STREAM, file);
                                    builder.addFormDataPart(entry.getKey(), file.getName(), fileBody);
                                }
                            }
                        }
                    }
                }
                //}
                return builder.build();
            }
        }
        return new HttpRequestBody("");
    }

    public void cancelTag(String tag) {
        for (Call call : client.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : client.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }


    private static class SingletonHolder {
        private static final ApiImpl INSTANCE = new ApiImpl();
    }

    @IntDef({Type.POST_STRING, Type.POST_FORM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
        int POST_STRING = 1;
        int POST_FORM = 2;
    }
}
