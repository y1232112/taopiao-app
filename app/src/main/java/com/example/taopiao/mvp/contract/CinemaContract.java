package com.example.taopiao.mvp.contract;

import com.example.taopiao.base.BasePresenter;
import com.example.taopiao.base.BaseView;

import java.util.List;
import java.util.Map;

public interface CinemaContract {
    interface view extends BaseView<presenter> {
        void setContent(String content);//设置内容
        void setCinema(List<Map<String,String>> maps);
    }
    interface presenter extends BasePresenter {
         void getCinemaInfo(Integer cinema_id);
    }

}
