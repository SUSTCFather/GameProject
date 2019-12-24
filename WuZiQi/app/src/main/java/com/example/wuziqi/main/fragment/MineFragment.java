package com.example.wuziqi.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSON;
import com.example.wuziqi.about.AboutActivity;
import com.example.wuziqi.util.Constant;
import com.example.wuziqi.LaunchActivity;
import com.example.wuziqi.R;
import com.example.wuziqi.util.SharedUtil;
import com.example.wuziqi.bean.User;
import com.example.wuziqi.dialog.ExitDialog;
import com.example.wuziqi.forget.ForgetActivity;
import com.example.wuziqi.history.HistoryActivity;
import com.example.wuziqi.rank.RankActivity;
import com.example.wuziqi.personal.PersonalActivity;


public class MineFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "MineFragment";

    private User user;

    private ExitDialog exitDialog;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate");
        String userData = SharedUtil.getInstance(getActivity()).readShared(Constant.USER_DATA,null);
        if(userData != null) {
            user = JSON.parseObject(userData,User.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(TAG,"onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        initView(rootView);
        addListener(rootView);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
    }

    private void initView(View view) {
        TextView titleTxt = view.findViewById(R.id.title_txt);
        TextView nameTxt = view.findViewById(R.id.name_txt);
        exitDialog = new ExitDialog(getActivity(),this);
        if(user != null) {
            nameTxt.setText(user.getUserName());
        }
        titleTxt.setText("我的");
        view.findViewById(R.id.back_btn).setVisibility(View.GONE);
        view.findViewById(R.id.go_txt).setVisibility(View.GONE);
    }

    private void addListener(View view) {
        view.findViewById(R.id.mine_card).setOnClickListener(this);
        view.findViewById(R.id.setPassword).setOnClickListener(this);
        view.findViewById(R.id.history).setOnClickListener(this);
        view.findViewById(R.id.rank_list).setOnClickListener(this);
        view.findViewById(R.id.about).setOnClickListener(this);
        view.findViewById(R.id.tv_exit).setOnClickListener(this);
    }

    private void jumpToPersonal() {
        Intent intent = new Intent(getActivity(), PersonalActivity.class);
        intent.putExtra(Constant.USER_ID,user.getUserId());
        startActivity(intent);
    }

    private void jumpToHistory() {
        Intent intent = new Intent(getActivity(), HistoryActivity.class);
        intent.putExtra(Constant.USER_ID,user.getUserId());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_card:
                jumpToPersonal();
                break;
            case R.id.setPassword:
                Intent it2 = new Intent(getActivity(), ForgetActivity.class);
                startActivity(it2);
                break;
            case R.id.history:
                jumpToHistory();
                break;
            case R.id.rank_list:
                Intent it3 = new Intent(getActivity(), RankActivity.class);
                startActivity(it3);
                break;
            case R.id.about:
                Intent it4 = new Intent(getActivity(), AboutActivity.class);
                startActivity(it4);
                break;
            case R.id.tv_exit:
                exitDialog.show();
                break;
            case R.id.cancel_btn:
                exitDialog.dismiss();
                break;
            case R.id.commit_btn:
                exitDialog.dismiss();
                doExit();
                break;
        }
    }

//    private void showProgressDialog(){
//        ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setTitle("我是个等待的Dialog");
//        progressDialog.setMessage("等待中");
//        progressDialog.setIndeterminate(true);
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//    }

    private void doExit() {
        if(getActivity() != null) {
            SharedUtil.getInstance(getActivity()).removeShared(Constant.USER_DATA);
            showToast("退出登录成功");
            Intent intent = new Intent(getActivity(), LaunchActivity.class);
            getActivity().startActivity(intent);
            getActivity().finish();
        }
    }

    private void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}