package com.example.wuziqi.forget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.wuziqi.Constant;
import com.example.wuziqi.R;
import com.example.wuziqi.bean.request.UserRequest;
import com.example.wuziqi.bean.response.UserResponse;
import com.example.wuziqi.login.LoginActivity;
import com.example.wuziqi.register.TimeCount;

public class ForgetActivity extends AppCompatActivity implements View.OnClickListener,ForgetContract.ForgetView {

    private EditText mMailAddressInput;

    private EditText mCheckCodeInput;

    private EditText mPasswordInput;

    private TextView verifyBtn;

    private ForgetPresenterIml presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        presenter = new ForgetPresenterIml();
        presenter.attachView(this);
        initView();
    }

    private void initView(){
        ImageView backBtn = findViewById(R.id.back_btn);
        TextView titleTxt = findViewById(R.id.title_txt);
        TextView sureBtn = findViewById(R.id.sure_btn);
        verifyBtn = findViewById(R.id.verify_btn);
        mCheckCodeInput = findViewById(R.id.input_checkCode);
        mMailAddressInput = findViewById(R.id.input_mailAddress);
        mPasswordInput = findViewById(R.id.input_password);
        findViewById(R.id.go_txt).setVisibility(View.GONE);
        titleTxt.setText("重置密码");
        sureBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        verifyBtn.setOnClickListener(this);
    }

    @Override
    public void showChange(UserResponse response) {
        Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
        if(response.getStatus() == Constant.SUCCESS) {
            Intent it = new Intent(ForgetActivity.this, LoginActivity.class);
            startActivity(it);
            finish();
        }
    }

    @Override
    public void showVerify(UserResponse response) {
        if(response.getStatus() == Constant.SUCCESS) {
            startTimer();
        }
        Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.verify_btn:
                verify();
                break;
            case R.id.sure_btn:
                sure();
                break;
        }
    }

    private void verify() {
        String mailAddress = mMailAddressInput.getText().toString();
        if(TextUtils.isEmpty(mailAddress)){
            Toast.makeText(this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        UserRequest request = new UserRequest();
        request.setMailAddress(mailAddress);
        presenter.doVerify(request);
        //设置verifyBtn
        startTimer();
    }

    private void startTimer(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TimeCount timeCount = new TimeCount(60 * 1000, 1000, verifyBtn);
                timeCount.start();
            }
        });
    }

    private void sure() {
        String mailAddress = mMailAddressInput.getText().toString();
        String checkCode = mCheckCodeInput.getText().toString();
        String password = mPasswordInput.getText().toString();
        if(TextUtils.isEmpty(mailAddress)){
            Toast.makeText(this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(checkCode)){
            Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        UserRequest request = new UserRequest(null,mailAddress,checkCode,password);
        presenter.doChange(request);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
