package com.example.taopiao.mvp.contract;

import com.example.taopiao.base.BasePresenter;
import com.example.taopiao.base.BaseView;

import java.util.List;
import java.util.Map;

public interface CinemaDetailContract {
    interface view extends BaseView<CinemaContract.presenter> {
        void setContent(List<Map<String,String>> maps);
        void setServe(List<Map<String,String>> maps);
    }
    interface presenter extends BasePresenter {
        void getCinemaDetail(Integer cinema_id);
        void getCienmaServe(Integer cinema_id);
    }

}
