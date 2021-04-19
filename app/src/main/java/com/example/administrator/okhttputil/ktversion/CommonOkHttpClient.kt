package com.example.selfupdate.net

import com.example.selfupdate.net.response.CommonJsonCallback
import com.example.selfupdate.net.ssl.HttpUtils
import okhttp3.*
import java.util.concurrent.TimeUnit


/**
 * Created by zb on 2021/3/31 15:40
 */

private const val TIME_OUT: Long = 30

object CommonOkHttpClient {

    private var okHttpClient: OkHttpClient

    init {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .followRedirects(true)
            .hostnameVerifier { _, _ -> true }
            .sslSocketFactory(HttpUtils.getSslSocketFactory())

        okHttpClient = okHttpClientBuilder.build()
    }

    fun sendRequest(request: Request, callback: CommonJsonCallback): Call {

        val call: Call = okHttpClient.newCall(request)
        call.enqueue(callback)
        return call
    }

}