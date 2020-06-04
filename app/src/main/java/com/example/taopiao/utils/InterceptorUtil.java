package com.example.taopiao.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.taopiao.application.MyApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class InterceptorUtil {

    public static final String TAG="------拦截器：-----------------------";
    public static HttpLoggingInterceptor logInterceptor(){//日志拦截器，打印返回的请求结果
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.w(TAG, "log: "+message );
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);

    }

    public static Interceptor HeaderInterceptor(){//头部添加请求头信息
        MyApplication app=MyApplication.getInstance();
//        ShareManager shareManager=new ShareManager();
//        String  token=shareManager.getToken();
         String token=app.token;
         int  user_id=app.user_id;

//        if(token.equals("")||token==null){

            return new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request=chain.request()
                            .newBuilder()
                            .addHeader("Content-Type","application/json;charSet=UTF-8")
                            .header("token",token)
                            .header("user_id",""+user_id)
                            .build();
                    return chain.proceed(request);
                }
            };

//        }else {
//            return new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request request=chain.request()
//                            .newBuilder()
//                            .addHeader("Content-Type","application/json;charSet=UTF-8")
//                            .header("token",token)
//                            .build();
//                    return chain.proceed(request);
//                }
//            };
//
//        }
//
    }
}
