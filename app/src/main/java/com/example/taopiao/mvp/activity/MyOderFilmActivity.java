package com.example.taopiao.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taopiao.R;
import com.example.taopiao.adapter.OrderFilmAdapter;
import com.example.taopiao.adapter.OrderSnackAdapter;
import com.example.taopiao.application.MyApplication;
import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseObserver;
import com.example.taopiao.network.retrofit.BaseRequest;
import com.example.taopiao.widget.PassEditView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MyOderFilmActivity extends AppCompatActivity {
    @BindView(R.id.goback_of)
    ImageView goback;
    private Integer user_id;
    private OrderFilmAdapter orderFilmAdapter;
    @BindView(R.id.have_order_film)
    ListView listView;
    @BindView(R.id.of_root)
    FrameLayout root;
    private String payType;
    private static MyOderFilmActivity myOderFilmActivity;
    public MyOderFilmActivity(){
        myOderFilmActivity=this;
    }
    public static MyOderFilmActivity getInstance(){
        return myOderFilmActivity;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_film);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        MyApplication app=MyApplication.getInstance();
        user_id=app.user_id;
        getMyFilmOrder();
    }
    @OnClick({R.id.goback_of})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.goback_of:
            finish();
                break;
        }
    }

    public void getMyFilmOrder(){

        BaseRequest.getInstance().apiServices.userUserFilmOrder(user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Observer<BaseEntity<List<Map<String, String>>>>) new BaseObserver<List<Map<String, String>>>(MyOderFilmActivity.this) {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(BaseEntity<List<Map<String, String>>> entity) {

                    }

                    @Override
                    public void onNext(BaseEntity<List<Map<String, String>>> entity) {
                        super.onNext(entity);
                        Log.d("onNext", entity.getParams().toString());
                        if (entity.getParams()!=null){
                           orderFilmAdapter=new OrderFilmAdapter(MyOderFilmActivity.this,entity.getParams());
                            listView.setAdapter(orderFilmAdapter);
                        }



                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onCodeError(BaseEntity<List<Map<String, String>>> entity) {

                    }

                    @Override
                    public void onFailure(Throwable e, boolean network) throws Exception {
                        Log.d("++++++++++++",e.toString());
                    }
                });
    }

    public void showPipupWindow(String content,String order_id){
        //加载自定义布局
        View contentView= LayoutInflater.from(MyOderFilmActivity.this).inflate(R.layout.popup_window_pay,null);

        RelativeLayout lin1=contentView.findViewById(R.id.pay_zhifubao);
        RelativeLayout lin2=contentView.findViewById(R.id.pay_weixin);
        RelativeLayout lin3=contentView.findViewById(R.id.pay_union);
        CheckBox box1=(CheckBox) lin1.findViewById(R.id.select_zhifubao);
        CheckBox box2=(CheckBox)lin2.findViewById(R.id.select_weixin);
        CheckBox box3=(CheckBox)lin3.findViewById(R.id.select_union);


        ImageView nopay_return=contentView.findViewById(R.id.nopay_return);
        final PopupWindow popupWindow=new PopupWindow(contentView,RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);


        nopay_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        lin1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                box1.setChecked(true);
                box2.setChecked(false);
                box3.setChecked(false);
                payType="支付宝";
                             Intent intent=new Intent(MyOderFilmActivity.this,FilmPayActivity.class);
                             intent.putExtra("order_id",order_id);
                             intent.putExtra("content",content);
                             intent.putExtra("pay_type",payType);
                             popupWindow.dismiss();
                             startActivity(intent);

            }
        });
        lin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                box1.setChecked(false);
                box2.setChecked(true);
                box3.setChecked(false);
                payType="微信";
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(MyOderFilmActivity.this,FilmPayActivity.class);
                        intent.putExtra("order_id",order_id);
                        intent.putExtra("content",content);
                        intent.putExtra("pay_type",payType);
                        popupWindow.dismiss();
                        startActivity(intent);
                    }
                },200);
            }
        });
        lin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                box1.setChecked(false);
                box2.setChecked(false);
                box3.setChecked(true);
                payType="银联";
                Intent intent=new Intent(MyOderFilmActivity.this,FilmPayActivity.class);
                intent.putExtra("order_id",order_id);
                intent.putExtra("content",content);
                intent.putExtra("pay_type",payType);
                popupWindow.dismiss();
                startActivity(intent);
            }
        });

        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.tranparent_boder_square));
//        popupWindow.setHeight(90);
//        popupWindow.setWidth(90);
        popupWindow.showAtLocation(root,Gravity.BOTTOM,0,0);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyFilmOrder();
    }
}
