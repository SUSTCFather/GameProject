package com.example.wuziqi.question;

import android.util.Log;

import com.example.wuziqi.bean.response.QuestionResponse;
import com.example.wuziqi.retrofit.HttpHandler;
import com.example.wuziqi.retrofit.manager.QuestionDataManager;

public class QuestionPresenterIml implements QuestionContract.QuestionPresenter{

    private QuestionContract.QuestionView mView;

    private QuestionDataManager questionDataManager;

    public QuestionPresenterIml() {
        this.questionDataManager = new QuestionDataManager(new QuestionHandler());
    }

    public void attachView(QuestionContract.QuestionView view) {
        this.mView = view;
    }

    public void detachView() {
        mView = null;
    }

    @Override
    public void doGetQuestion() {
        questionDataManager.getQuestion();
    }

    class QuestionHandler implements HttpHandler<QuestionResponse> {
        @Override
        public void onResultSuccess(QuestionResponse response) {
            if(mView != null) {
                mView.showQuestion(response);
            }
        }

        @Override
        public void onResultError(Throwable e) {
            Log.e("fuck",e.toString());
        }
    }

}
