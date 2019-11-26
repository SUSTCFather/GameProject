package com.example.wuziqi.main;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.example.wuziqi.Constant;
import com.example.wuziqi.MyApplication;
import com.example.wuziqi.R;
import com.example.wuziqi.SharedUtil;
import com.example.wuziqi.bean.User;
import com.example.wuziqi.main.fragment.HomeFragment;
import com.example.wuziqi.main.fragment.MineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;

    private MineFragment mineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String userData = SharedUtil.getInstance(this).readShared(Constant.USER_DATA,null);
        if(userData != null) {
            User user = JSON.parseObject(userData, User.class);
            MyApplication.startClient(user.getUserId()+"");
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, getHomeFragment())
                .add(R.id.fragment_container, getMineFragment())
                .hide(getMineFragment())
                .commit();
        initBottomNavigation();
    }

    private HomeFragment getHomeFragment() {
        if(homeFragment == null) {
            homeFragment = HomeFragment.newInstance();
        }
        return homeFragment;
    }

    private MineFragment getMineFragment() {
        if(mineFragment == null) {
            mineFragment = MineFragment.newInstance();
        }
        return mineFragment;
    }

    private void showFragment(int itemId) {
        if(itemId == R.id.menu_home) {
            getSupportFragmentManager().beginTransaction()
                    .show(getHomeFragment())
                    .hide(getMineFragment())
                    .commit();
        }else {
            getSupportFragmentManager().beginTransaction()
                    .show(getMineFragment())
                    .hide(getHomeFragment())
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
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.closeConnect();
    }
}
