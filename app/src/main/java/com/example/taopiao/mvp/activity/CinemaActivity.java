package com.example.taopiao.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taopiao.R;
import com.example.taopiao.adapter.SnackAdapter;
import com.example.taopiao.mvp.contract.CinemaContract;
import com.example.taopiao.mvp.presenter.CinemaPresenter;
import com.uber.autodispose.AutoDisposeConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CinemaActivity extends AppCompatActivity implements CinemaContract.view {
    private String cinema_name;
    private int cinema_id;
    private String address;
    private CinemaContract.presenter presenter;
    private SnackAdapter snackAdapter1;
    private SnackAdapter snackAdapter2;
    private SnackAdapter snackAdapter3;
    private List<Map<String,String>> maps1;
    private List<Map<String,String>> maps2;
    private List<Map<String,String>> maps3;

    @BindView(R.id.cinema_name)
    TextView cinemaName;
    @BindView(R.id.cinema_title_01)
    TextView title_01;
    @BindView(R.id.cinema_address_01)
    TextView address_01;
    @BindView(R.id.dan_ren)
    ListView dan_ren;
    @BindView(R.id.shuang_ren)
    ListView shuang_ren;
    @BindView(R.id.duo_ren)
    ListView duo_ren;
    @BindView(R.id.label_danren)
    TextView lb_danren;
    @BindView(R.id.label_shuangren)
    TextView lb_shuangren;
    @BindView(R.id.label_duoren)
    TextView lb_duoren;
    @BindView(R.id.a_cinema_container)
    RelativeLayout a_cinema_container;
    @BindView(R.id.bg_cinema_goback)
    ImageView goback;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema);
        ButterKnife.bind(this);
        //获取
        Intent intent=getIntent();
       cinema_id=intent.getIntExtra("cinema_id",0);
       cinema_name=intent.getStringExtra("cinema_name");
        address=intent.getStringExtra("address");
       cinemaName.setText(cinema_name);
       title_01.setText(cinema_name);
       address_01.setText(address);
       presenter=new CinemaPresenter(this,this);
       presenter.getCinemaInfo(cinema_id);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

    }

    @Override
    public void setContent(String content) {

    }
    @OnClick({R.id.a_cinema_container,R.id.bg_cinema_goback})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.a_cinema_container:
                Intent intent=new Intent(CinemaActivity.this,CinemaDetailActivity.class);
                intent.putExtra("cinema_id",cinema_id);
                startActivity(intent);
                break;
            case R.id.bg_cinema_goback:
                finish();
                default:
                    break;
        }

    }
    @Override
    public void setCinema(List<Map<String, String>> maps) {
            Log.d("小食信息",maps.toString());
            maps1=new ArrayList<>();
            maps2=new ArrayList<>();
            maps3=new ArrayList<>();
             if (maps.size()>0){

                  for (int i=0;i<maps.size();i++){
                      Map<String,String> map=maps.get(i);
                      if (map.get("num_type").equals("单人餐")){
                              maps1.add(map);

                      }else if (map.get("num_type").equals("双人餐")){
                          maps2.add(map);

                      }else if (map.get("num_type").equals("多人餐")){
                          maps3.add(map);

                      }

                  }
                 Log.d("小食信息",maps1.toString());
                 Log.d("小食信息",maps2.toString());
                 Log.d("小食信息",maps3.toString());
                  if (maps1.size()>0){
                      snackAdapter1=new SnackAdapter(CinemaActivity.this,maps1);
                      dan_ren.setAdapter(snackAdapter1);


                  }else if (maps1.size()==0){
                      lb_danren.setVisibility(View.GONE);

                  }
                 if (maps2.size()>0){
                     snackAdapter2=new SnackAdapter(CinemaActivity.this,maps2);
                     shuang_ren.setAdapter(snackAdapter2);

                 }else if (maps2.size()==0){
                     lb_shuangren.setVisibility(View.GONE);
                 }
                 if (maps3.size()>0){
                     snackAdapter3=new SnackAdapter(CinemaActivity.this,maps3);
                     duo_ren.setAdapter(snackAdapter3);

                 }else if (maps3.size()==0){
                     lb_duoren.setVisibility(View.GONE);
                 }
             }else {
                 lb_danren.setVisibility(View.GONE);
                 lb_shuangren.setVisibility(View.GONE);
                 lb_duoren.setVisibility(View.GONE);
             }
    }

    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return null;
    }
}
