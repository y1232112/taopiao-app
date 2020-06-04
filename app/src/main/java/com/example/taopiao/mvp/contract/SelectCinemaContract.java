package com.example.taopiao.mvp.contract;

import com.example.taopiao.base.BasePresenter;
import com.example.taopiao.base.BaseView;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

public interface SelectCinemaContract {

    interface view extends BaseView<MainContract.presenter> {
     void showCinemas(List<Map<String,String>> maps);
    }
    interface presenter extends BasePresenter {

    void getCinemasHasSchedule(RequestBody requestBody);
    }

}
