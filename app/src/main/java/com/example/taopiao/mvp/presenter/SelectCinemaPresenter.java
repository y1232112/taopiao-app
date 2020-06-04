package com.example.taopiao.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseObserver;
import com.example.taopiao.mvp.contract.MainContract;
import com.example.taopiao.mvp.contract.SelectCinemaContract;
import com.example.taopiao.network.retrofit.BaseRequest;

import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class SelectCinemaPresenter implements SelectCinemaContract.presenter {

    private Context context;
    private SelectCinemaContract.view view;

    public SelectCinemaPresenter(Context context,SelectCinemaContract.view view){
        this.context = context;
        this.view = view;

    }
    @Override
    public void getCinemasHasSchedule(RequestBody requestBody) {
        BaseRequest.getInstance().apiServices.getCinemasHaveSchedule(requestBody)
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
                        if (entity.getParams()!=null){
                                Log.d("onSuccess",entity.getParams().toString());
                               view.showCinemas(entity.getParams());



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
                    public void onCodeError(BaseEntity<List<Map<String,String>>> entity) {

                    }

                    @Override
                    public void onFailure(Throwable e, boolean network) throws Exception {

                    }
                });
    }
}
