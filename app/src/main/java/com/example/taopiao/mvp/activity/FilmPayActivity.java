package com.example.taopiao.mvp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.taopiao.R;
import com.example.taopiao.application.MyApplication;
import com.example.taopiao.base.BaseEntity;
import com.example.taopiao.base.BaseObserver;
import com.example.taopiao.network.retrofit.BaseRequest;
import com.example.taopiao.utils.BitmapUtils;
import com.example.taopiao.utils.QREncodingCodeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FilmPayActivity extends AppCompatActivity {
    @BindView(R.id.pay_edt)
    EditText ps;
    @BindView(R.id.pay_btn)
    Button button;
    private String content;
    private String order_id;
    private String pay_type;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acttivity_film_pay);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        Intent intent=getIntent();
        content=intent.getStringExtra("content");
        order_id=intent.getStringExtra("order_id");
        pay_type=intent.getStringExtra("pay_type");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              doOrderFilm();
            }
        });

    }

    public void doOrderFilm(){

        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.avatar);
        Bitmap bitmap1= QREncodingCodeUtils.receiveQRBitmap(content,400,400,"UTF-8",
                "H","1", Color.BLACK,Color.WHITE,bitmap,0.2F,null);
        JSONObject ob1=new JSONObject();
        MyApplication app=MyApplication.getInstance();
        try {
            ob1.put("user_id",app.user_id);
            ob1.put("type","image");
            //封装两个body
            RequestBody des=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),ob1.toString());
//          verifyStoragePermissions(SnackBuyActivity.this);
            File file= BitmapUtils.compressImage(bitmap1);
            MultipartBody.Builder builder=new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("desc","这是图片");

            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part mfile =
                    MultipartBody.Part.createFormData("file", file.getName(), requestFile);


            BaseRequest.getInstance().getApiServices().doQRCode(mfile)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe( new BaseObserver<Map<String,String>>(FilmPayActivity.this){
                        //观察者数据的

                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BaseEntity<Map<String,String>> entity) {
                            super.onNext(entity);
                            Log.d("onNext()方法--=====",entity.toString());
                            try {
                                addPayInfo(entity.getMessage());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        @Override
                        public void onSuccess(BaseEntity<Map<String,String>> entity) {
                            if(entity.isSuccess()){

                            }else {

                            }
                        }

                        @Override
                        public void onCodeError(BaseEntity<Map<String,String>> entity) {



                        }

                        @Override
                        public void onFailure(Throwable e, boolean network) throws Exception {

                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void addPayInfo(String phone_code) throws JSONException {
        JSONObject object=new JSONObject();
        object.put("order_id",order_id);
        object.put("phone_code",phone_code);
        object.put("pay_type",pay_type);
        String json=object.toString();
        RequestBody requestBody=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);
        BaseRequest.getInstance().getApiServices().postFilmOrderPay(requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( new BaseObserver<Map<String,String>>(FilmPayActivity.this){
                    //观察者数据的

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseEntity<Map<String,String>> entity) {
                        super.onNext(entity);
                        Log.d("onNext()方法--====",entity.toString());
                        Toast.makeText(FilmPayActivity.this,entity.getMessage(),Toast.LENGTH_SHORT).show();
                   finish();
                    }
                    @Override
                    public void onSuccess(BaseEntity<Map<String,String>> entity) {
                        if(entity.isSuccess()){

                        }else {

                        }
                    }

                    @Override
                    public void onCodeError(BaseEntity<Map<String,String>> entity) {



                    }

                    @Override
                    public void onFailure(Throwable e, boolean network) throws Exception {
                          finish();
                    }
                });
    }

}
