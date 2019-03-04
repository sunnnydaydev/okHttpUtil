package com.example.administrator.okhttputil.net.request;

import android.util.Log;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Create by SunnyDay on 2019/03/04
 *
 * @function 接收请求参数 为我们生成Request对象
 */
public class CommonRequest {
    /**
     * @function post 请求
     * @param url    url
     * @param params 请求参数
     *
     */
    public static Request createPostRequest(String url, RequestParams params) {
        FormBody.Builder builder = new FormBody.Builder();
        // 吧请求内容添加到请求体中
        for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        // 构建请求体
        FormBody formBody = builder.build();
         // 返回封装的Request请求
        return new Request.Builder().post(formBody).build();
    }

    /**
     * @function get 请求
     * @param url    url
     * @param params 请求参数
     *     通过url+请求参数的拼接 成我们的get请求url，在生成Request请求
     *
     *               get url 的方式  域名 ？ key = value & key = value ......
     *
     */
    public static Request createGetRequest(String url, RequestParams params){
        StringBuilder sb = new StringBuilder(url).append("?");
        if (params!=null){
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
               sb.append(entry.getKey())
                       .append("=")
                       .append(entry.getValue())
                       .append("&");
            }
        }
        return new Request.Builder()
                .url(sb.substring(0,sb.length()-1))
                .get()
                .build();
    }
}
