package com.example.taopiao.mvp.contract;

import com.example.taopiao.base.BasePresenter;
import com.example.taopiao.base.BaseView;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface DoAvatarContract {
    interface view extends BaseView<CinemaContract.presenter> {
            void getMessage(String message);
    }
    interface presenter extends BasePresenter {
        void cummitAvatar(MultipartBody.Part mfile);
    }
}
