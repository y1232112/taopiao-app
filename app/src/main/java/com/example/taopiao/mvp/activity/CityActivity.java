package com.example.taopiao.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taopiao.MainActivity;
import com.example.taopiao.R;
import com.example.taopiao.application.MyApplication;
import com.example.taopiao.utils.CityUtils;
import com.example.taopiao.utils.ShareManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityActivity extends AppCompatActivity {
    @BindView(R.id.select_tv_city)
    TextView select_text_view;
    @BindView(R.id.select_city_list)
    ListView cityListView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        ButterKnife.bind(this);
        select_text_view.setText(MyApplication.getInstance().city);
        CityUtils cityUtils=new CityUtils();
       String[] cities= cityUtils.mCitiesStrings;
//        Log.d("适配器：",cities.toString());
        ArrayAdapter<String> cityListAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cities);
        //listview视图加载适配器
        cityListView.setAdapter(cityListAdapter);
        //为列表视图中选中的项添加响应事件
        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String result=parent.getItemAtPosition(position).toString();
                if (!result.equals("A")&&
                        !result.equals("A")&&
                        !result.equals("B")&&
                        !result.equals("C")&&
                        !result.equals("D")&&
                        !result.equals("E")&&
                        !result.equals("F")&&
                        !result.equals("G")&&
                        !result.equals("H")&&
                        !result.equals("J")&&
                        !result.equals("K")&&
                        !result.equals("L")&&
                        !result.equals("M")&&
                        !result.equals("N")&&
                        !result.equals("P")&&
                        !result.equals("Q")&&
                        !result.equals("R")&&
                        !result.equals("S")&&
                        !result.equals("T")&&
                        !result.equals("W")&&
                        !result.equals("X")&&
                        !result.equals("Y")&&
                        !result.equals("Z")
                ){
                   MyApplication app= MyApplication.getInstance();
                   app.city=result;
                    ShareManager.setShare(CityActivity.this,"city",result);

                    select_text_view.setText(result);
                    Toast.makeText(CityActivity.this,"您点击了："+result,Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            startActivity(new Intent(CityActivity.this, MainActivity.class));
                        }
                    },100);
                }
//                Toast.makeText(CityActivity.this,"您点击了："+result,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
