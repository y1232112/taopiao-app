package com.example.taopiao.mvp.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.taopiao.R;
import com.example.taopiao.adapter.CinemaAdapter;
import com.example.taopiao.adapter.HotFilmAdapter;
import com.example.taopiao.adapter.MyAdapter1;
import com.example.taopiao.adapter.SpinnerAdapter2;
import com.example.taopiao.application.MyApplication;
import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseObserver;
import com.example.taopiao.mvp.activity.CinemaActivity;
import com.example.taopiao.mvp.activity.CityActivity;
import com.example.taopiao.mvp.activity.FilmDetailActivity;
import com.example.taopiao.network.retrofit.BaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;


public class CinemaFragment extends Fragment implements TabHost.OnTabChangeListener {
    private Context context;
    private String city;
    @BindView(android.R.id.tabhost)
    TabHost tabHost2;
    @BindView(R.id.tv_city_cinema)
    TextView select_city_cinema_view;
   private List<Map<String, String>> will_film;

   private SpinnerAdapter2 spinnerAdapter2;
   private ArrayAdapter<String> arrayAdapter;
    private MyAdapter1 myAdapter;
    private CinemaAdapter cinemaAdapter;
    private HotFilmAdapter hotFilmAdapter;
    private List<String> mCountyList;
    private Integer user_id;
    private ListView listViewCinema;
    private ListView listViewHotFilm;
    private View view1;
    private View view2;
    private View view3;
    private View view4;
    private ListView view5;
    private TextView view6;
    private TextView view7;
    private TextView view8;
    private ImageView view9;
    private TextView view10;
    private Spinner mSping1;
    private Spinner mSping2;
    private Spinner mSping3;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cinema, container, false);
        ButterKnife.bind(this, view);
        select_city_cinema_view.setText(MyApplication.getInstance().city);


        //
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        MyApplication app = MyApplication.getInstance();
        city=app.city;
        user_id = app.user_id;
        getCinemaAndServe();
        getWillFilms(user_id);
        tabHost2.setup();
        TabWidget tabWidget = tabHost2.getTabWidget();//获取TabHost的头部
       mCountyList=app.mCountyList;

       getHotFilms();

