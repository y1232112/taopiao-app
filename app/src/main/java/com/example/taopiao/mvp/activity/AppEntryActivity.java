package com.example.taopiao.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taopiao.R;

public class AppEntryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_entry);
//        设置两秒忠后新进入主页面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
          startActivity(new Intent(AppEntryActivity.this,LoginActivity.class));

            finish();
            }
        },2000);
    }
}
