package com.example.taopiao.mvp.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.taopiao.application.MyApplication;
import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseObserver;
import com.example.taopiao.mvp.contract.MainContract;
import com.example.taopiao.network.retrofit.BaseRequest;
import com.example.taopiao.utils.DateUtils;
import com.example.taopiao.utils.netUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainContract.presenter {
    private Context context;
    private MainContract.view view;
    private static String TAG="---MainPresenter----->";
    public MainPresenter(Context context,MainContract.view view){
        this.context = context;
        this.view = view;

    }

    @Override
    public void getHotFilms(String city) {
//这行http请求
         List<Object> list;
        BaseRequest.getInstance().getApiServices().getHotFilms(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Observer<BaseEntity<List<Map<String,String>>>>) new BaseObserver<List<Map<String,String>>>(context) {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseEntity<List<Map<String,String>>> entity) {


                    }

                    @Override
                    public void onNext(BaseEntity<List<Map<String,String>>> entity) {
                        super.onNext(entity);
                        Log.d("onSuccess",entity.getParams().toString());
                        List<Map<String,String>> maps=entity.getParams();
                        if (maps!=null){

                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.d("onSuccess",e.toString());
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
    public void getWishInfo(String user_id) {
        BaseRequest.getInstance().apiServices.getWishInfo(user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Observer<BaseEntity<List<Integer>>>) new BaseObserver<List<Integer>>(context) {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseEntity<List<Integer>> entity) {

                    }

                    @Override
                    public void onNext(BaseEntity<List<Integer>> entity) {
                        super.onNext(entity);
                        Log.d(TAG+"onNext",entity.getParams().toString());
                        MyApplication app=MyApplication.getInstance();


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
                    public void onCodeError(BaseEntity<List<Integer>> entity) {

                    }

                    @Override
                    public void onFailure(Throwable e, boolean network) throws Exception {

                    }
                });

    }

    public Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
