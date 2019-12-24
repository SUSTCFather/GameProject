package com.example.wuziqi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wuziqi.util.Constant;
import com.example.wuziqi.R;
import com.example.wuziqi.bean.GamePlayer;

public class GameHeadView extends FrameLayout {

    private ImageView gameHead;
    private TextView gameName;
    private ImageView hand;

    public GameHeadView(@NonNull Context context) {
        this(context,null);
    }

    public GameHeadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GameHeadView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GameHeadView);
        int location = a.getInt(R.styleable.GameHeadView_location,0);
        a.recycle();
        View view;
        if(location == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.profile_left, this, true);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.profile_right, this, true);
        }
        gameHead = view.findViewById(R.id.game_head);
        gameName = view.findViewById(R.id.game_name);
        hand = view.findViewById(R.id.hand);
    }

    public void refresh(GamePlayer gamePlayer, String nowUserId) {
        if(gamePlayer != null) {
            gameHead.setImageResource(R.drawable.ic_head);
            gameName.setText(gamePlayer.getUserName());
            if(gamePlayer.getUserId().equals(nowUserId)) {
                gameName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }else {
                gameName.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }
            if(gamePlayer.getType() == Constant.READY) {
                hand.setVisibility(View.VISIBLE);
            }else {
                hand.setVisibility(View.INVISIBLE);
            }
        }else {
            gameHead.setImageResource(R.drawable.ic_mine_normal);
            gameName.setText("");
            hand.setVisibility(View.INVISIBLE);
        }

    }

}
