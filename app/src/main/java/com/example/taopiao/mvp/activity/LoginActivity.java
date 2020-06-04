package com.example.taopiao.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taopiao.R;
import com.example.taopiao.mvp.contract.LoginContract;
import com.example.taopiao.mvp.presenter.LoginPresenter;
import com.example.taopiao.utils.MD5Util;
import com.example.taopiao.utils.ShareManager;
import com.uber.autodispose.AutoDisposeConverter;

import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginContract.view {
    private static final String TAG="--登录--";
    private Context mcontex;
    private ShareManager shareManager;
    private LoginPresenter presenter;
    @BindView(R.id.login_name)
    EditText username;
    @BindView(R.id.login_pass)
    EditText password;
    @BindView(R.id.regist_link_view)
    TextView link_to_regist;
    @BindView(R.id.login_btn)
    Button login_btn;
    @BindView(R.id.tv_login)
    TextView showcontent;
     @BindView(R.id.login_goback)
     TextView gb_l;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        mcontex=getApplicationContext();
        shareManager=new ShareManager();

       ButterKnife.bind(this);
       presenter=new LoginPresenter(this,this);

    }
    @OnClick({R.id.regist_link_view,R.id.login_btn,R.id.login_goback})
    public void OnClick(View view) {
        String name=username.getText().toString().trim();
        String pwd=password.getText().toString().trim();
        try {
            pwd= MD5Util.stringMD5(pwd);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        switch (view.getId()) {
            case R.id.regist_link_view:
                new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(LoginActivity.this, RegistActivity.class));

                }
            }, 100);
            break;
        case R.id.login_btn:
               presenter.userLogin(name,pwd);



            break;
            case R.id.login_goback:
                finish();
                break;
        };
    }

    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return null;
    }

    @Override
    public void setContent(String content) {
        if(content.indexOf("token=")==0){

             Log.d(TAG,"token-->"+content);
        }else
        showcontent.setText(content);
    }
}
