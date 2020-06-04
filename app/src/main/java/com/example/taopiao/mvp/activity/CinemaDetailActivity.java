package com.example.taopiao.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taopiao.R;
import com.example.taopiao.adapter.ServeAdapter;
import com.example.taopiao.mvp.contract.CinemaDetailContract;
import com.example.taopiao.mvp.presenter.CinemaDetailPresenter;
import com.uber.autodispose.AutoDisposeConverter;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CinemaDetailActivity extends AppCompatActivity  implements CinemaDetailContract.view {
    private Integer cinema_id;
    private CinemaDetailContract.presenter presenter;
    private ServeAdapter serveAdapter;
    @BindView(R.id.bg_cinema_detail_go_back)
    ImageView goback;
    @BindView(R.id.address_info)
    TextView address_info;
    @BindView(R.id.phone_info)
    TextView phone_info;
    @BindView(R.id.detail_cinema_title)
    TextView detail_cinema_title;
    @BindView(R.id.gonggao)
    TextView notice;
    @BindView(R.id.list_serve)
    ListView list_serve;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_detail);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        cinema_id=intent.getIntExtra("cinema_id",0);
         presenter=new CinemaDetailPresenter(this,this);
         presenter.getCinemaDetail(cinema_id);
         presenter.getCienmaServe(cinema_id);


    }
    @OnClick({R.id.bg_cinema_detail_go_back})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.bg_cinema_detail_go_back:
                finish();
                break;
                default:
                    break;

        }
    }
    @Override
    public void setContent(List<Map<String, String>> maps) {
        if (maps.size()>0){
            Map<String,String> map=maps.get(0);
            address_info.setText(map.get("county")+map.get("address"));
            phone_info.setText(map.get("phone"));
            detail_cinema_title.setText(map.get("cinema_name"));
            notice.setText(map.get("notice"));
        }


    }

    @Override
    public void setServe(List<Map<String, String>> maps) {
        if (maps.size()>0){
             serveAdapter=new ServeAdapter(this,maps);
             list_serve.setAdapter(serveAdapter);

        }
    }

    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return null;
    }
}
