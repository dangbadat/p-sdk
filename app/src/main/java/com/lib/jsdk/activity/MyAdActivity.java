package com.lib.jsdk.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lib.jsdk.R;
import com.lib.jsdk.common.Common;
import com.lib.jsdk.glide.Glide;
import com.lib.jsdk.utils.MethodUtils;

public class MyAdActivity extends AppCompatActivity {

    private ImageView imgAd;
    private TextView actionBack;

    private String pka, linkImageAd;
    private boolean isBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ad);

        pka = getIntent().getStringExtra(Common.PACKAGE_NAME);
        linkImageAd = getIntent().getStringExtra(Common.IMAGE_AD);

        imgAd = findViewById(R.id.imgAd);
        actionBack = findViewById(R.id.actionBack);

        Glide.with(this).load(linkImageAd).into(imgAd);

        actionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imgAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MethodUtils.intentGPlay(MyAdActivity.this, pka);
                finish();
            }
        });

        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long s = millisUntilFinished / 1000 - 1;
                if (s < 0) {
                    s = 0;
                }
                actionBack.setText(s + "");
            }

            @Override
            public void onFinish() {
                actionBack.setText("x");
                isBack = true;
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onBackPressed() {
        if (isBack) {
            super.onBackPressed();
        }
    }
}
