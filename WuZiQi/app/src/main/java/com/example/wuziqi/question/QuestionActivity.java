package com.example.wuziqi.question;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.wuziqi.AnswerChoice;
import com.example.wuziqi.AnswerItemAdapter;
import com.example.wuziqi.Constant;
import com.example.wuziqi.R;
import com.example.wuziqi.bean.request.QuestionRequest;
import com.example.wuziqi.bean.response.QuestionResponse;
import com.example.wuziqi.view.QuestionView;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity implements QuestionContract.QuestionView, View.OnClickListener {

    private QuestionPresenterIml presenter;

    private QuestionView questionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        presenter = new QuestionPresenterIml();
        presenter.attachView(this);
        getQuestion();
        initView();
    }

    private void getQuestion() {
        QuestionRequest request = new QuestionRequest();
        request.setQuestionId(3);
        presenter.doGetQuestion(request);
    }

    @Override
    public void showQuestion(QuestionResponse response) {
        if(response.getStatus() == Constant.SUCCESS) {
            questionView.refreshView(response.getData());
        }else {
            Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_btn:
                questionView.checkAnswer();
                break;
        }
    }

    private void initView() {
        TextView submitBtn = findViewById(R.id.submit_btn);
        LinearLayout container = findViewById(R.id.question_container);
        questionView = new QuestionView(this);
        submitBtn.setOnClickListener(this);
        container.addView(questionView.getView());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

}
