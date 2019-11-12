package com.example.wuziqi.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.wuziqi.R;

public class AnswerItemView extends FrameLayout implements Checkable {

    private TextView optionTv;

    private TextView contentTv;

    private String optionText;

    private String contentText;

    private boolean mChecked = false;

    public AnswerItemView(Context context) {
        this(context,null);
    }

    public AnswerItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AnswerItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.answer_view, this, true);
        optionTv = view.findViewById(R.id.option);
        contentTv = view.findViewById(R.id.content);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AnswerItemView);
        setOptionText(a.getString(R.styleable.AnswerItemView_option_text));
        setContentText(a.getString(R.styleable.AnswerItemView_content_text));
        a.recycle();
    }

    public void setContentText(String contentText) {
        if(contentText != null){
            this.contentText = contentText;
            contentTv.setText(contentText);
        }
    }

    public void setOptionText(String optionText) {
        if(optionText != null){
            this.optionText = optionText;
            optionTv.setText(optionText);
        }
    }

    public String getOptionText() {
        return optionText;
    }

    public String getContentText() {
        return contentText;
    }

    @Override
    public void setChecked(boolean b) {
        if(mChecked != b) {
            mChecked = b;
            optionTv.setSelected(mChecked);
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }
}
