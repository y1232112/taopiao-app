package com.example.taopiao.mvp.contract;

import com.example.taopiao.base.BasePresenter;
import com.example.taopiao.base.BaseView;

public interface LoginContract {

    interface view extends BaseView<presenter>{
        void setContent(String content);//设置内容
    }
    interface presenter extends BasePresenter{
        void userLogin(String nickname,String password);
    }
}
