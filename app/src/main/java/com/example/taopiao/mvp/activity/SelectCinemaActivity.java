package com.example.taopiao.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taopiao.R;
import com.example.taopiao.adapter.SelectCinemaAdapter;
import com.example.taopiao.application.MyApplication;
import com.example.taopiao.mvp.contract.SelectCinemaContract;
import com.example.taopiao.mvp.presenter.SelectCinemaPresenter;
import com.google.gson.JsonObject;
import com.uber.autodispose.AutoDisposeConverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;

public class SelectCinemaActivity  extends AppCompatActivity implements SelectCinemaContract.view {
    private Integer film_id;
    private String city= MyApplication.getInstance().city;
    private SelectCinemaContract.presenter presenter;
    private SelectCinemaAdapter selectCinemaAdapter;

    @BindView(R.id.list_cinema_select)
    ListView list_cinema;
    @BindView(R.id.bg_cinema_detail_go_back)
    ImageView goback;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_cinema);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        Intent intent=getIntent();
//        String id=intent.getStringExtra("film_id");
        film_id=intent.getIntExtra("film_id",0);
        ButterKnife.bind(this);
        presenter=new SelectCinemaPresenter(this,this);
        JSONObject object=new JSONObject();
        try {
            object.put("film_id",film_id);
            object.put("city",city);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = object.toString();
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        presenter.getCinemasHasSchedule(requestBody);
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
    public void showCinemas(List<Map<String, String>> maps) {
              if (maps.size()>0){
                  selectCinemaAdapter=new SelectCinemaAdapter(SelectCinemaActivity.this,maps);
                  list_cinema.setAdapter(selectCinemaAdapter);
                  list_cinema.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                      @Override
                      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                          Intent intent1=new Intent(SelectCinemaActivity.this,AfileCinemaSchedule.class);
                          intent1.putExtra("cinema_name",selectCinemaAdapter.getName(position));
                          intent1.putExtra("cinema_id",selectCinemaAdapter.getRealId(position));

                          startActivity(intent1);
                      }

                  });
              }else {

              }
    }

    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return null;
    }
    public Integer getFilm_id(){
        return film_id;
    }
}
