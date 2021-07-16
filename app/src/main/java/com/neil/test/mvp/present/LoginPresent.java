package com.neil.test.mvp.present;

import android.util.Log;

import com.neil.test.mvp.base.BasePresent;
import com.neil.test.mvp.bean.BaseObjectBean;
import com.neil.test.mvp.bean.LoginBean;
import com.neil.test.mvp.contract.LoginContract;
import com.neil.test.mvp.http.RxScheduler;
import com.neil.test.mvp.model.LoginModel;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @author zhongnan1
 * @time 2021/7/16 15:37
 *
 */
public class LoginPresent extends BasePresent<LoginContract.View> implements LoginContract.Present {
    protected final static String TAG = "LoginPresent";
    protected LoginContract.Model loginModel;

    public LoginPresent() {
        loginModel = new LoginModel();
    }

    @Override
    public void login(String username, String password) {
        if(!isViewAttached()) {
            Log.e(TAG, "login: view is not attached!!");
            return;
        }
        Log.d(TAG, "login : ");
        loginModel.login(username, password)
                .compose(RxScheduler.obsIoMain())
                .to(mView.bindAutoDispose())
                .subscribe(new Observer<BaseObjectBean<LoginBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(@NonNull BaseObjectBean<LoginBean> loginBeanBaseObjectBean) {
                        mView.onSuccess(loginBeanBaseObjectBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.onError(e.getMessage());
                        mView.hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });
    }
}
