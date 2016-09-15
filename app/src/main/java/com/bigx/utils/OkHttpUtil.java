package com.bigx.utils;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaoshuai on 16/3/18.
 */
public class OkHttpUtil {
    
    private static final String TAG = "OkHttpUtil";
    
    private static OkHttpUtil mInstance;
    
    private OkHttpClient mOkHttpClient;
    
    private Handler mDelivery;

    private OkHttpUtil() {
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        //cookie enabled
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mDelivery = new Handler(Looper.getMainLooper());
    }

    private static OkHttpUtil getInstance() {
        if (null == mInstance) {
            synchronized (OkHttpUtil.class) {
                if (null == mInstance) {
                    mInstance = new OkHttpUtil();
                }
            }
        }
        return mInstance;
    }

    private void getRequest(String url, ResultCallback callback) {
        Request request = new Request.Builder().url(url).build();
        deliveryResult(callback, request);
    }
    
    private <T> void postRequest(String url, ResultCallback<T> callback, List<Param> params) {
        Request request = buildPostRequest(url, params);
        deliveryResult(callback, request);
    }

    private <T> void deliveryResult(final ResultCallback<T> callback, Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sendFailCallback(callback, e);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    String resp = response.body().string();
                    if (callback.mType == String.class) {
                        sendSuccessCallback(callback, (T) resp);
                    } else {
                        Object object = JsonUtil.deserialize(resp, callback.mType);
                        sendSuccessCallback(callback, (T) object);
                    }
                } catch (Exception e) {
                    LogUtil.e(TAG, "convert json failure", e);
                    sendFailCallback(callback, e);
                }
            }
        });
    }

    private <T> void sendSuccessCallback(final ResultCallback<T> callback, final T resp) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (null != callback) {
                    callback.onSuccess(resp);
                }
            }
        });
    }

    private <T> void sendFailCallback(final ResultCallback<T> callback, final Exception e) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (null != callback) {
                    callback.onFailure(e);
                }
            }
        });
    }
    
    private Request buildPostRequest(String url, List<Param> params) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }

    /**
     * get请求
     * @param url
     * @param callback
     * @param <T>
     */
    public static <T> void get(String url, ResultCallback<T> callback) {
        getInstance().getRequest(url, callback);
    }
    
    public static <T> void post(String url, ResultCallback<T> callback, List<Param> params) {
        getInstance().postRequest(url, callback, params);
    }

    /**
     * http请求回调类,回调方法在UI线程中执行
     * @param <T>
     */
    public static abstract class ResultCallback<T> {
        Type mType;
        public ResultCallback(){
            mType = getSuperclassTypeParameter(getClass());
        }
        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }
        /**
         * 请求成功回调
         * @param response
         */
        public abstract void onSuccess(T response);
        /**
         * 请求失败回调
         * @param e
         */
        public abstract void onFailure(Exception e);
    }
    
    /**
     * post请求参数类
     */
    public static class Param {
        String key;
        String value;
        public Param() {
        }
        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
    
}
