package com.example.taopiao.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.taopiao.MainActivity;
import com.example.taopiao.application.MyApplication;
import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseObserver;
import com.example.taopiao.mvp.activity.LoginActivity;
import com.example.taopiao.mvp.contract.LoginContract;
import com.example.taopiao.network.retrofit.BaseRequest;
import com.example.taopiao.utils.JsonUtils;
import com.example.taopiao.utils.MD5Util;
import com.example.taopiao.utils.ShareManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class LoginPresenter implements LoginContract.presenter {
   private Context context;
   private LoginContract.view view;
   private static String TAG="--登录--：";

    public LoginPresenter(LoginActivity context, LoginContract.view view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void userLogin(String nickname, String password) {

//        将诗句转换成json
        try {
            JSONObject object= new JSONObject();
            object.put("nickname",nickname);
            object.put("password",password);
            String json=JsonUtils.ceateForParams(object).toString();
            Log.d(TAG,"object-->"+object);
//
            Log.d(TAG,"json-->"+json);
           RequestBody requestBody= RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
//        进行http请求
            BaseRequest.getInstance().apiServices.userLogin(requestBody)
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
//               在非activity中跳转页面
                    if (entity.getMessage().equals("登录成功")){
//                        在非activity中吐司
                        Toast.makeText(context,"登录成功",Toast.LENGTH_SHORT).show();

                        view.setContent("token="+entity.getToken());
//                        将信息写入preference
                         ShareManager sp=new ShareManager();
                         Map<String,String> p=entity.getParams();
                        int user_id=Integer.parseInt(p.get("user_id").toString());
                        Log.d(TAG,"你的id:"+user_id);
                         try {
                            String mpassword= MD5Util.stringMD5(password);


                            MyApplication app=MyApplication.getInstance();
                            app.token=entity.getToken();
                            app.user_id=user_id;
                             Intent intent=new Intent();
                             sp.save(context,user_id,entity.getToken(),nickname,mpassword);
                             intent.setClass(context, MainActivity.class);
                             intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                             context.startActivity(intent);
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }

                        Log.d("onNext()方法--",entity.toString());
                    }



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
                        view.setContent("hello---->"+entity.getParams());

                    }else {
                        view.setContent("失败了--->"+entity.getMessage());
                    }
                }

                @Override
                public void onCodeError(BaseEntity<Map<String, String>> entity) {

                    Log.d(TAG+"ErrCode--",Integer.toString(entity.getCode()));
                    view.setContent("---onCodeError-->"+Integer.toString(entity.getCode())+"="+entity.getParams());
                }

                @Override
                public void onFailure(Throwable e, boolean network) throws Exception {
                    view.setContent("---请求失败了----->"+e.getMessage());
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
