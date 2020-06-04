package com.example.taopiao.mvp.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.taopiao.R;
import com.example.taopiao.adapter.HotFilmAdapter;
import com.example.taopiao.adapter.HotFilmAdapter2;
import com.example.taopiao.adapter.MyAdapter1;
import com.example.taopiao.adapter.MyAdapter2;
import com.example.taopiao.application.MyApplication;
import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseObserver;
import com.example.taopiao.mvp.activity.CityActivity;
import com.example.taopiao.mvp.activity.FilmDetailActivity;
import com.example.taopiao.network.retrofit.BaseRequest;
import com.example.taopiao.widget.MyHorizontalScrollView2;
import com.example.taopiao.widget.MyHorizontalScrollView3;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment implements TabHost.OnTabChangeListener {
    @BindView(R.id.tv_city_home)
    TextView select_city_view;
    private String city;
    @BindView(android.R.id.tabhost)
    TabHost tabHost1;
    private Context context;
    private ListView mList;
    private Integer user_id;
    private List<Map<String, String>> willfilm;
    private MyAdapter2 myAdapter2;
    private HotFilmAdapter2 hotFilmAdapter1;
    private MyHorizontalScrollView3 myHorizontalScrollView3;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,view);
        select_city_view.setText(MyApplication.getInstance().city);
        context=getActivity();
        MyApplication app = MyApplication.getInstance();
        city=app.city;
        user_id = app.user_id;
        getHotFilms();
        getWillFilms(user_id);
        tabHost1.setup();
        View v1= inflater.inflate(R.layout.tab_1,tabHost1.getTabContentView());
        View v2=inflater.inflate(R.layout.tab_2,tabHost1.getTabContentView());
        View v3=inflater.inflate(R.layout.tab_3,tabHost1.getTabContentView());
        tabHost1.addTab(tabHost1.newTabSpec("tab1").setIndicator("推荐").setContent(R.id.tab1));
        tabHost1.addTab(tabHost1.newTabSpec("tab_2").setIndicator("新片单").setContent(R.id.tab2));
        tabHost1.addTab(tabHost1.newTabSpec("tab_3").setIndicator("资讯").setContent(R.id.tab3));
        tabHost1.setCurrentTab(0);
        tabHost1.setOnTabChangedListener(this);
        mList=v1.findViewById(R.id.tab_main_lv);
        myHorizontalScrollView3=v1.findViewById(R.id.hotfilm_h_scroll);
        //循环每个tabView
        TabWidget tabWidget1 = tabHost1.getTabWidget();//获取TabHost的头部
        for (int i=0; i<tabWidget1.getChildCount(); i++){
            //获取tabView项
            View view11;
            view11 = tabWidget1.getChildAt(i);
            view11.setBackgroundResource(R.drawable.bottom_none_tab_xtyle);
            view.getLayoutParams().height = (int) (view.getLayoutParams().height);

        }

        return view;
    }

    @Override
    public void onStart() {
        TextView textView=getActivity().findViewById(R.id.tv_city_home);
        Log.d("------text_--fragment--",textView.getText().toString());
        super.onStart();
    }
    @OnClick({R.id.tv_city_home})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_city_home:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getActivity(), CityActivity.class));
                    }
                },100);
        }
    }
    public void setAdarpter(List<Map<String, String>> maps) {

        if (getActivity() != null) {
           willfilm = maps;

            myAdapter2= new MyAdapter2(getContext(), maps, HomeFragment.this);
            mList.setAdapter(myAdapter2);
           mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                            intent.putExtra("film_id", myAdapter2.getRealId(position));
                            startActivity(intent);
                        }
                    }, 100);
                }
            });
        }

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

    public void getHotFilms(){
        BaseRequest.getInstance().getApiServices().getHotFilms(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Observer<BaseEntity<List<Map<String,String>>>>) new BaseObserver<List<Map<String,String>>>(getContext()) {
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
                                hotFilmAdapter1=new HotFilmAdapter2(getContext(),maps);
                                myHorizontalScrollView3.initDatas(hotFilmAdapter1);
                            }


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

    public void upUi() {

        Log.d("更新：", "我执行了");
        getWillFilms(this.user_id);

    }

    @Override
    public void onTabChanged(String tabId) {
        super.onDestroyView();
         upTab(tabHost1);
         Log.d("tabid:",tabId);
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

}
