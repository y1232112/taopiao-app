package com.example.taopiao.network.retrofit;

import com.example.taopiao.network.api.ApiServices;
import com.example.taopiao.utils.InterceptorUtil;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;


/**
 *通过初始化okhttp和retrofit,获取ApiServise的实例
 * 主要做retrofit绑定okhttp,然后通过retrofit的实例
 * 获取ApiServise的实例
 */
public class BaseRequest {
    public static BaseRequest instance;
    //获取ApiServise的实例
    OkHttpClient client=new OkHttpClient.Builder().
            connectTimeout(20, TimeUnit.SECONDS).                   //设置请求超时时间
            readTimeout(20,TimeUnit.SECONDS).                       //设置读取数据超时时间
            writeTimeout(20,TimeUnit.SECONDS).                      //设置写入数据超时时间
            addInterceptor(InterceptorUtil.logInterceptor()).                //绑定日志拦截器
            addNetworkInterceptor(InterceptorUtil.HeaderInterceptor())       //绑定header拦截器
            .build();


    Retrofit retrofit=new Retrofit.Builder().
            addConverterFactory(GsonConverterFactory.create()).             //设置gson转换器,将返回的json数据转为实体
            addCallAdapterFactory(RxJava2CallAdapterFactory.create()).       //设置CallAdapter
            baseUrl(ApiServices.HOST).
            client(client)                                                  //设置客户端okhttp相关参数
            .build();
    public ApiServices apiServices=retrofit.create(ApiServices.class);//通过retrofit的实例,获取ApiServise接口的实例
    private BaseRequest(){

    }
   public static BaseRequest getInstance(){
        if(instance==null){
            synchronized (BaseRequest.class){
                if(instance==null){
                    instance=new BaseRequest();
                }
            }
        }
        return instance;

   }
   public ApiServices getApiServices(){
        return apiServices;
   }






}
