package com.example.taopiao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.taopiao.application.MyApplication;
import com.example.taopiao.mvp.contract.MainContract;
import com.example.taopiao.mvp.fragment.CinemaFragment;
import com.example.taopiao.mvp.fragment.HomeFragment;
import com.example.taopiao.mvp.fragment.MineFragment;
import com.example.taopiao.mvp.fragment.VideoFragment;
import com.example.taopiao.mvp.presenter.MainPresenter;
import com.example.taopiao.mvp.presenter.RegistPresenter;
import com.example.taopiao.utils.JsonUtils;
import com.example.taopiao.utils.ShareManager;
import com.example.taopiao.utils.readCityUtils;
import com.uber.autodispose.AutoDisposeConverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import butterknife.ButterKnife;
import butterknife.OnClick;



public class MainActivity extends AppCompatActivity implements MainContract.view {
    private Context mContext;
    private static String TAG="---MainActivity--->";
    public static MainActivity mainActivity;
    private static List<String> mCountyList;
    @BindView(R.id.imv_1)
    ImageView img_home;
    @BindView(R.id.imv_2)
    ImageView cinema;
    @BindView(R.id.imv_3)
    ImageView video;
    @BindView(R.id.imv_4)
    ImageView mine;
    @BindView(R.id.main_bt_tv1)
    TextView tv1;
    @BindView(R.id.main_bt_tv2)
    TextView tv2;
    @BindView(R.id.main_bt_tv3)
    TextView tv3;
    @BindView(R.id.main_bt_tv4)
    TextView tv4;

//    @BindView(R.id.tv_city)
//    TextView tv_city;

     private MainPresenter presenter;
     private HomeFragment homeFragment;
     private CinemaFragment cinemaFragment;
     private VideoFragment videoFragment;
     private MineFragment mineFrament;
     private List<Fragment> fragmentList;
     private static String bottomStatus="HOME";
     private FragmentManager manager;
     public static List<Object> hotFilms;
     public List<Map<String,String>> willFilms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();
        ButterKnife.bind(this);
        mainActivity=this;
        //******************************


        //**********************************
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
       MyApplication app=MyApplication.getInstance();
      int id=app.user_id;

        presenter=new MainPresenter(this,this);

        Map<String,String> map= ShareManager.read(this);

        ArrayList<String> list=new ArrayList<>();
        try {
            JSONObject object= readCityUtils.getJsonObject("city.json",this);
            Log.d("---------json--read---",object.toString());
            Log.d("******************************你的城市 是：",app.city);
            mCountyList=JsonUtils.getMyCityInfo(object,app.city);
            app.mCountyList=mCountyList;
            Log.d("---------json--read-  你所在城市的区县  --",mCountyList.toString());
           list= JsonUtils.getMainCity(object);
            Log.d("---------json--read---",list.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//
        initView();


    }


    private void initView(){
        img_home.setColorFilter(Color.RED);
        tv1.setTextColor(Color.RED);
        FragmentTransaction  transaction=manager.beginTransaction();
        transaction.replace(R.id.main_fragment,new HomeFragment()).commit();
    }

    @OnClick({R.id.imv_4,R.id.imv_1,R.id.imv_2,R.id.imv_3})
    public void OnClick(View view){

        FragmentTransaction  transaction=manager.beginTransaction();
     switch (view.getId()){

         case R.id.imv_1:
             tv1.setTextColor(Color.RED);
             tv2.setTextColor(Color.BLACK);
             tv3.setTextColor(Color.BLACK);
             tv4.setTextColor(Color.BLACK);
             img_home.setColorFilter(Color.RED);
             cinema.setColorFilter(Color.BLACK);
             video.setColorFilter(Color.BLACK);
             mine.setColorFilter(Color.BLACK);
          transaction.replace(R.id.main_fragment,new HomeFragment()).commit();

             break;
         case R.id.imv_2:
             tv2.setTextColor(Color.RED);
             tv1.setTextColor(Color.BLACK);
             tv3.setTextColor(Color.BLACK);
             tv4.setTextColor(Color.BLACK);
             img_home.setColorFilter(Color.BLACK);
             cinema.setColorFilter(Color.RED);
             video.setColorFilter(Color.BLACK);
             mine.setColorFilter(Color.BLACK);
             transaction.replace(R.id.main_fragment,new CinemaFragment()).commit();

             break;
         case R.id.imv_3:
             tv2.setTextColor(Color.BLACK);
             tv1.setTextColor(Color.BLACK);
             tv3.setTextColor(Color.RED);
             tv4.setTextColor(Color.BLACK);
             img_home.setColorFilter(Color.BLACK);
             cinema.setColorFilter(Color.BLACK);
             video.setColorFilter(Color.RED);
             mine.setColorFilter(Color.BLACK);
             transaction.replace(R.id.main_fragment,new VideoFragment()).commit();
             break;
         case R.id.imv_4:
             tv2.setTextColor(Color.BLACK);
             tv1.setTextColor(Color.BLACK);
             tv3.setTextColor(Color.BLACK);
             tv4.setTextColor(Color.RED);
             img_home.setColorFilter(Color.BLACK);
             cinema.setColorFilter(Color.BLACK);
             video.setColorFilter(Color.BLACK);
             mine.setColorFilter(Color.RED);
            transaction.replace(R.id.main_fragment,new MineFragment()).commit();
             break;
             default:
                 break;
     }
    }


    @Override
    public void setContent(String content) {

    }

    @Override
    public void setWillFilms(List<Map<String, String>> maps) {
        this.willFilms=maps;
        Log.d(TAG+"willFilms",maps.toString());



    }

    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return null;
    }
}
