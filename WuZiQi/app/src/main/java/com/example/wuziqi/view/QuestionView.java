package com.example.wuziqi.view;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wuziqi.AnswerItemAdapter;
import com.example.wuziqi.R;
import com.example.wuziqi.bean.Answer;
import com.example.wuziqi.bean.Question;

import java.util.List;

public class QuestionView {

    private static final int CORRECT = 1;

    private Context mContext;

    private View mView;

    private TextView mQuestionText;

    private ListView mListView;

    private AnswerItemAdapter mAdapter;

    private int correctId;

    public QuestionView(Context context) {
        this.mContext = context;
        initView();
    }

    private void initView() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.question_view,null);
        View headerView = LayoutInflater.from(mContext).inflate(R.layout.header_view, null);
        headerView.setOnClickListener(null);
        mListView = mView.findViewById(R.id.list_view);
        mQuestionText = headerView.findViewById(R.id.question_text);
        mAdapter = new AnswerItemAdapter(mContext);
        mListView.addHeaderView(headerView);
        mListView.setAdapter(mAdapter);

        // 头部view：设置view的点击时间，用于屏蔽listview的item的点击事件
    }

    public boolean checkAnswer() {
        SparseBooleanArray checkedArray = mListView.getCheckedItemPositions();
        if(checkedArray.size() == 0) {
            Toast.makeText(mContext, "请先选择一个选项", Toast.LENGTH_SHORT).show();
            return false;
        }
        int chooseId = 0;
        for (int i = 0; i < checkedArray.size(); i++) {
            if (checkedArray.valueAt(i)){
                chooseId = checkedArray.keyAt(i);
            }
        }
        if(chooseId == correctId) {
            Toast.makeText(mContext, "答案正确", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, "答案错误", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void refreshView(Question question) {
        updateCorrectId(question.getAnswers());
        mQuestionText.setText(question.getContent());
        mAdapter.refreshData(question.getAnswers());
    }

    private void updateCorrectId(List<Answer> answers) {
        for(int i = 0;i<answers.size();i++) {
            if(answers.get(i).getType() == CORRECT) {
                correctId = i+1;
                break;
            }
        }
    }

    public View getView() {
        return mView;
    }
}
