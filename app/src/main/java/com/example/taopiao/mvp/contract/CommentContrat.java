package com.example.taopiao.mvp.contract;

import com.example.taopiao.base.BasePresenter;
import com.example.taopiao.base.BaseView;

import java.util.Map;

import okhttp3.RequestBody;

public interface CommentContrat {
    interface view extends BaseView<CinemaContract.presenter> {
          void showResult(String content);
          void showMyComment(Map<String,String> map);
    }
    interface presenter extends BasePresenter {
          void commitComment(String json);
          void doMyComment(RequestBody requestBody);

    }

}
