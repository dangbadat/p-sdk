package com.lib.jsdk.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.lib.jsdk.utils.AdmobManager;
import com.lib.jsdk.utils.MethodUtils;

public class TempAdActivity extends AppCompatActivity {

    private boolean isShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            isShow = true;
            AdmobManager.getInstance().showAd(TempAdActivity.this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MethodUtils.foregrounded() && !isShow) {
            isShow = true;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    AdmobManager.getInstance().showAd(TempAdActivity.this);
                }
            }, 200);
        }
    }
}
