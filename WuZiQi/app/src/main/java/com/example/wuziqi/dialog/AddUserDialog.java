package com.example.wuziqi.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wuziqi.R;


public class AddUserDialog {
    private Context context;

    private AlertDialog dialog;

    private EditText editText;

    public AddUserDialog(Context context, View.OnClickListener onClickListener) {
        this.context = context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = getView(onClickListener);
        builder.setView(view);
        dialog = builder.create();
    }

    private View getView(View.OnClickListener onClickListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_friend, null);
        Button cancel = view.findViewById(R.id.cancel_btn);
        Button commit = view.findViewById(R.id.commit_btn);
        editText = view.findViewById(R.id.input_username);
        cancel.setOnClickListener(onClickListener);
        commit.setOnClickListener(onClickListener);
        return view;
    }

    public String getUserInput() {
        return editText.getText().toString();
    }

    /**
     * Miss method
     */
    public void dismiss(){
        dialog.dismiss();
        editText.setText("");
    }

    /**
     * Show method
     */
    public void show() {
        //这个位置十分重要，只有位于这个位置逻辑才是正确的
        dialog.show();
    }
}
