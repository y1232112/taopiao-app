package com.example.taopiao.mvp.activity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taopiao.R;
import com.example.taopiao.mvp.contract.registContract;
import com.example.taopiao.mvp.presenter.registPresenter;
import com.uber.autodispose.AutoDisposeConverter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistActivity extends AppCompatActivity implements registContract.view {
    private registPresenter presenter;
    @BindView(R.id.regist_name)
    EditText registname;
    @BindView(R.id.regist_pass)
    EditText registpass;
    @BindView(R.id.regist_sex)
    EditText registsex;
    @BindView(R.id.regist_phone)
    EditText registphone;
    @BindView(R.id.regist_btn)
    Button regist_btn;
    @BindView(R.id.tv_regist)
    TextView showcontent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ButterKnife.bind(this);
        presenter=new registPresenter(this,this);
    }

    @OnClick(R.id.regist_btn)
    public void clicked(View view){
        String name=registname.getText().toString().trim();
        String pwd=registpass.getText().toString().trim();
        String sex=registsex.getText().toString().trim();
        String phone=registphone.getText().toString().trim();
        switch (view.getId()){
            case R.id.regist_btn:
                presenter.userRegist(name,pwd,sex,phone);
                Log.d("调试信息-------------",name+"--"+pwd+"--"+sex+"--"+phone);
                break;
        }
    }
    @Override
    public void setContent(String content) {
        showcontent.setText(content);
    }

//    @Override
//    public void showLoading() {
//
//    }
//
//    @Override
//    public void hideLoading() {
//
//    }
//
//    @Override
//    public void onError(Throwable throwable) {
//
//    }

    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return null;
    }
}
