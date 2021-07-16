package com.neil.test.mvp.base;

import autodispose2.AutoDisposeConverter;

/**
 * @author zhongnan1
 * @time 2021/7/16 14:26
 *
 */
public interface BaseView {
    /**
     * 显示加载中
     */
    void showLoading();

    /**
     * 关闭加载中
     */
    void hideLoading();

    /**
     * 获取数据失败
     * @param errorMsg
     */
    void onError(String errorMsg);

    /**
     * 绑定android生命周期
     * @param <T>
     * @return
     */
    <T> AutoDisposeConverter<T> bindAutoDispose();
}
