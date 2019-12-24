package com.example.wuziqi.rank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.wuziqi.R;
import com.example.wuziqi.bean.response.RankResponse;

public class RankActivity extends AppCompatActivity implements RankContract.RankView, View.OnClickListener {

    private RankPresenterIml presenter;

    private RankAdapter mRankAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        presenter = new RankPresenterIml();
        presenter.attachView(this);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.getRankList();
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    public void showRankList(RankResponse response) {
        mRankAdapter.refreshData(response.getData());
    }

    private void initView() {
        TextView titleTxt = findViewById(R.id.title_txt);
        findViewById(R.id.go_txt).setVisibility(View.GONE);
        findViewById(R.id.back_btn).setOnClickListener(this);
        titleTxt.setText("排行榜");
        RecyclerView recyclerView = findViewById(R.id.rank_view);
        //recyclerView
        mRankAdapter = new RankAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mRankAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
