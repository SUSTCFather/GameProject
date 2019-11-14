package com.example.wuziqi.question;

import com.example.wuziqi.bean.request.QuestionRequest;
import com.example.wuziqi.bean.response.QuestionResponse;

public interface QuestionContract {

    interface QuestionView{

        void showQuestion(QuestionResponse response);

    }

    interface QuestionPresenter{

        void doGetQuestion(QuestionRequest request);

    }
}