//        获取视图对象
        view1 = inflater.inflate(R.layout.tab_4, tabHost2.getTabContentView());
        view2 = inflater.inflate(R.layout.tab_5, tabHost2.getTabContentView());
        view3 = inflater.inflate(R.layout.tab_6, tabHost2.getTabContentView());
        view4 = inflater.inflate(R.layout.list_item_1, null);
        view5 = view3.findViewById(R.id.tab_will_film_lv);
        listViewCinema=view2.findViewById(R.id.list_cinema);
        listViewHotFilm=view1.findViewById(R.id.tab_hot_film_lv);
        view6 = view4.findViewById(R.id.tab_will_film_title);
        view7 = view4.findViewById(R.id.tab_will_film_type);
        view8 = view4.findViewById(R.id.tab_will_film_actor);
        view9 = view4.findViewById(R.id.tab_will_film_img_1);
        view10 = view4.findViewById(R.id.tab_will_film_public_date);
        mSping1=view2.findViewById(R.id.spin_01);
        mSping2=view2.findViewById(R.id.spin_02);
        mSping3=view2.findViewById(R.id.spin_03);
        tabHost2.addTab(tabHost2.newTabSpec("tab4").setIndicator("热映", null).setContent(R.id.tab4));
        tabHost2.addTab(tabHost2.newTabSpec("tab_5").setIndicator("影院", null).setContent(R.id.tab5));
        tabHost2.addTab(tabHost2.newTabSpec("tab_6").setIndicator("待映", null).setContent(R.id.tab6));
        tabHost2.setCurrentTab(0);
        tabHost2.setOnTabChangedListener(this);


        TabWidget tabWidget1 = tabHost2.getTabWidget();//获取TabHost的头部
        for (int i=0; i<tabWidget1.getChildCount(); i++){
            //获取tabView项
            View view11;
            view11 = tabWidget1.getChildAt(i);
            view11.setBackgroundResource(R.drawable.bottom_none_tab_xtyle);
            view.getLayoutParams().height = (int) (view.getLayoutParams().height);

        }
        //

        spinnerAdapter2=new SpinnerAdapter2(getContext(),android.R.layout.simple_spinner_dropdown_item,mCountyList);
         mSping2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 String s = spinnerAdapter2.getItem(position);
                 Log.d("spinner", "onItemSelected: position--"+position);
                 Log.d("区",mCountyList.toString());
                 Log.d("你选择了", s);
                 if (s.equals("全城")){
                     Log.d("你选择了...if...", s);
                     updateSelectAllCity();
                 }else {
                     BaseRequest.getInstance().apiServices.searchCinemaBycountyCity(s, city)
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
                                             if (entity.getParams() != null) {
                                                 Log.d("onSuccess", entity.getParams().toString());
//                                             delCinemaAndServe(entity.getParams());
                                                 List<Map<String,String>> maps=entity.getParams();
                                                 if (maps.size()==0){
                                                     Toast.makeText(getContext(),"没有在这个区找到任何影院",Toast.LENGTH_SHORT).show();
                                                 }
                                                 if (maps.size()>0){
                                                     Log.d("影院信息：",maps.toString());

                                                     cinemaAdapter=new CinemaAdapter(getContext(),maps);
                                                     listViewCinema.setAdapter(cinemaAdapter);
                                                     listViewCinema.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                         @Override
                                                         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                             Intent intent=new Intent(getContext(), CinemaActivity.class);
                                                             intent.putExtra("cinema_id",cinemaAdapter.getRealId(position));
                                                             intent.putExtra("cinema_name",cinemaAdapter.getName(position));
                                                             intent.putExtra("address",cinemaAdapter.getAddress(position));
                                                             startActivity(intent);
                                                         }
                                                     });
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
                                         public void onCodeError(BaseEntity<List<Map<String, String>>> entity) {

                                         }

                                         @Override
                                         public void onFailure(Throwable e, boolean network) throws Exception {

                                         }
                                     }
                             );
                 }


             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

        return view;
    }

    @OnClick({R.id.tv_city_cinema})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_city_cinema:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getActivity(), CityActivity.class));
                    }
                }, 100);
        }
    }

    @Override
    public void onTabChanged(String tabId) {
           upTab(tabHost2);
    }

    public Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public void getWillFilms(Integer user_id) {
        BaseRequest.getInstance().apiServices.getWillFilms(user_id)
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

                        setAdarpter(entity.getParams());


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

                    }
                });


    }

    public void setAdarpter(List<Map<String, String>> maps) {

        if (getActivity() != null) {
             will_film = maps;

            myAdapter = new MyAdapter1(getContext(), maps, CinemaFragment.this);
            view5.setAdapter(myAdapter);
            view5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Button bt = view.findViewById(R.id.list_item_1_btn);
                    TextView tv = view.findViewById(R.id.tab_will_film_title);
                    Log.d("你点击了", tv.getText().toString());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getActivity(), FilmDetailActivity.class);
//                            传递影片Id信息
                            intent.putExtra("film_id", myAdapter.getRealId(position));
                            startActivity(intent);
                        }
                    }, 100);
                }
            });
        }

    }
