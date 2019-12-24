package com.example.wuziqi.question;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wuziqi.util.Constant;
import com.example.wuziqi.R;
import com.example.wuziqi.bean.response.QuestionResponse;
import com.example.wuziqi.dialog.ExitDialog;
import com.example.wuziqi.view.QuestionView;

public class QuestionActivity extends AppCompatActivity implements QuestionContract.QuestionView, View.OnClickListener {

    private QuestionPresenterIml presenter;

    private QuestionView questionView;

    private ExitDialog exitDialog;

    private ExitDialog submitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        presenter = new QuestionPresenterIml();
        presenter.attachView(this);
        getQuestion();
        initView();
    }

    private void initExitDialog() {
        exitDialog = new ExitDialog(this, "直接退出将视作做错题处理，您确定退出？", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.commit_btn) {
                    exitDialog.dismiss();
                    Intent intent = new Intent();
                    intent.putExtra(Constant.ANSWER_CODE, QuestionView.FAIL);
                    setResult(RESULT_OK, intent);
                    QuestionActivity.this.finish();
                }else {
                    exitDialog.dismiss();
                }
            }
        });
    }

    private void initSubmitDialog() {
        submitDialog = new ExitDialog(this, "您确定提交题目？\n（答对获得一次下棋机会，答错惩罚15秒）", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.commit_btn) {
                    submitDialog.dismiss();
                    doCheckAnswer();
                }else {
                    submitDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        exitDialog.show();
    }

    private void getQuestion() {
        presenter.doGetQuestion();
    }

    @Override
    public void showQuestion(QuestionResponse response) {
        if(response.getStatus() == Constant.SUCCESS) {
            questionView.refreshView(response.getData());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_btn:
                submitDialog.show();
                break;
        }
    }

    private void doCheckAnswer() {
        int result = questionView.checkAnswer();
        if(result == QuestionView.INVALID) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(Constant.ANSWER_CODE,result);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initView() {
        TextView submitBtn = findViewById(R.id.submit_btn);
        LinearLayout container = findViewById(R.id.question_container);
        questionView = new QuestionView(this);
        submitBtn.setOnClickListener(this);
        container.addView(questionView.getView());
        //topbar
        findViewById(R.id.back_btn).setVisibility(View.GONE);
        findViewById(R.id.go_txt).setVisibility(View.GONE);
        TextView title = findViewById(R.id.title_txt);
        title.setText("题目");
        //dialog
        initExitDialog();
        initSubmitDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

}
