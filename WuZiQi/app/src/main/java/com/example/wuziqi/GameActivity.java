package com.example.wuziqi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wuziqi.question.QuestionActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * 五子棋游戏
 * 参考鸿洋大神慕课视频：http://www.imooc.com/learn/641
 * -刘某人程序员
 */
public class GameActivity extends AppCompatActivity {

    //重来按钮
    private FloatingActionButton fab;
    //游戏
    private GameView game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        game = (GameView) findViewById(R.id.mGameView);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                game.RestartGame();
                Intent intent = new Intent(GameActivity.this, QuestionActivity.class);
                startActivity(intent);
            }
        });
    }
}