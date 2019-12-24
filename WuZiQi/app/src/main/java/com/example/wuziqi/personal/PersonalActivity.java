package com.example.wuziqi.personal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wuziqi.util.Constant;
import com.example.wuziqi.R;
import com.example.wuziqi.bean.User;
import com.example.wuziqi.bean.response.UserResponse;
import com.example.wuziqi.dialog.AddUserDialog;

public class PersonalActivity extends AppCompatActivity implements View.OnClickListener, PersonalContract.PersonalView {

    private TextView mNickName;

    private TextView mWinCount;

    private TextView mLoseCount;

    private TextView mScore;

    private TextView mWinRate;

    private TextView mailText;

    private AddUserDialog changeNameDialog;

    private PersonalPresenterIml presenter;

    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        presenter = new PersonalPresenterIml();
        presenter.attachView(this);
        userId = getIntent().getLongExtra(Constant.USER_ID,0);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.getUserInfo(userId);
    }

    private void initView() {
        //topbar
        ImageView backBtn = findViewById(R.id.back_btn);
        findViewById(R.id.go_txt).setVisibility(View.GONE);
        TextView titleTxt = findViewById(R.id.title_txt);
        titleTxt.setText("个人信息");
        backBtn.setOnClickListener(this);
        //个人信息
        findViewById(R.id.nickname_btn).setOnClickListener(this);
        mNickName = findViewById(R.id.nickname);
        mWinCount = findViewById(R.id.win_count);
        mLoseCount = findViewById(R.id.lose_count);
        mScore = findViewById(R.id.score);
        mWinRate = findViewById(R.id.win_rate);
        mailText = findViewById(R.id.mail_txt);

        changeNameDialog = new AddUserDialog(this,this);
    }

    @Override
    public void showUserInfo(UserResponse response) {
        if(response.getStatus() == Constant.SUCCESS) {
            User user = response.getData();
            mailText.setText("邮箱: "+user.getMailAddress());
            mNickName.setText(user.getUserName());
            mWinCount.setText(String.valueOf(user.getWinNum()));
            mLoseCount.setText(String.valueOf(user.getLoseNum()));
            mScore.setText(String.valueOf(user.getScore()));
            int sum = user.getLoseNum() + user.getWinNum();
            mWinRate.setText(String.format("%.2f%%",getRate(user.getWinNum(),sum)));
        }
    }

    private double getRate(int win,int sum) {
        if(sum == 0) {
            return 0;
        }
        return (double)win * 100 / sum;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.nickname_btn:
                changeNameDialog.show();
                break;
            case R.id.commit_btn:
                //todo
                changeNameDialog.dismiss();
                break;
            case R.id.cancel_btn:
                changeNameDialog.dismiss();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }


}
