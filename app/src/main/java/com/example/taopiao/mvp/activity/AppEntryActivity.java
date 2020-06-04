package com.example.taopiao.mvp.activity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taopiao.MainActivity;
import com.example.taopiao.R;
import com.example.taopiao.application.MyApplication;
import com.example.taopiao.utils.ShareManager;

import java.util.Map;

import butterknife.BindView;

public class AppEntryActivity extends AppCompatActivity {
    private ShareManager shareManager;
    private Context mContext;
    private static final String DEFAULT_CITY="贵阳";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        处理sharepreferrence
        mContext = this;
        MyApplication app=MyApplication.getInstance();
        Map<String,String> map=shareManager.read(mContext);
        String token=map.get("token");
        String nickname =map.get("nickname");
        String password=map.get("password");
        String city=map.get("city");
        if (map.get("user_id")!=null){
            Integer user_id=Integer.parseInt(map.get("user_id"));
            app.user_id=user_id;
        }
        if (city==null){
          app.city=DEFAULT_CITY;
        }else {
            app.city=map.get("city");
        }
        if (nickname==null||password==null){

        }else {

        }

        Log.d("---sp工具---","token:"+token);
        Log.d("---sp工具---","city:"+city);
       if(token.equals("")){
           //        设置两秒忠后进入登录页面
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   startActivity(new Intent(AppEntryActivity.this,LoginActivity.class));

                   finish();
               }
           },2000);
       }else {
           //        设置两秒后新进入主页面
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                   startActivity(new Intent(AppEntryActivity.this,MainActivity.class));

                   finish();
               }
           },2000);
       }
//        获取本地token


    }
}
