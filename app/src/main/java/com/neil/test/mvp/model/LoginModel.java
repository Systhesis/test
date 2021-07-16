package com.neil.test.mvp.model;

import com.neil.test.mvp.bean.BaseObjectBean;
import com.neil.test.mvp.bean.LoginBean;
import com.neil.test.mvp.contract.LoginContract;
import com.neil.test.mvp.http.RetrofitClient;

import io.reactivex.rxjava3.core.Observable;

/**
 * @author zhongnan1
 * @time 2021/7/16 15:35
 *
 */
public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<BaseObjectBean<LoginBean>> login(String username, String password) {
        return RetrofitClient.getInstance().getApi().login(username, password);
    }
}
