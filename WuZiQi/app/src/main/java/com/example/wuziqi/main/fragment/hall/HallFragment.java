package com.example.wuziqi.main.fragment.hall;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.wuziqi.Constant;
import com.example.wuziqi.R;
import com.example.wuziqi.SharedUtil;
import com.example.wuziqi.bean.GamePlayer;
import com.example.wuziqi.bean.Hall;
import com.example.wuziqi.bean.User;
import com.example.wuziqi.bean.request.EnterRequest;
import com.example.wuziqi.bean.response.EnterResponse;
import com.example.wuziqi.bean.response.HallResponse;
import com.example.wuziqi.game.GameActivity;
import com.example.wuziqi.main.HallAdapter;
import com.example.wuziqi.view.listener.OnItemClickListener;

import java.util.List;

public class HallFragment extends Fragment implements OnItemClickListener, HallContract.HallView {

    private static final String TAG = "HallFragment";

    private HallPresenterIml presenter;

    private HallAdapter mHallAdapter;

    private User nowUser;

    public static HallFragment newInstance() {
        HallFragment fragment = new HallFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userData = SharedUtil.getInstance(getActivity()).readShared(Constant.USER_DATA,null);
        if(userData != null) {
            nowUser = JSON.parseObject(userData, User.class);
        }
        presenter = new HallPresenterIml();
        presenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hall, container, false);
        initView(rootView);
        Log.e(TAG,"onCreateView");
        return rootView;
    }

    private void initView(View view) {
        TextView titleTxt = view.findViewById(R.id.title_txt);
        RecyclerView recyclerView = view.findViewById(R.id.hall_view);
        view.findViewById(R.id.back_btn).setVisibility(View.GONE);
        view.findViewById(R.id.go_txt).setVisibility(View.GONE);
        titleTxt.setText("游戏大厅");

        //recyclerView
        mHallAdapter = new HallAdapter(getActivity(),this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mHallAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void showHall(HallResponse response) {
        if(response.getStatus() == Constant.SUCCESS) {
            mHallAdapter.refreshData(response.getData());
        }
    }

    @Override
    public void showEnter(EnterResponse response) {
        if(response.getStatus() == Constant.SUCCESS) {
            Intent intent = new Intent(getActivity(), GameActivity.class);
            intent.putExtra(Constant.ENTER_DATA,JSON.toJSONString(response.getData()));
            getActivity().startActivity(intent);
        }
    }

    public void refresh(List<Hall> list) {
        if(mHallAdapter != null) {
            mHallAdapter.refreshData(list);
        }
    }

    @Override
    public void onResume() {
        Log.e(TAG,"onResume");
        super.onResume();
        presenter.getHall();
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()){
            case R.id.black_seat:
                doClickBlack(position);
                break;
            case R.id.white_seat:
                doClickWhite(position);
                break;
        }
    }

    private void doClickBlack(int position) {
        Hall hall = mHallAdapter.getHall(position);
        if(hall.getBlackPlayer() != null) {
            showToast("该座位上有人");
            return;
        }
        GamePlayer gamePlayer = new GamePlayer(nowUser.getUserId()+"",nowUser.getUserName());
        EnterRequest request = new EnterRequest(hall.getHallId(), Constant.BLACK, gamePlayer);
        presenter.enterHall(request);
    }

    private void doClickWhite(int position) {
        Hall hall = mHallAdapter.getHall(position);
        if(hall.getWhitePlayer() != null) {
            showToast("该座位上有人");
            return;
        }
        GamePlayer gamePlayer = new GamePlayer(nowUser.getUserId()+"",nowUser.getUserName());
        EnterRequest request = new EnterRequest(hall.getHallId(), Constant.WHITE, gamePlayer);
        presenter.enterHall(request);
    }

    private void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}
