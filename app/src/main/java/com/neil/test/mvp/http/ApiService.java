package com.neil.test.mvp.http;

import com.neil.test.mvp.bean.BaseObjectBean;
import com.neil.test.mvp.bean.LoginBean;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author zhongnan1
 * @time 2021/7/16 15:13
 *
 */
public interface ApiService {
    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseObjectBean<LoginBean>> login(
            @Field("username") String username,
            @Field("password") String password);
}
