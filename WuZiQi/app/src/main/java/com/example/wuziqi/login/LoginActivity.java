package com.example.wuziqi.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.wuziqi.forget.ForgetActivity;
import com.example.wuziqi.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginContract.LoginView {
    private static final String TAG = "LoginActivity";

    private LoginPresenterIml presenter;

    private EditText mUserNameInput;

    private EditText mPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenterIml();
        presenter.attachView(this);
        initView();
        Log.e(TAG,"onCreate");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            mUserNameInput.setText(bundle.getString(Constant.USER_NAME));
            mPasswordInput.setText(bundle.getString(Constant.PASSWORD));
        }else {
            mUserNameInput.setText("");
            mPasswordInput.setText("");
        }
        Log.e(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"onResume");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    private void initView(){
        ImageView backBtn = findViewById(R.id.back_btn);
        TextView goBtn = findViewById(R.id.go_txt);
        TextView loginBtn = findViewById(R.id.login_btn);
        TextView forgetBtn = findViewById(R.id.forget_btn);
        mUserNameInput = findViewById(R.id.input_username);
        mPasswordInput = findViewById(R.id.input_password);
        backBtn.setOnClickListener(this);
        goBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        forgetBtn.setOnClickListener(this);
    }

    @Override
    public void showLogin(UserResponse loginResponse) {
        if(loginResponse.getStatus() == Constant.SUCCESS) {
            //todo
        }
        Toast.makeText(this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void login() {
        String userName = mUserNameInput.getText().toString();
        String password = mPasswordInput.getText().toString();
        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        UserRequest request = new UserRequest();
        request.setUserName(userName);
        request.setPassword(password);
        presenter.doLogin(request);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.go_txt:
                Intent it1 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(it1);
                break;
            case R.id.login_btn:
                login();
                break;
            case R.id.forget_btn:
                Intent it2 = new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(it2);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
