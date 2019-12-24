package com.example.wuziqi.main;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.example.wuziqi.util.Constant;
import com.example.wuziqi.MyApplication;
import com.example.wuziqi.R;
import com.example.wuziqi.util.SharedUtil;
import com.example.wuziqi.bean.User;
import com.example.wuziqi.bean.response.HallResponse;
import com.example.wuziqi.bean.response.HttpResult;
import com.example.wuziqi.main.fragment.hall.HallFragment;
import com.example.wuziqi.main.fragment.MineFragment;
import com.example.wuziqi.websocket.OnMessageHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements OnMessageHandler {

    private static final String TAG = "MainActivity";

    private HallFragment hallFragment;

    private MineFragment mineFragment;

    private User nowUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hallFragment = HallFragment.newInstance();
        mineFragment = MineFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, hallFragment)
                .add(R.id.fragment_container, mineFragment)
                .hide(mineFragment)
                .commit();
        initBottomNavigation();
        String userData = SharedUtil.getInstance(this).readShared(Constant.USER_DATA,null);
        if(userData != null) {
            nowUser = JSON.parseObject(userData, User.class);
        }
    }

    @Override
    protected void onResume() {
        Log.e(TAG,"onResume");
        super.onResume();
        MyApplication.initClient(nowUser.getUserId()+"");
        MyApplication.setMessageHandler(this);
    }

    private void showFragment(int itemId) {
        if(itemId == R.id.menu_home) {
            getSupportFragmentManager().beginTransaction()
                    .show(hallFragment)
                    .hide(mineFragment)
                    .commit();
        }else {
            getSupportFragmentManager().beginTransaction()
                    .show(mineFragment)
                    .hide(hallFragment)
                    .commit();
        }
    }

    private void initBottomNavigation() {
        BottomNavigationView mBottomNavigationView = findViewById(R.id.bottomNavigation);
        // 添加监听
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                showFragment(item.getItemId());
                // 这里注意返回true,否则点击失效
                return true;
            }
        });
    }

    @Override
    public void onMessage(String message) {
        Log.e(TAG,message);
        HttpResult result = JSON.parseObject(message,HttpResult.class);
        if(result.getMessage().equals("HallList")) {
            HallResponse responseHall = JSON.parseObject(message,HallResponse.class);
            hallFragment.refresh(responseHall.getData());
        }
    }

    @Override
    public void onClose(final String reason) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "socket关闭: "+reason, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.closeConnect();
    }
}
