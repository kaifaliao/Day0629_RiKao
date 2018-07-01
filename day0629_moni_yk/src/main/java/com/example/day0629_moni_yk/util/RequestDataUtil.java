package com.example.day0629_moni_yk.util;

import android.os.Handler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by John on 2018/6/29 0029.
 */

public class RequestDataUtil {

    private final OkHttpClient client;
    private static Handler handler;

    private RequestDataUtil() {
        client = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        handler = new Handler();
    }
    private static final RequestDataUtil dataUtil = new RequestDataUtil();
    public static RequestDataUtil getInstance(){
        return dataUtil;
    }
    //------------------OkHttp 的外部调用
    //1.定义接口
    public interface IcallBack{
        void onRequest(String jsonData);//请求数据
    }
    //2.将代码运行到主线程中 处理请求数据成功时回调
    private static void requestJsonDataOk(final String jsonData, final IcallBack icallBack){
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(icallBack != null){
                    icallBack.onRequest(jsonData);
                }
            }
        });
    }
    //3.提供外部调用的方法
    public void getRequestJsonData(final String myUrl, final IcallBack icallBack){
        Request request = new Request.Builder().url(myUrl).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //请求失败
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功
                String jsonData = response.body().string();
                requestJsonDataOk(jsonData,icallBack);//调用从请求成功的方法
            }
        });
    }
}
