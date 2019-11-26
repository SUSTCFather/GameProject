package com.example.wuziqi.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.example.wuziqi.Constant;
import com.example.wuziqi.R;
import com.example.wuziqi.SharedUtil;
import com.example.wuziqi.bean.User;
import com.example.wuziqi.question.QuestionActivity;
import com.example.wuziqi.view.GameView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class GameActivity extends AppCompatActivity implements View.OnClickListener, GameContract.GameView {

    private static final String TAG = "GameActivity";

    private GameView game;

    private GamePresenterIml presenter;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        presenter = new GamePresenterIml();
        presenter.attachView(this);
        initView();
        String userData = SharedUtil.getInstance(this).readShared(Constant.USER_DATA,null);
        if(userData != null) {
            user = JSON.parseObject(userData,User.class);
            presenter.openConnect(user.getUserId()+"");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initView() {
        game = findViewById(R.id.mGameView);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                Intent intent = new Intent(GameActivity.this, QuestionActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.closeConnect();
        presenter.detachView();
    }

    @Override
    public void showMessage(String message) {
        Log.e(TAG,message);

    }
}