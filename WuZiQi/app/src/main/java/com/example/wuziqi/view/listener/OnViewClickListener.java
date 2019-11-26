package com.example.wuziqi.view.listener;

import android.view.View;

public class OnViewClickListener implements View.OnClickListener{

    private int position;

    private OnItemClickListener listener;

    public OnViewClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void onClick(View view) {
        listener.onItemClick(view,position);
    }
}
