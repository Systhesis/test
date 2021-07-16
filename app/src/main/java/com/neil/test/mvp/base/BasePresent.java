package com.neil.test.mvp.base;
/**
 * @author zhongnan1
 * @time 2021/7/16 14:32
 *
 */
public abstract class BasePresent<V extends BaseView> {
    protected V mView;

    public void attachView(V view) {
        this.mView = view;
    }

    public void detachView() {
        this.mView = null;
    }

    public boolean isViewAttached() {
        return mView != null;
    }


}
