package com.example.taopiao.mvp.presenter;

import android.content.Context;

import android.util.Log;


import com.example.taopiao.application.MyApplication;
import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseObserver;
import com.example.taopiao.mvp.contract.CommentContrat;
import com.example.taopiao.network.retrofit.BaseRequest;

import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CommentPresenter implements CommentContrat.presenter {
    private Context context;
    private CommentContrat.view view;
    private static String TAG="---comment---";

    public CommentPresenter(Context context, CommentContrat.view view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void commitComment(String json) {
        RequestBody requestBody= RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        BaseRequest.getInstance().apiServices.doComment(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Map<String, String>>(context) {
                    static final String TAG="--登录--：";
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity<Map<String, String>> entity) {
                        super.onNext(entity);
                        Log.d(TAG,"...请求..成功.");



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
                        if(entity.isSuccess()){
                                view.showResult("你的评论提交成功");

                        }
                    }

                    @Override
                    public void onCodeError(BaseEntity<Map<String, String>> entity) {

                        Log.d(TAG+"ErrCode--",Integer.toString(entity.getCode()));

                    }

                    @Override
                    public void onFailure(Throwable e, boolean network) throws Exception {
                          view.showResult("发布失败");
                    }
                });
    }

    @Override
    public void doMyComment(RequestBody requestBody) {
        BaseRequest.getInstance().apiServices.myCommentAboutFilm(requestBody)
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
                            if (entity.getParams().size()>0){
                                Log.d(TAG+"onSuccess",entity.getParams().toString());

                                view.showMyComment(entity.getParams().get(0));
                            }

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
