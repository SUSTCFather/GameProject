package com.example.wuziqi.retrofit.manager;

import com.example.wuziqi.bean.request.QuestionRequest;
import com.example.wuziqi.bean.response.QuestionResponse;
import com.example.wuziqi.retrofit.HttpHandler;

import io.reactivex.Observable;

public class QuestionDataManager extends BaseDataManager<QuestionRequest, QuestionResponse>{

    public QuestionDataManager(HttpHandler<QuestionResponse> handler) {
        super(handler);
    }

    public void getQuestion(QuestionRequest request) {
        Observable<QuestionResponse> observable = retrofitService.getQuestion(request);
        initObservable(observable);
    }



}
