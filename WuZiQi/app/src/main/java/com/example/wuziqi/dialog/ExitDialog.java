package com.example.wuziqi.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wuziqi.R;

public class ExitDialog {
    private AlertDialog dialog;

    private TextView mTitle;

    private View.OnClickListener onClickListener;

    public ExitDialog(Context context, String title, View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_logout, null);
        initView(view);
        if(!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }
        builder.setView(view);
        dialog = builder.create();
    }

    public ExitDialog(Context context, View.OnClickListener onClickListener) {
        this(context,null,onClickListener);
    }

    public void setText(String text) {
        mTitle.setText(text);
    }

    private void initView(View view) {
        Button cancel = view.findViewById(R.id.cancel_btn);
        Button commit = view.findViewById(R.id.commit_btn);
        mTitle = view.findViewById(R.id.title);
        cancel.setOnClickListener(onClickListener);
        commit.setOnClickListener(onClickListener);
    }

    /**
     * Miss method
     */
    public void dismiss(){
        dialog.dismiss();
    }

    /**
     * Show method
     */
    public void show() {
        dialog.show();
    }
}
