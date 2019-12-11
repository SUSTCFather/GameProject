package com.example.wuziqi.game;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.wuziqi.Constant;
import com.example.wuziqi.MyApplication;
import com.example.wuziqi.R;
import com.example.wuziqi.SharedUtil;
import com.example.wuziqi.bean.GameData;
import com.example.wuziqi.bean.GamePlayer;
import com.example.wuziqi.bean.Hall;
import com.example.wuziqi.bean.User;
import com.example.wuziqi.bean.request.EnterRequest;
import com.example.wuziqi.bean.request.PointRequest;
import com.example.wuziqi.bean.response.EnterResponse;
import com.example.wuziqi.bean.response.GameDataResponse;
import com.example.wuziqi.bean.response.HallResponse;
import com.example.wuziqi.bean.response.HttpResult;
import com.example.wuziqi.dialog.ExitDialog;
import com.example.wuziqi.main.MainActivity;
import com.example.wuziqi.question.QuestionActivity;
import com.example.wuziqi.register.TimeCount;
import com.example.wuziqi.view.GameView;
import com.example.wuziqi.view.QuestionView;
import com.example.wuziqi.view.listener.OnGameViewListener;
import com.example.wuziqi.websocket.GameWebSocketClient;
import com.example.wuziqi.websocket.OnMessageHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class GameActivity extends AppCompatActivity implements View.OnClickListener, GameContract.GameView, OnMessageHandler, OnGameViewListener {

    private static final String TAG = "GameActivity";

    private static final int TO_QUESTION = 1;

    private static final int PUNISH_TIME = 15;

    private GameView game;

    private GamePresenterIml presenter;

    private EnterRequest nowUser;

    private ExitDialog exitDialog;

    private ExitDialog winnerDialog;

    private ExitDialog questionDialog;

    private View whiteUser;

    private View blackUser;

    private Button questionBtn;

    private Button readyBtn;

    private TextView counter;

    private TextView hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        presenter = new GamePresenterIml();
        presenter.attachView(this);
        initView();
        initData();
    }

    private void initView() {
        game = findViewById(R.id.mGameView);
        game.setListener(this);
        whiteUser = findViewById(R.id.white_user);
        blackUser = findViewById(R.id.black_user);
        counter = findViewById(R.id.counter);
        hint = findViewById(R.id.hint);
        hint.setText("点击准备，准备开始");
        findViewById(R.id.back_btn).setVisibility(View.GONE);
        findViewById(R.id.go_txt).setVisibility(View.GONE);
        findViewById(R.id.exit_btn).setOnClickListener(this);
        questionBtn = findViewById(R.id.question_btn);
        questionBtn.setEnabled(false);
        readyBtn = findViewById(R.id.ready_btn);
        questionBtn.setOnClickListener(this);
        readyBtn.setOnClickListener(this);
        //dialog
        initExitDialog();
        initQuestionDialog();
        initWinnerDialog();

    }

    private void initData() {
        String s = getIntent().getStringExtra(Constant.ENTER_DATA);
        if(s != null) {
            nowUser = JSON.parseObject(s, EnterRequest.class);
            TextView titleTxt = findViewById(R.id.title_txt);
            titleTxt.setText(String.format("%d号厅",nowUser.getHallId()));
        }
    }

    private void initExitDialog() {
        exitDialog = new ExitDialog(this, "你确定退出游戏？", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.commit_btn) {
                    exitDialog.dismiss();
                    presenter.doExit(nowUser);
                }else {
                    exitDialog.dismiss();
                }
            }
        });
    }

    private void initWinnerDialog() {
        winnerDialog = new ExitDialog(this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                winnerDialog.dismiss();
            }
        });
    }

    private void initQuestionDialog() {
        questionDialog = new ExitDialog(this, "您还有一次下棋机会，您确定要放弃它去做题吗？", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.commit_btn) {
                    questionDialog.dismiss();
                    game.setCanClick(false);
                    toQuestionActivity();
                }else {
                    questionDialog.dismiss();
                }
            }
        });
    }

    /**
     * 棋盘点击回调
     * @param point
     */
    @Override
    public void onPointClick(Point point) {
        PointRequest request = new PointRequest(nowUser,point);
        boolean haveSend = MyApplication.sendMessage(JSON.toJSONString(request));
        //发送成功不给发了
        if(haveSend) {
            hint.setText("先通过做题来获得下棋机会");
            game.setCanClick(false);
        }
    }

    /**
     * 房间内用户展示
     * @param response
     */
    @Override
    public void showHallInfo(HallResponse response) {
        if(response.getStatus() == Constant.SUCCESS) {
            Hall hall = response.getData().get(0);
            initUserView(whiteUser, hall.getWhitePlayer());
            initUserView(blackUser, hall.getBlackPlayer());
        }
    }

    /**
     * 退出回调
     * @param response
     */
    @Override
    public void showExit(EnterResponse response) {
        if(response.getStatus() == Constant.SUCCESS) {
            finish();
        }
    }

    /**
     * 准备回调
     * @param response
     */
    @Override
    public void showReady(EnterResponse response) {
        if(response.getStatus() == Constant.SUCCESS) {
            readyBtn.setText("已准备");
            readyBtn.setEnabled(false);
            game.setVisibility(View.GONE);
            game.setGameOver(false);
            counter.setVisibility(View.VISIBLE);
        }
    }

    private void initUserView(View view, GamePlayer gamePlayer) {
        ImageView head = view.findViewById(R.id.game_head);
        TextView name = view.findViewById(R.id.game_name);
        ImageView hand = view.findViewById(R.id.hand);
        if(gamePlayer != null) {
            head.setImageResource(R.drawable.ic_head);
            name.setText(gamePlayer.getUserName());
            if(gamePlayer.getType() == Constant.READY) {
                hand.setVisibility(View.VISIBLE);
            }else {
                hand.setVisibility(View.INVISIBLE);
            }
        }else {
            head.setImageResource(R.drawable.ic_mine_normal);
            name.setText("");
            hand.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onResume() {
        Log.e(TAG,"onResume");
        super.onResume();
        MyApplication.openConnect();
        MyApplication.setMessageHandler(this);
        presenter.getHallInfo(nowUser.getHallId());
    }

    /**
     * WebSocket信息回调
     * @param message
     */
    @Override
    public void onMessage(String message) {
        Log.e(TAG,message);
        HttpResult result = JSON.parseObject(message,HttpResult.class);
        if(result.getMessage().equals("HallList")) {
            HallResponse responseHall = JSON.parseObject(message,HallResponse.class);
            List<Hall> halls = responseHall.getData();
            Hall hall = halls.get(nowUser.getHallId()-1);
            initUserView(whiteUser, hall.getWhitePlayer());
            initUserView(blackUser, hall.getBlackPlayer());
        } else if(result.getMessage().equals("GameData")) {
            startFirstTimer();
            GameDataResponse dataResponse = JSON.parseObject(message,GameDataResponse.class);
            game.refreshView(dataResponse.getData());
            showWinner(dataResponse.getData());
        }
    }

    private void showWinner(GameData data) {
        int winner = data.getWinner();
        if(winner == -1) {
            return;
        }
        if(winner == Constant.WHITE) {
            winnerDialog.setText(String.format("%s获得胜利",data.getWhitePlayer().getUserName()));
        }else {
            winnerDialog.setText(String.format("%s获得胜利",data.getBlackPlayer().getUserName()));
        }
        winnerDialog.show();
        game.setGameOver(true);
        resetGame();
    }

    private void resetGame() {
        questionBtn.setEnabled(false);
        readyBtn.setEnabled(true);
        readyBtn.setText("准备");
        hint.setText("点击准备，准备开始");
        whiteUser.findViewById(R.id.hand).setVisibility(View.INVISIBLE);
        blackUser.findViewById(R.id.hand).setVisibility(View.INVISIBLE);
    }

    private void startFirstTimer() {
        Log.i(TAG,"startFirstTimer "+game.isContainData());
        if(!game.isContainData()) {
            hint.setText("先通过做题来获得下棋机会");
            GameCounter timeCount = new GameCounter(6 * 1000, 1000);
            timeCount.start();
        }
    }

    @Override
    public void onClose(final String reason) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(GameActivity.this, "socket关闭: "+reason, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        exitDialog.show();
    }

    private void toQuestionActivity() {
        Intent intent = new Intent(GameActivity.this, QuestionActivity.class);
        startActivityForResult(intent,TO_QUESTION);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.question_btn:
                if(game.isCanClick()) {
                    questionDialog.show();
                }else {
                    toQuestionActivity();
                }
                break;
            case R.id.exit_btn:
                exitDialog.show();
                break;
            case R.id.ready_btn:
                game.refreshView(null);
                nowUser.getPlayer().setType(Constant.READY);
                presenter.doReady(nowUser);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TO_QUESTION && resultCode == RESULT_OK && data != null) {
            int result = data.getIntExtra(Constant.ANSWER_CODE,-1);
            if(result == QuestionView.CORRECT) {
                hint.setText("答对了，可以下棋");
                game.setCanClick(true);
            }else if(result == QuestionView.FAIL) {
                hint.setText("答错了，接受惩罚");
                game.setCanClick(false);
                startPunishTimer();
            }
        }
    }

    private void startPunishTimer() {
        if(!game.isGameOver()) {
            questionBtn.setEnabled(false);
            game.setVisibility(View.GONE);
            counter.setVisibility(View.VISIBLE);
            GameCounter timeCount = new GameCounter(PUNISH_TIME * 1000, 1000);
            timeCount.start();
        }
    }

    class GameCounter extends CountDownTimer{

        public GameCounter(long millisInFuture, long countDownInterval){
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long time) {
            counter.setText(time/1000+"");
        }

        @Override
        public void onFinish() {
            counter.setVisibility(View.GONE);
            counter.setText("");
            game.setVisibility(View.VISIBLE);
            if(!game.isGameOver()) {
                questionBtn.setEnabled(true);
            }
        }
    }

}