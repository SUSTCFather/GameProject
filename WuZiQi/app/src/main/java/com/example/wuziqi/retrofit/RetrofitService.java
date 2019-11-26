package com.example.wuziqi.retrofit;


import com.example.wuziqi.bean.request.FriendRequest;
import com.example.wuziqi.bean.request.InviteRequest;
import com.example.wuziqi.bean.request.QuestionRequest;
import com.example.wuziqi.bean.request.UserRequest;
import com.example.wuziqi.bean.response.FriendResponse;
import com.example.wuziqi.bean.response.InviteResponse;
import com.example.wuziqi.bean.response.QuestionResponse;
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

    //修改密码
    @POST("/user/addFriend")
    Observable<FriendResponse> addFriend(@Body FriendRequest request);

    //修改密码
    @POST("/user/getFriends")
    Observable<FriendResponse> getFriends(@Body FriendRequest request);

    //获取题目
    @POST("/question/get")
    Observable<QuestionResponse> getQuestion(@Body QuestionRequest request);

    //邀请好友
    @POST("/game/invite")
    Observable<InviteResponse> inviteFriend(@Body InviteRequest request);



}
