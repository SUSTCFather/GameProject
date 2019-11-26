package com.example.wuziqi.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.wuziqi.Constant;
import com.example.wuziqi.R;
import com.example.wuziqi.SharedUtil;
import com.example.wuziqi.bean.User;
import com.example.wuziqi.bean.request.FriendRequest;
import com.example.wuziqi.bean.request.InviteRequest;
import com.example.wuziqi.bean.response.FriendResponse;
import com.example.wuziqi.bean.response.InviteResponse;
import com.example.wuziqi.main.FriendAdapter;
import com.example.wuziqi.main.dialog.AddUserDialog;
import com.example.wuziqi.view.listener.OnItemClickListener;

public class HomeFragment extends Fragment implements View.OnClickListener, HomeContract.HomeView, OnItemClickListener {

    private static final String TAG = "HomeFragment";

    private FriendAdapter mFriendAdapter;

    private AddUserDialog addUserDialog;

    private HomePresenterIml presenter;

    private User nowUser;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"onCreate");
        String userData = SharedUtil.getInstance(getActivity()).readShared(Constant.USER_DATA,null);
        if(userData != null) {
            nowUser = JSON.parseObject(userData,User.class);
        }
        presenter = new HomePresenterIml();
        presenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy");
        presenter.detachView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG,"onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initView(rootView);
        FriendRequest request = new FriendRequest();
        request.setFromUserName(nowUser.getUserName());
        presenter.getFriendList(request);
        return rootView;
    }

    private void initView(View view) {
        TextView titleTxt = view.findViewById(R.id.title_txt);
        TextView goTxt = view.findViewById(R.id.go_txt);
        RecyclerView recyclerView = view.findViewById(R.id.friend_view);

        titleTxt.setText("游戏好友");
        goTxt.setText("添加");
        goTxt.setOnClickListener(this);
        view.findViewById(R.id.back_btn).setVisibility(View.GONE);
        //recyclerView
        mFriendAdapter = new FriendAdapter(getActivity(),this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mFriendAdapter);
        //dialog
        addUserDialog = new AddUserDialog(getActivity(),this);
    }

    @Override
    public void onItemClick(View view, int position) {
        User user = mFriendAdapter.getUser(position);
        InviteRequest request = new InviteRequest();
        request.setFromUserId(nowUser.getUserId());
        request.setToUserId(user.getUserId());
        presenter.inviteFriend(request);
    }

    @Override
    public void showInvite(InviteResponse response) {
        Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_txt:
                addUserDialog.show();
                break;
            case R.id.cancel_btn:
                addUserDialog.dismiss();
                break;
            case R.id.commit_btn:
                doAddFriend();
                break;
        }
    }

    @Override
    public void showFriend(FriendResponse response) {
        if(response.getStatus() == Constant.SUCCESS) {
            mFriendAdapter.addUser(response.getData());
            addUserDialog.dismiss();
        }
        Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFriendList(FriendResponse response) {
        Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
        mFriendAdapter.addUser(response.getData());
    }

    private void doAddFriend() {
        String userInput = addUserDialog.getUserInput();
        if(TextUtils.isEmpty(userInput)) {
            Toast.makeText(getActivity(), "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        FriendRequest request = new FriendRequest();
        request.setFromUserName(nowUser.getUserName());
        request.setToUserName(userInput);
        presenter.addFriend(request);
    }
}
