package com.stokey.androidunittest.interceptor;

import java.io.IOException;
import java.util.UUID;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by stokey on 2018/6/17.
 */

public class LoginInterceptor implements Interceptor {
    private static final String HEADER_KEY_SIGN = "sign";
    private static final String HEADER_KEY_ID = "Request-Id";
    private static final String HEADER_KEY_TIME = "Request-TimeStamp";


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        String requestId = UUID.randomUUID().toString();
        String requestTime = String.valueOf(System.currentTimeMillis());
        // TODO 签名规则未实现
        String sign = "xxxxxx";
        Request newRequest = request.newBuilder()
                .addHeader(HEADER_KEY_ID, requestId)
                .addHeader(HEADER_KEY_TIME, requestTime)
                .addHeader(HEADER_KEY_SIGN, sign)
                .build();
        return chain.proceed(newRequest);
    }
}
