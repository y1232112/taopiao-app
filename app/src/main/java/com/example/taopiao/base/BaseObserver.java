package com.example.taopiao.base;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Message;
import android.util.Log;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;


public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {
    private Context mycontext;
    public BaseObserver(Context mycontext){
        this.mycontext=mycontext;
    }


    //请求成功
    @Override
    public void onNext(BaseEntity<T> entity) {
//        响应码为200时执行onsuccess()，不为200时执行onCodeError()
        Log.d("请求成功！！！！！！", "------------------");
        if(entity.isSuccess()){
               onSuccess(entity);
        }else {
               onCodeError(entity);
        }
    }
    //请求失败
    @Override
    public void onError(Throwable e) {
        if(e instanceof ConnectException||e instanceof TimeoutException
                ||e instanceof NetworkErrorException||e instanceof UnknownHostException){
            Log.d("网络异常！！！！！！", "------------------");
            e.printStackTrace();
            try {

                onFailure(e,false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }else {
            try {
                onFailure(e,true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    //执行onNext后执行
    @Override
    public void onComplete() {
      
    }

    //请求成功且返回码为200的回调方法,这里抽象方法申明
    public abstract void onSuccess(BaseEntity<T> entity);
    //请求成功但返回的code码不是200的回调方法,这里抽象方法申明
    public abstract void onCodeError(BaseEntity<T> entity);
    //请求失败回调方法,这里抽象方法申明
    public abstract void onFailure(Throwable e,boolean network)throws Exception;
}
