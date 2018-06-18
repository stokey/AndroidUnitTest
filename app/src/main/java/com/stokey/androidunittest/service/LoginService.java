package com.stokey.androidunittest.service;

import com.stokey.androidunittest.model.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
/**
 *
 * @author stokey
 * @date 2018/6/17
 */

public interface LoginService {
    @FormUrlEncoded
    @POST("/login")
    Observable<User> login(
            @Field("name") String name,
            @Field("psw  ") String psw);

    @FormUrlEncoded
    @POST("/updateToke")
    Observable<String> updateToke();
}
