package com.example.taopiao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.taopiao.mvp.presenter.registPresenter;


public class MainActivity extends AppCompatActivity  {
   private registPresenter presenter;
//@BindView(R.id.login_btn)
//Button button_login;
//@BindView(R.id.search_name)
//EditText username;
//@BindView(R.id.search_pass)
//EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
//        presenter=new registPresenter(this,this);
    }


}
