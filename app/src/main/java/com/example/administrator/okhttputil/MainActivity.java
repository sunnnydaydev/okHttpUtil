package com.example.administrator.okhttputil;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.okhttputil.net.request.CommonRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "abc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    /**
     * 同步get请求
     *
     * 发送请求后就会进入阻塞状态（阻塞当前线程）  直到收到响应
     */
    private void getSync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 1
                OkHttpClient client = new OkHttpClient.Builder().build();
                // 2
                Request request = new Request.Builder()
                        .url("https://www.baidu.com")
                        .get()//默认的方法 不添加也可以的
                        .build();
                try {
                    // 3 Call 为接口  具体实现类是 realcall
                    Call call = client.newCall(request);// call 可以看做response 和request的连接桥梁

                   // 4 同步 异步的前三步一样  第四步开始不同
                    Response response = call.execute();
                    if (response.isSuccessful()) {
                        // Log.i(TAG, "返回码 " + response.code());
                        //Log.i(TAG, "message " + response.message());
                        Log.i(TAG, "同步请求数据： " + response.body().string());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 异步get请求
     *
     * 不阻塞当前线程 enqueue在子线程中进行
     * */
    private void getAsync() {
        //1
        OkHttpClient client = new OkHttpClient();
        //2
        final Request request = new Request.Builder()
                .url("https://www.baidu.com")
                .build();
        // 3,4 Call call = client.newCall(request);  call.enqueue(callback)
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.i(TAG, "异步请求数据 ：" + response.body().string());
                }
            }
        });
    }
}
