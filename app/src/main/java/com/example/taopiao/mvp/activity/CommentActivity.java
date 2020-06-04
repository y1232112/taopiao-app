package com.example.taopiao.mvp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;

import android.view.View;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taopiao.R;
import com.example.taopiao.application.MyApplication;
import com.example.taopiao.mvp.contract.CommentContrat;
import com.example.taopiao.mvp.presenter.CommentPresenter;
import com.uber.autodispose.AutoDisposeConverter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class CommentActivity extends AppCompatActivity implements CommentContrat.view {
    @SuppressLint("ResourceType")
    @BindView(R.id.my_score_rating)
    RatingBar ratingBar;
    @BindView(R.id.comment_commit)
    TextView tc;
    @BindView(R.id.comment_goback)
    TextView t_go;
    @BindView(R.id.comment_content)
    TextView content;
   private int user_id=0;
    private int film_id;
    private static final int type=1;
    private CommentContrat.presenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        ButterKnife.bind(this);
        MyApplication app=MyApplication.getInstance();
        user_id=app.user_id;
        presenter=new CommentPresenter(this,this);
       int stars= ratingBar.getNumStars();

        Intent intent=getIntent();
         film_id = intent.getIntExtra("film_id", 0);
        Log.d("---comment--你获得的的id是：", String.valueOf(film_id));
        Log.d("---comment--你获得星星数是：", String.valueOf(stars));
        JSONObject object=new JSONObject();
        try {
            object.put("film_id",film_id);
            object.put("user_id",user_id);
            object.put("type",type);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json=object.toString();
        RequestBody requestBody= RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        presenter.doMyComment(requestBody);
    }
  @OnClick({R.id.comment_commit,R.id.comment_goback})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.comment_commit:
               float r= ratingBar.getRating();
                     String s1= String.valueOf(1);
                MyApplication app=MyApplication.getInstance();
                       String s2= String.valueOf(app.user_id);
                    String s3=content.getText().toString().trim();
                JSONObject object= new JSONObject();
               if (s3.equals("")){
                   Toast.makeText(CommentActivity.this,"评论不能为空",Toast.LENGTH_SHORT).show();
                   break;
               }
                try {
                    object.put("t_id",film_id);
                    object.put("type",1);
                    object.put("score",r*2);
                    object.put("from_uid",s2);
                    object.put("content",s3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                 presenter.commitComment(object.toString());

                Log.d("---comment--你获得星星数是：", String.valueOf(r)+"评论是："+content.getText());
                break;
            case R.id.comment_goback:
                finish();
        }
  }


    @Override
    public void showResult(String content) {
          Toast.makeText(CommentActivity.this,content,Toast.LENGTH_SHORT).show();
          finish();
    }

    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return null;
    }

    @Override
    public void showMyComment(Map<String, String> map) {
         if (map!=null){
             if (map.size()>0){
                 ratingBar.setRating(Integer.parseInt(map.get("score")));
                 content.setText(map.get("content"));
             }
         }
    }
}
