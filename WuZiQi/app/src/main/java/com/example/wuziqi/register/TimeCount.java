package com.example.wuziqi.register;


import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.example.wuziqi.R;


public class TimeCount extends CountDownTimer {

    private TextView textView;

    /**
     * time count
     * @param millisInFuture the millon second in the future
     * @param countDownInterval count down interval
     * @param button button
     */
    public TimeCount(long millisInFuture, long countDownInterval, TextView button){
        super(millisInFuture, countDownInterval);
        this.textView = button;
    }

    /**
     * onTick event
     * @param time time
     */
    @Override
    public void onTick(long time){
        textView.setEnabled(false);
        textView.setText(time/1000+"s");
        textView.setTextColor(Color.GRAY);
    }

    /**
     * on finish event
     */
    @Override
    public void onFinish(){
        textView.setEnabled(true);
        textView.setText("重新发送");
        textView.setTextColor(Color.BLACK);
    }
}
