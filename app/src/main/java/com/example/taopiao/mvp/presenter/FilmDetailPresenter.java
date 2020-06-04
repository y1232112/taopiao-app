package com.example.taopiao.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.taopiao.MainActivity;
import com.example.taopiao.application.MyApplication;
import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseObserver;
import com.example.taopiao.mvp.contract.FilmDetailContract;
import com.example.taopiao.network.retrofit.BaseRequest;
import com.example.taopiao.utils.MD5Util;
import com.example.taopiao.utils.ShareManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class FilmDetailPresenter implements FilmDetailContract.presenter {
      private Context context;
      private FilmDetailContract.view view;
      private static String TAG="FilmDetail******************";
//    构造函数

    public FilmDetailPresenter(Context context,FilmDetailContract.view view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void getFilm(int film_id) {
        BaseRequest.getInstance().apiServices.getFilm(String.valueOf(film_id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Observer<BaseEntity<List<Map<String,String>>>>) new BaseObserver<List<Map<String,String>>>(context) {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseEntity<List<Map<String,String>>> entity) {
                        Log.d(TAG+"onSuccess",entity.getParams().toString());
                        MyApplication app=MyApplication.getInstance();

                        view.setFilm(entity.getParams());
                    }

                    @Override
                    public void onNext(BaseEntity<List<Map<String,String>>> entity) {
                        super.onNext(entity);


                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onCodeError(BaseEntity<List<Map<String,String>>> entity) {

                    }

                    @Override
                    public void onFailure(Throwable e, boolean network) throws Exception {

                    }
                });

    }

    @Override
    public void getRoles(int film_id) {
        BaseRequest.getInstance().apiServices.getRoles(String.valueOf(film_id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Observer<BaseEntity<List<Map<String,String>>>>) new BaseObserver<List<Map<String,String>>>(context) {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseEntity<List<Map<String,String>>> entity) {
                        Log.d(TAG+"onSuccess",entity.getParams().toString());
//                        MyApplication app=MyApplication.getInstance();

                        view.setRoles(entity.getParams());
                    }

                    @Override
                    public void onNext(BaseEntity<List<Map<String,String>>> entity) {
                        super.onNext(entity);


                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onCodeError(BaseEntity<List<Map<String,String>>> entity) {

                    }

                    @Override
                    public void onFailure(Throwable e, boolean network) throws Exception {

                    }
                });

    }

    @Override
    public void getComment(String json) {
        RequestBody requestBody= RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        BaseRequest.getInstance().apiServices.selectComment(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Observer<BaseEntity<List<Map<String,String>>>>) new BaseObserver<List<Map<String,String>>>(context) {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseEntity<List<Map<String,String>>> entity) {
                        Log.d(TAG+"onSuccess",entity.getParams().toString());
//                        MyApplication app=MyApplication.getInstance();
                        view.setComment(entity.getParams());

                    }

                    @Override
                    public void onNext(BaseEntity<List<Map<String,String>>> entity) {
                        super.onNext(entity);
                        Log.d(TAG,entity.getParams().toString());
                       view.setComment(entity.getParams());

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onCodeError(BaseEntity<List<Map<String,String>>> entity) {

                    }

                    @Override
                    public void onFailure(Throwable e, boolean network) throws Exception {
                           Log.d(TAG,e.toString());
                    }
                });
    }

    @Override
    public void getUserFilm(Integer film_id, Integer user_id) {

        BaseRequest.getInstance().apiServices.getUserFilm(film_id,user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Observer<BaseEntity<List<Map<String,String>>>>) new BaseObserver<List<Map<String,String>>>(context) {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseEntity<List<Map<String,String>>> entity) {
                        Log.d(TAG+"onSuccess",entity.getParams().toString());
//                        MyApplication app=MyApplication.getInstance();
                        if (entity.getParams()!=null);
                        view.setUserFilm(entity.getParams());

                    }

                    @Override
                    public void onNext(BaseEntity<List<Map<String,String>>> entity) {
                        super.onNext(entity);
                        Log.d(TAG,entity.getParams().toString());
                        view.setUserFilm(entity.getParams());

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onCodeError(BaseEntity<List<Map<String,String>>> entity) {

                    }

                    @Override
                    public void onFailure(Throwable e, boolean network) throws Exception {
                        Log.d(TAG,e.toString());
                    }
                });
    }

    @Override
    public void doLooked(RequestBody requestBody) {
        BaseRequest.getInstance().apiServices.doLooked(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Map<String, String>>(context) {
                    static final String TAG="--登录--_____________登录：";
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity<Map<String, String>> entity) {
                        super.onNext(entity);
//
                        view.showDoLooked(entity.getMessage());


                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,"...网络请求...");
                        e.printStackTrace();

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"...Complete...");
                    }

                    @Override
                    public void onSuccess(BaseEntity<Map<String, String>> entity) {

                    }

                    @Override
                    public void onCodeError(BaseEntity<Map<String, String>> entity) {



                    }

                    @Override
                    public void onFailure(Throwable e, boolean network) throws Exception {

                    }
                });
    }

    @Override
    public void doWish(RequestBody requestBody) {

        BaseRequest.getInstance().apiServices.postDoWish(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Map<String, String>>(context){

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity<Map<String, String>> entity) {
                        super.onNext(entity);
                        view.showDoWish(entity.getMessage());


                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onSuccess(BaseEntity<Map<String, String>> entity) {


                    }

                    @Override
                    public void onCodeError(BaseEntity<Map<String, String>> entity) {

                    }

                    @Override
                    public void onFailure(Throwable e, boolean network) throws Exception {
                        Log.d("想看：---e--",e.toString());

                    }

                });


    }


}
