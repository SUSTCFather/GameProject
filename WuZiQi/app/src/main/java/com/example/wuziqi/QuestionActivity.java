package com.example.wuziqi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wuziqi.view.AnswerItemView;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    private TextView mQuestionText;

    private TextView mSubmitBtn;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        initView();
    }

    private void initView() {
        mSubmitBtn = findViewById(R.id.submit_btn);
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray checkedArray = mListView.getCheckedItemPositions();
                for (int i = 0; i < checkedArray.size(); i++) {
                    if (checkedArray.valueAt(i)){
                        Log.e("fuck",checkedArray.keyAt(i)+" "+checkedArray.valueAt(i));
                    }
                }
                Toast.makeText(QuestionActivity.this,"find nothing",Toast.LENGTH_LONG).show();
            }
        });

        mListView = findViewById(R.id.list_view);

        //构造数据
        List<AnswerChoice> dataList = new ArrayList<>();
        dataList.add(new AnswerChoice("A","test1"));
        dataList.add(new AnswerChoice("B","test2"));
        dataList.add(new AnswerChoice("C","test3"));
        dataList.add(new AnswerChoice("D","test4"));

        //构造Adapter
        AnswerItemAdapter adapter = new AnswerItemAdapter(QuestionActivity.this, dataList);
        // 头部view：设置view的点击时间，用于屏蔽listview的item的点击事件
        View headerView = getLayoutInflater().inflate(R.layout.header_view, null);
        mQuestionText = headerView.findViewById(R.id.question_text);
        mQuestionText.setText("代码......以下哪个测试可以杀死这个变异体？");

        headerView.setOnClickListener(null);

        mListView.addHeaderView(headerView);
        mListView.setAdapter(adapter);

    }

}
