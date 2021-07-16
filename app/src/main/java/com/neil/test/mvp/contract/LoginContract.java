package com.neil.test.mvp.contract;

import com.neil.test.mvp.base.BasePresent;
import com.neil.test.mvp.base.BaseView;
import com.neil.test.mvp.bean.BaseObjectBean;
import com.neil.test.mvp.bean.LoginBean;

import io.reactivex.rxjava3.core.Observable;

/**
 * @author zhongnan1
 * @time 2021/7/16 14:19
 *
 */
public interface LoginContract {
    interface Model {
        Observable<BaseObjectBean<LoginBean>> login(String username, String password);
    }

    interface View extends BaseView {
        void onSuccess(BaseObjectBean<LoginBean> bean);
    }

    interface Present {
        void login(String username, String password);
    }
}
