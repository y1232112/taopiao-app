package com.example.taopiao.mvp.contract;

import com.example.taopiao.base.BasePresenter;
import com.example.taopiao.base.BaseView;

import java.util.List;
import java.util.Map;

public interface BuyAboutFilmContract {

    interface view extends BaseView<CinemaContract.presenter> {
          void showContent(List<Map<String,String>> maps);
    }
    interface presenter extends BasePresenter {
          void buyAboutFilm(Integer id);

    }
}
