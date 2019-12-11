package com.example.wuziqi.retrofit.manager;

import com.example.wuziqi.bean.response.QuestionResponse;
import com.example.wuziqi.retrofit.HttpHandler;

import io.reactivex.Observable;

public class QuestionDataManager extends BaseDataManager<QuestionResponse>{

    public QuestionDataManager(HttpHandler<QuestionResponse> handler) {
        super(handler);
    }

    public void getQuestion() {
        Observable<QuestionResponse> observable = retrofitService.getQuestion();
        initObservable(observable);
    }



}
