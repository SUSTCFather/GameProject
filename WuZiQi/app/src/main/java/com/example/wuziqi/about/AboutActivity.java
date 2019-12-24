package com.example.wuziqi.about;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wuziqi.R;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView aboutTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
        initAbout();
    }

    private void initView() {
        //topbar
        findViewById(R.id.back_btn).setOnClickListener(this);
        findViewById(R.id.go_txt).setVisibility(View.GONE);
        TextView titleTxt = findViewById(R.id.title_txt);
        titleTxt.setText("关于");
        aboutTxt = findViewById(R.id.about_txt);
    }

    private void initAbout() {
        aboutTxt.setText(Test.getAbout());
    }

    @Override
    public void onClick(View view) {
       finish();
    }
}
