package com.example.taopiao.mvp.contract;

import com.example.taopiao.base.BasePresenter;
import com.example.taopiao.base.BaseView;

/**
 * 建立契约，契约接口，为mvp建立契约
 */
public interface RegistContract {
    /**
     * 视图接口,设置内容
     */
    interface view  extends BaseView<presenter> {
        void setContent(String content);//设置内容
    }

    /**
     * 代理，中介
     */
    interface presenter extends BasePresenter{

        void userRegist(String username,String password,String sex,String phone);
    }
}
