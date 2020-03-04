package com.example.taopiao.base;

import com.uber.autodispose.AutoDisposeConverter;

/**
 * 定义一个的视图基类，
 * @param <T>
 */
public  interface BaseView<T> {
//    /**
//     * 显示加载中
//     */
//    void showLoading();
//
//    /**
//     * 隐藏加载
//     */
//    void hideLoading();
//
//    /**
//     * 数据获取失败
//     * @param throwable
//     */
//    void onError(Throwable throwable);

    /**
     * 绑定Android生命周期 防止RxJava内存泄漏
     *
     * @param <T>
     * @return
     */
    <T> AutoDisposeConverter<T> bindAutoDispose();

}
