package com.example.wuziqi.retrofit;


import com.example.wuziqi.bean.request.UserRequest;
import com.example.wuziqi.bean.response.UserResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitService {
    //登录
    @POST("/user/login")
    Observable<UserResponse> userLogin(@Body UserRequest request);

    //注册
    @POST("/user/register")
    Observable<UserResponse> userRegister(@Body UserRequest request);

    //注册验证码
    @POST("/user/sendCheckCode")
    Observable<UserResponse> sendCheckCode(@Body UserRequest request);

    //重置密码验证码
    @POST("/user/verifyChange")
    Observable<UserResponse> verifyChange(@Body UserRequest request);

    //修改密码
    @POST("/user/changePassword")
    Observable<UserResponse> changePassword(@Body UserRequest request);



}
