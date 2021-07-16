package com.neil.test.mvp.http;

import com.neil.test.BuildConfig;

import java.io.IOException;

import io.reactivex.rxjava3.annotations.NonNull;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author zhongnan1
 * @time 2021/7/16 15:16
 *
 */
public class RetrofitClient {
    private static volatile RetrofitClient INSTANCE = null;
    private ApiService apiService;
    private final static String BASE_URL = "https://www.wanandroid.com";
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;

    private RetrofitClient() {}
    public static RetrofitClient getInstance() {
        if(INSTANCE == null) {
            synchronized (RetrofitClient.class) {
                if(INSTANCE == null) {
                    INSTANCE = new RetrofitClient();
                }

            }
        }
        return INSTANCE;
    }


    /**
     * 设置Header
     *
     * @return
     */
    private Interceptor getHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder();
                //添加Token
//                        .header("token", "");
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };

    }

    /**
     * 设置拦截器 打印日志
     *
     * @return
     */
    private Interceptor getInterceptor() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //显示日志
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return interceptor;
    }

    public OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            //如果为DEBUG 就打印日志
            if (BuildConfig.DEBUG) {
                okHttpClient = new OkHttpClient().newBuilder()
                        //设置Header
                        .addInterceptor(getHeaderInterceptor())
                        //设置拦截器
                        .addInterceptor(getInterceptor())
                        .build();
            } else {
                okHttpClient = new OkHttpClient().newBuilder()
                        //设置Header
                        .addInterceptor(getHeaderInterceptor())
                        .build();
            }

        }

        return okHttpClient;
    }

    public ApiService getApi() {
        //初始化一个client,不然retrofit会自己默认添加一个
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    //设置网络请求的Url地址
                    .baseUrl(BASE_URL)
                    //设置数据解析器
                    .addConverterFactory(GsonConverterFactory.create())
                    //设置网络请求适配器，使其支持RxJava与RxAndroid
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build();
        }
        //创建—— 网络请求接口—— 实例
        if (apiService==null){
            apiService = retrofit.create(ApiService.class);
        }

        return apiService;
    }


}
