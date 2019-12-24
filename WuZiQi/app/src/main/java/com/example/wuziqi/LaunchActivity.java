package com.example.wuziqi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.wuziqi.login.LoginActivity;
import com.example.wuziqi.main.MainActivity;
import com.example.wuziqi.register.RegisterActivity;
import com.example.wuziqi.util.Constant;
import com.example.wuziqi.util.SharedUtil;

public class LaunchActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLogin();
        setContentView(R.layout.activity_launch);
        initView();
    }

    private void checkLogin() {
        String userData = SharedUtil.getInstance(this).readShared(Constant.USER_DATA,null);
        if(!TextUtils.isEmpty(userData)) {
            Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initView() {
        TextView loginBtn = findViewById(R.id.login_btn);
        TextView registerBtn = findViewById(R.id.register_btn);
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_btn:
                Intent it1 = new Intent(LaunchActivity.this, LoginActivity.class);
                startActivity(it1);
                break;
            case R.id.register_btn:
                Intent it2 = new Intent(LaunchActivity.this, RegisterActivity.class);
                startActivity(it2);
                break;
        }
    }
}
