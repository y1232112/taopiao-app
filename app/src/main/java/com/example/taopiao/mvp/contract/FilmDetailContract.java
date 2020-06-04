package com.example.taopiao.mvp.contract;

import com.example.taopiao.base.BasePresenter;
import com.example.taopiao.base.BaseView;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

public interface FilmDetailContract {
    interface view extends BaseView<MainContract.presenter> {
        void setFilm(List<Map<String,String>> maps);
        void setRoles(List<Map<String,String>> maps);
        void setComment(List<Map<String,String>> maps);
        void setUserFilm(List<Map<String,String>> maps);
        void showDoLooked(String message);
        void showDoWish(String message);
    }
    interface presenter extends BasePresenter{
        void getFilm(int film_id);//获得影片信息
        void getRoles(int film_id);//根据电影获取影院列表信息
        void getComment(String json);//获取电影详情页的评论信息
        void getUserFilm(Integer film_id,Integer user_id);
        void doLooked(RequestBody requestBody);
        void doWish(RequestBody requestBody);
    }
}
