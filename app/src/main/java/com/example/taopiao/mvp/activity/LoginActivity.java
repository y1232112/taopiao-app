package com.example.taopiao.mvp.activity;

import android.content.Intent;
import android.os.Bundle;


import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taopiao.R;
import com.example.taopiao.mvp.presenter.registPresenter;
import com.example.taopiao.utils.MD5Util;

import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity  {
    private registPresenter presenter;
    @BindView(R.id.login_name)
    EditText username;
    @BindView(R.id.login_pass)
    EditText password;
    @BindView(R.id.regist_link_view)
    TextView link_to_regist;
    @BindView(R.id.login_btn)
    Button login_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       ButterKnife.bind(this);

    }
    @OnClick({R.id.regist_link_view,R.id.login_btn})
    public void OnClick(View view) {
        String name=username.getText().toString().trim();
        String pwd=password.getText().toString().trim();
        switch (view.getId()) {
            case R.id.regist_link_view:
                new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(LoginActivity.this, RegistActivity.class));
                    finish();
                }
            }, 100);
            break;
        case R.id.login_btn:
            Toast.makeText(LoginActivity.this,"用户名："+name+"密码为："
                    +pwd,Toast.LENGTH_SHORT).show();
//            对用户名和密码进行加密
            String s = null;
            try {
                s = MD5Util.stringMD5(pwd);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            Log.d("加码（32位）：",s);

            break;
        };
    }

}
