package com.example.wuziqi.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wuziqi.util.Constant;
import com.example.wuziqi.R;
import com.example.wuziqi.bean.request.UserRequest;
import com.example.wuziqi.bean.response.UserResponse;
import com.example.wuziqi.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, RegisterContract.RegisterView {

    private RegisterPresenterIml presenter;

    private EditText mUserNameInput;

    private EditText mMailAddressInput;

    private EditText mCheckCodeInput;

    private EditText mPasswordInput;

    private TextView verifyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        presenter = new RegisterPresenterIml();
        presenter.attachView(this);
        initView();
    }

    private void initView(){
        ImageView backBtn = findViewById(R.id.back_btn);
        TextView titleTxt = findViewById(R.id.title_txt);
        TextView registerBtn = findViewById(R.id.register_btn);
        verifyBtn = findViewById(R.id.verify_btn);
        mUserNameInput = findViewById(R.id.input_username);
        mCheckCodeInput = findViewById(R.id.input_checkCode);
        mMailAddressInput = findViewById(R.id.input_mailAddress);
        mPasswordInput = findViewById(R.id.input_password);
        findViewById(R.id.go_txt).setVisibility(View.GONE);
        titleTxt.setText("注册");
        registerBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        verifyBtn.setOnClickListener(this);
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
            case R.id.register_btn:
                register();
                break;
        }
    }

    @Override
    public void showRegister(UserResponse response) {
        Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
        if(response.getStatus() == Constant.SUCCESS) {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.USER_NAME, mUserNameInput.getText().toString());
            bundle.putString(Constant.PASSWORD, mPasswordInput.getText().toString());
            Intent it = new Intent(RegisterActivity.this, LoginActivity.class);
            it.putExtras(bundle);
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

    private void verify() {
        String userName = mUserNameInput.getText().toString();
        String mailAddress = mMailAddressInput.getText().toString();
        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(mailAddress)){
            Toast.makeText(this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        UserRequest request = new UserRequest();
        request.setUserName(userName);
        request.setMailAddress(mailAddress);
        presenter.doVerify(request);
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

    private void register() {
        String userName = mUserNameInput.getText().toString();
        String mailAddress = mMailAddressInput.getText().toString();
        String checkCode = mCheckCodeInput.getText().toString();
        String password = mPasswordInput.getText().toString();
        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
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
        UserRequest request = new UserRequest(userName,mailAddress,checkCode,password);
        presenter.doRegister(request);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

}
