package com.example.taopiao.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.example.taopiao.application.MyApplication;
import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseObserver;
import com.example.taopiao.mvp.contract.SelectCinemaContract;
import com.example.taopiao.mvp.contract.SnackBuyContract;
import com.example.taopiao.network.retrofit.BaseRequest;

import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SnackBuyPresenter implements SnackBuyContract.presenter {

    private Context context;
    private SnackBuyContract.view view;

    public SnackBuyPresenter(Context context,SnackBuyContract.view view){
        this.context = context;
        this.view = view;

    }

    @Override
    public void getUser() {
        MyApplication app=MyApplication.getInstance();
        int user_id=app.user_id;
        BaseRequest.getInstance().getApiServices().userInfo(user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( new BaseObserver<Map<String,String>>(context){
                    //观察者数据的

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity<Map<String,String>> entity) {
                        super.onNext(entity);
                        Log.d("onNext()方法--freagment=====",entity.toString());
                        Map<String,String> map=entity.getParams();
                       view.setUser(map);
                    }
                    @Override
                    public void onSuccess(BaseEntity<Map<String,String>> entity) {
                        if(entity.isSuccess()){

                        }else {

                        }
                    }

                    @Override
                    public void onCodeError(BaseEntity<Map<String,String>> entity) {



                    }

                    @Override
                    public void onFailure(Throwable e, boolean network) throws Exception {

                    }
                });
    }

    @Override
    public void getSnack(Integer id) {
        BaseRequest.getInstance().apiServices.getSnack(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Observer<BaseEntity<List<Map<String, String>>>>) new BaseObserver<List<Map<String, String>>>(context) {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseEntity<List<Map<String, String>>> entity) {

                    }

                    @Override
                    public void onNext(BaseEntity<List<Map<String, String>>> entity) {
                        super.onNext(entity);
                        Log.d("onNext", entity.getParams().toString());
                        if (entity.getParams()!=null){
                            view.setContent(entity.getParams());
                        }



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
                    public void onCodeError(BaseEntity<List<Map<String, String>>> entity) {

                    }

                    @Override
                    public void onFailure(Throwable e, boolean network) throws Exception {
                        Log.d("++++++++++++",e.toString());
                    }
                });
    }

    @Override
    public void uploadCode(MultipartBody.Part qr) {
        BaseRequest.getInstance().getApiServices().doQRCode(qr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( new BaseObserver<Map<String,String>>(context){
                    //观察者数据的

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity<Map<String,String>> entity) {
                        super.onNext(entity);
                        Log.d("onNext()方法--freagment=====",entity.toString());
                        Map<String,String> map=entity.getParams();
                       view.laterDoOrder(entity.getMessage());
                    }
                    @Override
                    public void onSuccess(BaseEntity<Map<String,String>> entity) {
                        if(entity.isSuccess()){

                        }else {

                        }
                    }

                    @Override
                    public void onCodeError(BaseEntity<Map<String,String>> entity) {



                    }

                    @Override
                    public void onFailure(Throwable e, boolean network) throws Exception {

                    }
                });
    }

    @Override
    public void postSnackOrder(RequestBody requestBody) {
        BaseRequest.getInstance().getApiServices().postSnackOrder(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( new BaseObserver<Map<String,String>>(context){
                    //观察者数据的

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity<Map<String,String>> entity) {
                        super.onNext(entity);
                        Log.d("onNext()方法",entity.toString());

                      view.messageSnackOrder(entity.getMessage());
                    }
                    @Override
                    public void onSuccess(BaseEntity<Map<String,String>> entity) {
                        if(entity.isSuccess()){

                        }else {

                        }
                    }

                    @Override
                    public void onCodeError(BaseEntity<Map<String,String>> entity) {



                    }

                    @Override
                    public void onFailure(Throwable e, boolean network) throws Exception {

                    }
                });
    }
}
