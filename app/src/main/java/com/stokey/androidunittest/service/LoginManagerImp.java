package com.stokey.androidunittest.service;

import android.support.annotation.NonNull;
import com.stokey.androidunittest.BuildConfig;
import com.stokey.androidunittest.interceptor.LoginInterceptor;
import com.stokey.androidunittest.model.User;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author stokey
 * @date 2018/6/17
 */

public class LoginManagerImp {
    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofitClient;
    private LoginService mService;

    private static final String BASE_URL = "http://127.0.0.1";

    private LoginManagerImp() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder mOkHttpClientBuilder = new OkHttpClient
                .Builder();
        if (BuildConfig.DEBUG) {
            mOkHttpClientBuilder.addInterceptor(httpLoggingInterceptor);
        }

        mOkHttpClient = mOkHttpClientBuilder.addInterceptor(new LoginInterceptor()).build();
        Retrofit.Builder mRetrofitBuilder = new Retrofit.Builder()
                // 处理Service返回类型
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //处理HTTP Result Convert
                .addConverterFactory(GsonConverterFactory.create())
                // TODO 未处理BaseURL
                .baseUrl(BASE_URL)
                .client(mOkHttpClient);
        mRetrofitClient = mRetrofitBuilder.build();
        mService = mRetrofitClient.create(LoginService.class);
    }

    private static class LazyHolder {
        private static LoginManagerImp mInstance = new LoginManagerImp();
    }

    public static LoginManagerImp getInstance() {
        return LazyHolder.mInstance;
    }
    /**
     * 登录
     *
     * @param name
     * @param psw
     * @return
     */
    public Observable<User> login(@NonNull final String name, @NonNull final String psw) {
        return mService.login(name, psw).subscribeOn(Schedulers.io());
    }

    /**
     * 更新Token
     *
     * @return
     */
    public Observable<String> updateToken() {
        return mService.updateToke().subscribeOn(Schedulers.io());
    }
}
