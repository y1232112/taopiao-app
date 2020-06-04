package com.example.taopiao.mvp.contract;

import com.example.taopiao.base.BasePresenter;
import com.example.taopiao.base.BaseView;

import java.util.List;
import java.util.Map;

public interface MainContract {
    interface view extends BaseView<presenter>{
        void setContent(String content);//设置内容
        void setWillFilms(List<Map<String,String>> maps);
    }
    interface presenter extends BasePresenter{
        void getHotFilms(String city);//获得热映电影信息

        void getWishInfo(String user_id);//获取wish表中用户对应的信息

    }
}
