package com.lib.jsdk.utils;

import android.content.Context;
import android.os.Handler;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.lib.jsdk.activity.TempAdActivity;

public class AdmobManager {

    private static AdmobManager instance;
    private InterstitialAd mInterstitialAd;
    private TempAdActivity tempAdActivity;

    private AdmobManager() {
    }

    public static AdmobManager getInstance() {
        if (instance == null) {
            instance = new AdmobManager();
        }
        return instance;
    }

    public void loadInterstitialAd(Context context, String appId, String fullID, final OnAdLoadListener onAdLoadListener) {
        if (mInterstitialAd == null || !mInterstitialAd.isLoaded()) {
            LogUtils.d("loadInterstitialAd");
            MobileAds.initialize(context, appId);
            mInterstitialAd = new InterstitialAd(context);
            mInterstitialAd.setAdUnitId(fullID);
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    LogUtils.d("onAdLoaded");
                    if (onAdLoadListener != null) {
                        onAdLoadListener.onAdLoaded();
                    }
                }

                @Override
                public void onAdClosed() {
                    if (tempAdActivity != null) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tempAdActivity.onBackPressed();
                                tempAdActivity = null;
                            }
                        }, 100);
                    }
                }

                @Override
                public void onAdFailedToLoad(int i) {
                    LogUtils.d("onAdFailedToLoad: " + i);
                }
            });
        } else {
            LogUtils.d("ad loaded");
        }
    }

    public void showAd(TempAdActivity activity) {
        this.tempAdActivity = activity;
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    public void showAd() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    public interface OnAdLoadListener {
        void onAdLoaded();
    }
}