//获得影院和影院服务信息
    public void getCinemaAndServe(){
        JSONObject object=new JSONObject();
        try {
            object.put("city",city);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = object.toString();
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        BaseRequest.getInstance().getApiServices().getCinemasAndServe(requestBody)
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
                        Log.d("onSuccess",entity.getParams().toString());
                        List<Map<String,String>> maps=entity.getParams();
                        if (maps!=null){
                            delCinemaAndServe(maps);
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.d("onSuccess",e.toString());
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onCodeError(BaseEntity<List<Map<String,String>>> entity) {
                        Toast.makeText(context,entity.getMessage(),Toast.LENGTH_LONG).show();
                        Log.d("onSuccess",entity.getMessage().toString());

                    }

                    @Override
                    public void onFailure(Throwable e, boolean network) throws Exception {

                    }
                });
    }

    public void updateSelectAllCity(){
        JSONObject object=new JSONObject();
        try {
            object.put("city",city);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = object.toString();
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        BaseRequest.getInstance().getApiServices().getCinemasAndServe(requestBody)
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
                        Log.d("onSuccess",entity.getParams().toString());
                        List<Map<String,String>> maps=entity.getParams();
                        if (maps.size()>0){
                            Log.d("影院信息：",maps.toString());

                            cinemaAdapter=new CinemaAdapter(getContext(),maps);
                            listViewCinema.setAdapter(cinemaAdapter);
                            listViewCinema.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent=new Intent(getContext(), CinemaActivity.class);
                                    intent.putExtra("cinema_id",cinemaAdapter.getRealId(position));
                                    intent.putExtra("cinema_name",cinemaAdapter.getName(position));
                                    intent.putExtra("address",cinemaAdapter.getAddress(position));
                                    startActivity(intent);
                                }
                            });
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.d("onSuccess",e.toString());
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onCodeError(BaseEntity<List<Map<String,String>>> entity) {
                        Toast.makeText(context,entity.getMessage(),Toast.LENGTH_LONG).show();
                        Log.d("onSuccess",entity.getMessage().toString());

                    }

                    @Override
                    public void onFailure(Throwable e, boolean network) throws Exception {

                    }
                });
    }

    public void upUi() {

        Log.d("更新：", "我执行了");
        getWillFilms(this.user_id);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private void upTab(TabHost mTabHost) {
        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            if (mTabHost.getCurrentTab() == i) {//选中
                tv.setTextColor(this.getResources().getColor(R.color.colorPrimaryDark));
            } else {//不选中
                tv.setTextColor(this.getResources().getColor(R.color.colorMainFont));
            }
        }

    }
    //处理获得的影院和服务信息
    public void delCinemaAndServe( List<Map<String,String>> maps){
            if (maps.size()>0){
                Log.d("影院信息：",maps.toString());
                mSping2.setAdapter(spinnerAdapter2);
                cinemaAdapter=new CinemaAdapter(getContext(),maps);
                listViewCinema.setAdapter(cinemaAdapter);
                listViewCinema.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent=new Intent(getContext(), CinemaActivity.class);
                            intent.putExtra("cinema_id",cinemaAdapter.getRealId(position));
                            intent.putExtra("cinema_name",cinemaAdapter.getName(position));
                             intent.putExtra("address",cinemaAdapter.getAddress(position));
                            startActivity(intent);
                    }
                });
                }

            }
            public void getHotFilms(){
                BaseRequest.getInstance().getApiServices().getHotFilms(city)
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
                                Log.d("onSuccess",entity.getParams().toString());
                                List<Map<String,String>> maps=entity.getParams();
                                if (maps!=null){
                                    if (maps.size()>0){
                                        setHotAdarpter(maps);
                                    }
//                                  hotFilmAdapter=new HotFilmAdapter(getContext(),maps);
//                                  listViewHotFilm.setAdapter(hotFilmAdapter);

                                }


                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                Log.d("onSuccess",e.toString());
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
    public void setHotAdarpter(List<Map<String, String>> maps) {

        if (getActivity() != null) {


            hotFilmAdapter = new HotFilmAdapter(getContext(), maps );
            listViewHotFilm.setAdapter(hotFilmAdapter);
            listViewHotFilm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getActivity(), FilmDetailActivity.class);
//                            传递影片Id信息
                            intent.putExtra("film_id", hotFilmAdapter.getRealId(position));
                            startActivity(intent);
                        }
                    }, 100);
                }
            });
        }

    }


}