package com.example.wuziqi.retrofit;


import com.example.wuziqi.bean.request.EnterRequest;
import com.example.wuziqi.bean.request.PointRequest;
import com.example.wuziqi.bean.request.UserRequest;
import com.example.wuziqi.bean.response.EnterResponse;
import com.example.wuziqi.bean.response.RankResponse;
import com.example.wuziqi.bean.response.HallResponse;
import com.example.wuziqi.bean.response.HistoryResponse;
import com.example.wuziqi.bean.response.QuestionResponse;
import com.example.wuziqi.bean.response.UserResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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
    @GET("/user/rankList")
    Observable<RankResponse> getRankList();

    //查询个人信息
    @GET("/user/personal")
    Observable<UserResponse> getPersonalInfo(@Query("userId") long userId);

    //下棋
    @GET("/user/history")
    Observable<HistoryResponse> getHistory(@Query("userId") long userId);

    //获取题目
    @GET("/question/get")
    Observable<QuestionResponse> getQuestion();

    //获取最新大厅
    @GET("/game/getHallList")
    Observable<HallResponse> getHallList();

    //进入大厅
    @POST("/game/enter")
    Observable<EnterResponse> enterHall(@Body EnterRequest request);

    //离开大厅
    @POST("/game/exit")
    Observable<EnterResponse> exitHall(@Body EnterRequest request);

    //离开大厅
    @POST("/game/ready")
    Observable<EnterResponse> ready(@Body EnterRequest request);

    //获取单个大厅信息
    @GET("/game/getHall")
    Observable<HallResponse> getHall(@Query("hallId") int hallId);

    //下棋
    @POST("/game/chess")
    Observable<EnterResponse> putChess(@Body PointRequest request);


}
