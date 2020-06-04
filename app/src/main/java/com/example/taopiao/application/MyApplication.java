package com.example.taopiao.application;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.example.taopiao.R;
import com.example.taopiao.utils.ShareManager;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MyApplication extends Application {
    public  static  MyApplication  app;
    public static String Tag="*****MyApplication******";
    public  static  MyApplication  getInstance(){
        return  app;
    }
    public static String token;
    public static  int user_id=0;
    public static String city;
    public static List<Map<String,String>> will_films;
    public static String seletItem;
    public static List<Integer> wish_info;
    public static List<String> mCountyList;
    @Override
    public void onCreate() {

        super.onCreate();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)

                .build();
        ImageLoader.getInstance().init(config);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_lautcher)
                .showImageForEmptyUri(R.drawable.ic_lautcher)
                .showImageOnFail(R.drawable.ic_lautcher)
                .cacheInMemory(true)
                .cacheOnDisc(false)
                .displayer(new RoundedBitmapDisplayer(20))
                .build();

        //Initialize ImageLoader with configuration
        ImageLoader.getInstance().init(config);
        ShareManager manager=new ShareManager();
        token=manager.getToken(MyApplication.this);
        user_id=manager.getUserId(MyApplication.this);

        String temp0=manager.getCity(MyApplication.this);
//        if (temp0!=null){
//            Log.d(Tag+"city"+":",temp0);
//            city=temp0;
//        }else {
//            city="贵阳";
//        }
//        Log.d("******************************你的城市 是：",temp0);

//        manager.setShare(MyApplication.this,"city",city);
    }



}
