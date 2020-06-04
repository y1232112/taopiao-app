package com.example.taopiao.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseObserver;
import com.example.taopiao.mvp.contract.DoAvatarContract;
import com.example.taopiao.network.retrofit.BaseRequest;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class DoAvatarPresenter implements DoAvatarContract.presenter{
    private Context context;
   private DoAvatarContract.view view;

    public DoAvatarPresenter(Context context, DoAvatarContract.view view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void cummitAvatar( MultipartBody.Part mfile) {
        BaseRequest.getInstance().getApiServices().cummitAvatar(mfile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( new BaseObserver<Map<String,String>>(context){
                    //观察者数据的接收器
                    static final String TAG="--图片上传--：";
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity<Map<String,String>> entity) {
                        super.onNext(entity);
                        Log.d("onNext()方法--",entity.toString());
                        Log.d("onNext()方法--",entity.getMessage());
                        view.getMessage(entity.getMessage());

                    }

                    @Override
                    public void onSuccess(BaseEntity<Map<String,String>> entity) {
                        if(entity.isSuccess()){
                            Log.d("onS()方法--",entity.toString());
                            Log.d("onS()方法--",entity.getMessage());
                        }else {

                        }
                    }

                    @Override
                    public void onCodeError(BaseEntity<Map<String,String>> entity) {

                        Log.d(TAG+"ErrCode--",Integer.toString(entity.getCode()));

                    }

                    @Override
                    public void onFailure(Throwable e, boolean network) throws Exception {
                        Log.d(TAG+"上传失败 请求异常-",e.toString());

                    }
                });
    }
}
