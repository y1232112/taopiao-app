package com.example.taopiao.network.api;

import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.mvp.entity.Login;
import com.example.taopiao.mvp.entity.Register;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 *
 */
public interface ApiServices {
    String HOST = "http://192.168.1.85:8084/";
//    String HOST = "http://192.168.206.1:8084/";
//   String HOST = "http://192.168.137.1:8084/";

    /**
     * get请求方式
     * @Query
     * 形成单个查询参数, 将接口url中追加类似于"page=1"的字符串,形成提交给服务器端的参数,
     * 主要用于Get请求数据，用于拼接在拼接在url路径后面的查询参数，一个@Query相当于拼接一个参数
     */



    /**
     * post请求方式
     * @param requestBody
     */

//    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @Headers({"Content-Type:application/json; charset=utf-8","Accept: application/json"})
    @POST("user/doRegist")   //方法名
    Observable<BaseEntity<Map<String,String>>> userRegist(@Body RequestBody requestBody);//请求参数

}
