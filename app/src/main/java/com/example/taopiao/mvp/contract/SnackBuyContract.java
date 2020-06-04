package com.example.taopiao.mvp.contract;

import com.example.taopiao.base.BasePresenter;
import com.example.taopiao.base.BaseView;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface SnackBuyContract {
    interface view  extends BaseView<RegistContract.presenter> {
        void setContent(List<Map<String,String>> maps);//设置内容
        void setUser(Map<String,String> maps);
        void laterDoOrder(String url);
        void messageSnackOrder(String message);
    }


    interface presenter extends BasePresenter {
       void getUser();
       void getSnack(Integer id);
       void uploadCode(MultipartBody.Part qr);
       void postSnackOrder(RequestBody requestBody);
    }
}
