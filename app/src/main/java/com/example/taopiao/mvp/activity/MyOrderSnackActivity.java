package com.example.taopiao.mvp.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taopiao.R;
import com.example.taopiao.adapter.OrderSnackAdapter;
import com.example.taopiao.application.MyApplication;
import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseObserver;
import com.example.taopiao.network.retrofit.BaseRequest;
import com.example.taopiao.utils.QREncodingCodeUtils;
import com.google.zxing.WriterException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyOrderSnackActivity extends AppCompatActivity {
    @BindView(R.id.goback_os)
    ImageView goback;
    private Integer user_id;
    @BindView(R.id.have_order_snack)
    ListView listView;
   private OrderSnackAdapter orderSnackAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_snack);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        MyApplication app=MyApplication.getInstance();
        user_id=app.user_id;
        getMySnackOrder();

    }

    @OnClick({R.id.goback_os})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.goback_os:
                finish();
                break;
        }
    }
    public void getMySnackOrder(){

        BaseRequest.getInstance().apiServices.userSnackOrder(user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((Observer<BaseEntity<List<Map<String, String>>>>) new BaseObserver<List<Map<String, String>>>(MyOrderSnackActivity.this) {
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
                            orderSnackAdapter=new OrderSnackAdapter(MyOrderSnackActivity.this,entity.getParams());
                            listView.setAdapter(orderSnackAdapter);
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
}
