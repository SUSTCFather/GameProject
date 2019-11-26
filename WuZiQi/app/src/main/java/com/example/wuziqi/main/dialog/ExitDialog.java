package com.example.wuziqi.main.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wuziqi.R;

public class ExitDialog {
    private Context context;

    private AlertDialog dialog;

    public ExitDialog(Context context, View.OnClickListener onClickListener) {
        this.context = context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = getView(onClickListener);
        builder.setView(view);
        dialog = builder.create();
    }

    private View getView(View.OnClickListener onClickListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_logout, null);
        Button cancel = view.findViewById(R.id.cancel_btn);
        Button commit = view.findViewById(R.id.commit_btn);
        cancel.setOnClickListener(onClickListener);
        commit.setOnClickListener(onClickListener);
        return view;
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
