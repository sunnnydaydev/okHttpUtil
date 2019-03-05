package com.example.administrator.okhttputil.net.response;

import android.os.Handler;
import android.os.Looper;

import com.example.administrator.okhttputil.net.exception.OkHttpException;
import com.example.administrator.okhttputil.net.listener.DisposeDataHandle;
import com.example.administrator.okhttputil.net.listener.DisposeDataListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Create by SunnyDay on 2019/03/05
 */
public class CommonJsonCallback implements Callback {
    protected final String RESULT_CODE = "ecode";
    protected final int RESULT_CODE_VALUE = 0;
    protected final String ERROR_MSG = "emsg";
    protected final String EMPTY_MSG = "";

    /**
     * 自定义类型异常
     */
    protected final int NETWORK_ERROR = -1;
    protected final int JSON_ERROR = -2;
    protected final int OTHER_ERROR = -3;


    private Handler mDeliveryHandler; //进行消息转发
    private DisposeDataListener mlistener;
    private Class<?> mClass;

    public CommonJsonCallback(DisposeDataHandle handle) {
        this.mlistener = handle.listener;
        this.mClass = handle.mClass;
        this.mDeliveryHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 请求失败处理
     */
    @Override
    public void onFailure(Call call, final IOException e) {
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                mlistener.onFailure(new OkHttpException(NETWORK_ERROR, e));
            }
        });
    }

    /**
     * 真正的响应处理
     */
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();// json 类型 为String就行
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                handleResponse(result);
            }
        });
    }

    private void handleResponse(Object responseObj) {
        if (responseObj == null && responseObj.toString().trim().equals("")) {
            mlistener.onFailure(new OkHttpException(NETWORK_ERROR, EMPTY_MSG));
            return;
        }
        try {
            JSONObject result = new JSONObject(responseObj.toString());
            if (result.has(RESULT_CODE)) {
                if (result.getInt(RESULT_CODE) == RESULT_CODE_VALUE) {
                    if (mClass == null) {
                        mlistener.onSuccess(responseObj);
                    } else {
                        // 转化为实体
                        Gson gson = new Gson();
                        Object obj = gson.fromJson(responseObj.toString(), mClass);
                        if (obj != null) {
                            mlistener.onSuccess(obj);
                        } else {
                            // 不是合法的json
                            mlistener.onFailure(new OkHttpException(JSON_ERROR, EMPTY_MSG));
                        }
                    }
                }
            }else{
                 mlistener.onFailure(new OkHttpException(OTHER_ERROR,result.get(RESULT_CODE)));
            }
        } catch (Exception e) {
           mlistener.onFailure(new OkHttpException(OTHER_ERROR,e.getMessage()));
        }
    }
}
