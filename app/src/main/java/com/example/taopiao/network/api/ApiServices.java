package com.example.taopiao.network.api;

import com.example.taopiao.application.MyApplication;
import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.mvp.entity.Login;
import com.example.taopiao.mvp.entity.Register;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observer;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 *
 */
public interface ApiServices {

//    String HOST = "http://192.168.43.227:8084/";

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
     * 注册模块
     * post请求方式
     * @param requestBody
     */

//    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @Headers({"Content-Type:application/json; charset=utf-8","Accept: application/json"})
    @POST("user/doRegister")   //方法名
    Observable<BaseEntity<Map<String,String>>> userRegist(@Body RequestBody requestBody);//请求参数

    /**
     * 登录模块
     * @param requestBody
     * @return
     */
    @Headers({"Content-Type:application/json; charset=utf-8","Accept: application/json"})
    @POST("user/doLogin")
    Observable<BaseEntity<Map<String,String>>> userLogin(@Body RequestBody requestBody);
    @GET("user/hotFilms/{city}/")
    Observable<BaseEntity<List<Map<String,String>>>> getHotFilms(@Path("city") String city);
    @GET("user/willFilms/{user_id}")
    Observable<BaseEntity<List<Map<String,String>>>> getWillFilms(@Path("user_id") Integer user_id);
    @GET(value = "user/myWishInfo/{user_id}/")
    Observable<BaseEntity<List<Integer>>> getWishInfo(@Path("user_id") String user_id);
    @POST("user/dowish")
    Observable<BaseEntity<Map<String,String>>> postDoWish(@Body RequestBody requestBody);
    @GET("user/film/{film_id}")
    Observable<BaseEntity<List<Map<String,String>>>> getFilm(@Path("film_id") String film_id);
    @GET("user/roles/{id}/")
    Observable<BaseEntity<List<Map<String,String>>>> getRoles(@Path("id") String film_id);
    @POST("user/film/addcomment/")
    Observable<BaseEntity<Map<String,String>>> doComment(@Body RequestBody requestBody);
    @POST("user/film/scomment/")
    Observable<BaseEntity<List<Map<String,String>>>> selectComment(@Body RequestBody requestBody);
    @Multipart

    @POST("user/avatar.json/")
    Observable<BaseEntity<Map<String,String>>> cummitAvatar(@Part MultipartBody.Part avatar);
    @GET("user/getUser/{id}/")
    Observable<BaseEntity<Map<String,String>>> userInfo(@Path("id")int user_id);
    @POST("user/film/addReply/")
    Observable<BaseEntity<Map<String,String>>> addReply(@Body RequestBody requestBody);
    @GET("user/film/reply")
    Observable<BaseEntity<List<Map<String,String>>>> getReply(@Query("id") Integer comment_id,@Query("user_id") Integer user_id);
    @POST("user/film/comment/zan.do/")
    Observable<BaseEntity<Map<String,String>>> doZan(@Body RequestBody requestBody);
    @POST("user/film/comment/one.do/")
    Observable<BaseEntity<List<Map<String,String>>>> selectZandReply(@Body RequestBody requestBody);
    @POST("user/hascomment")
    Observable<BaseEntity<Map<String,String>>> queryhasComment(@Query("film_id")Integer film_id,@Query("type")Integer type,@Query("user_id")Integer user_id);
    @POST("user/film/comment/mycomment.do")
    Observable<BaseEntity<List<Map<String,String>>>> myCommentAboutFilm(@Body RequestBody requestBody);
    @POST("user/film/comment/deleteMycomment.do")
    Observable<BaseEntity<Map<String,String>>> deleteMyComment(@Body RequestBody requestBody);
    @POST("user/cinemas/cinemasandserve")
    Observable<BaseEntity<List<Map<String,String>>>> getCinemasAndServe(@Body RequestBody requestBody);
    @GET("user/mySnacks/{id}/")
    Observable<BaseEntity<List<Map<String,String>>>> getMySnack(@Path("id") Integer cinema_id);
    @GET("user/cinemadetail/{id}/")
    Observable<BaseEntity<List<Map<String,String>>>> getCienmaDetail(@Path("id") Integer cinema_id);
    @GET("user/myServe/{id}")
    Observable<BaseEntity<List<Map<String,String>>>> getCienmaServe(@Path("id") Integer cinema_id);
    @GET("user/filmabout")
    Observable<BaseEntity<List<Map<String,String>>>> getUserFilm(@Query("film_id") Integer film_id,@Query("user_id") Integer user_id);
    @POST("user/doLooked/")
    Observable<BaseEntity<Map<String,String>>> doLooked(@Body RequestBody requestBody);
    @POST("user/cinemasbyschedule.do/")
    Observable<BaseEntity<List<Map<String,String>>>>getCinemasHaveSchedule(@Body RequestBody requestBody);
    @GET("user/buy/film/{id}/")
    Observable<BaseEntity<List<Map<String,String>>>> buyAboutFilm(@Path("id")Integer id);
    @GET("user/afilmschedule.do/")
    Observable<BaseEntity<List<Map<String,String>>>> aFilmSchedule(@Query("cinema_id")Integer cinema_id,@Query("film_id") Integer film_id);
    @POST("user/hallseatinfo.do/")
    Observable<BaseEntity<List<Map<String,String>>>>  getSeats(@Body RequestBody requestBody);
    @GET("userbuy/schedule/{id}")
    Observable<BaseEntity<List<Map<String,String>>>> getScheduleById(@Path("id")Integer schedule_id);
    @GET("user/gethall.do/")
    Observable<BaseEntity<List<Map<String,String>>>> getHall(@Query("cinema_id")Integer cinema_id,@Query("hall_id") Integer hall_id);
    @POST("user/film/addOrder.do/")
    Observable<BaseEntity<Map<String,String>>> cummitFilmOrder(@Body RequestBody requestBody);
    @POST("user/film/addItem.do/")
    Observable<BaseEntity<Map<String,String>>> addItem(@Body RequestBody requestBody);
    @GET("user/buy/getsnack/{id}")
    Observable<BaseEntity<List<Map<String,String>>>>  getSnack(@Path("id")Integer id);
    @Multipart
    @POST("user/qrcode.do/")
    Observable<BaseEntity<Map<String,String>>> doQRCode(@Part MultipartBody.Part qr);
    @POST("user/oder/snack.do/")
    Observable<BaseEntity<Map<String,String>>> postSnackOrder(@Body RequestBody requestBody);
    @GET("user//cinema/query/")
    Observable<BaseEntity<List<Map<String,String>>>> searchCinemaBycountyCity(@Query("county")String county,@Query("city")String city);
    @GET("user/orderSnack/query/")
    Observable<BaseEntity<List<Map<String,String>>>> userSnackOrder(@Query("user_id")Integer user_id);
    @GET("user/orderFilm/query/")
    Observable<BaseEntity<List<Map<String,String>>>> userUserFilmOrder(@Query("user_id")Integer user_id);
    @POST("user/upFilmOder.do/")
    Observable<BaseEntity<Map<String,String>>> postFilmOrderPay(@Body RequestBody requestBody);
    @GET("user/myWishedFilms/{user_id}")
    Observable<BaseEntity<List<Map<String,String>>>> receiveMyWished(@Path("user_id")Integer user_id);
    @GET("user/myLookedFilms/{user_id}")
    Observable<BaseEntity<List<Map<String,String>>>> receiveMyLooked(@Path("user_id")Integer user_id);

}
