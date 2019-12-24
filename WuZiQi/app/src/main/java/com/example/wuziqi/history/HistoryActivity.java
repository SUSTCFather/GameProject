package com.example.wuziqi.history;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.wuziqi.util.Constant;
import com.example.wuziqi.R;
import com.example.wuziqi.bean.GameRecord;
import com.example.wuziqi.bean.response.HistoryResponse;

import java.util.List;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener, HistoryContract.HistoryView {

    private static final String TAG = "HistoryActivity";

    private long userId;

    private HistoryPresenterIml presenter;

    private HistoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        presenter = new HistoryPresenterIml();
        presenter.attachView(this);
        userId = getIntent().getLongExtra(Constant.USER_ID,0);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.getHistory(userId);
    }

    private void initView() {
        //topbar
        findViewById(R.id.go_txt).setVisibility(View.GONE);
        TextView titleTxt = findViewById(R.id.title_txt);
        titleTxt.setText("历史战绩");
        findViewById(R.id.back_btn).setOnClickListener(this);
        //历史
        RecyclerView recyclerView = findViewById(R.id.history);
        mAdapter = new HistoryAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void showHistory(HistoryResponse response) {
        if(response.getStatus() == Constant.SUCCESS) {
            List<GameRecord> list = response.getData();
            mAdapter.refreshData(list);
        }
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
