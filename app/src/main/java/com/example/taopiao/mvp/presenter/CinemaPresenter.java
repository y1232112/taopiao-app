package com.example.taopiao.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.example.taopiao.adapter.HotFilmAdapter;
import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseObserver;
import com.example.taopiao.mvp.contract.CinemaContract;
import com.example.taopiao.network.retrofit.BaseRequest;

import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CinemaPresenter implements CinemaContract.presenter {
    private Context context;
    private CinemaContract.view view;

    public CinemaPresenter(Context context, CinemaContract.view view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void getCinemaInfo(Integer cinema_id) {
        BaseRequest.getInstance().apiServices.getMySnack(cinema_id)
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
                            view.setCinema(entity.getParams());
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
}
