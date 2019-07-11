package com.lib.jsdk.sdk;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.lib.jsdk.callback.OnRegisterListner;
import com.lib.jsdk.activity.TempAdActivity;
import com.lib.jsdk.activity.MyAdActivity;
import com.lib.jsdk.asynctask.FirstOpenAsyntask;
import com.lib.jsdk.common.Common;
import com.lib.jsdk.glide.Glide;
import com.lib.jsdk.glide.load.DataSource;
import com.lib.jsdk.glide.load.engine.GlideException;
import com.lib.jsdk.glide.request.RequestListener;
import com.lib.jsdk.glide.request.target.Target;
import com.lib.jsdk.utils.AdmobManager;
import com.lib.jsdk.utils.LogUtils;
import com.lib.jsdk.utils.MethodUtils;
import com.lib.jsdk.utils.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

public class SdkMethod {

    private static SdkMethod instance;

    private SdkMethod() {
    }

    static SdkMethod getInstance() {
        if (instance == null) {
            instance = new SdkMethod();
        }
        return instance;
    }

    private void registerFirebase(Context context, final OnRegisterListner onRegisterListner, String apiKey, String projectID, String senderID) {
        if (apiKey.isEmpty() || projectID.isEmpty() || senderID.isEmpty()) {
            if (onRegisterListner != null) {
                onRegisterListner.onSuccess();
            }
            return;
        }
        final TinyDB tinyDB = new TinyDB(context);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApiKey(apiKey)
                .setApplicationId(projectID)
                .setGcmSenderId(senderID)
                .build();
        boolean hasBeenInitialized = false;
        List<FirebaseApp> firebaseApps = FirebaseApp.getApps(context);
        for (FirebaseApp app : firebaseApps) {
            if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) {
                hasBeenInitialized = true;
            }
        }

        if (!hasBeenInitialized) {
            FirebaseApp.initializeApp(context, options);
            FirebaseMessaging.getInstance().subscribeToTopic("jsdk")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            LogUtils.d("subscribeToTopic Done");
                            tinyDB.putBoolean(Common.FIRST_OPEN, false);
                            tinyDB.putLong(Common.TIME_FIRST_OPEN, Calendar.getInstance().getTimeInMillis());
                            if (onRegisterListner != null) {
                                onRegisterListner.onSuccess();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            LogUtils.d("subscribeToTopic failure");
                            if (onRegisterListner != null) {
                                onRegisterListner.onSuccess();
                            }
                        }
                    });
        }
    }

    void firstOpen(Context ctx, final OnRegisterListner onRegisterListner) {
        TinyDB tinyDB = new TinyDB(ctx);
        if (MethodUtils.isNetworkConnected(ctx) && tinyDB.getBoolean(Common.FIRST_OPEN, Common.DEFAULT_FIRST_OPEN)) {
            FirstOpenAsyntask firstOpenAsyntask = new FirstOpenAsyntask(ctx, new FirstOpenAsyntask.OnRequestFirstOpenListener() {
                @Override
                public void onPostExecute(Context context, String apiKey, String projectID, String senderID) {
                    registerFirebase(context, onRegisterListner, apiKey, projectID, senderID);
                }
            });
            firstOpenAsyntask.execute();
        } else {
            if (onRegisterListner != null) {
                onRegisterListner.onSuccess();
            }
        }
    }

    void handlingMessages(Context context, String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);

            String type = jsonResponse.getString("type");
            if (type.equals("show_my_ad")) {
                showMyAd(context, jsonResponse);
            } else if (type.equals("show_ad_network")) {
                showAdNetWork(context, jsonResponse);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showAdNetWork(final Context context, JSONObject jsonResponse) {
        try {
            JSONObject data = jsonResponse.getJSONObject("data");

            String appId = data.getString("app_id");
            String fullscreenID = data.getString("fullscreen_id");

            if (JSdk.DEBUG) {
                appId = "ca-app-pub-3940256099942544~3347511713";
                fullscreenID = "ca-app-pub-3940256099942544/1033173712";
            }

            AdmobManager.getInstance().loadInterstitialAd(context, appId, fullscreenID, new AdmobManager.OnAdLoadListener() {
                @Override
                public void onAdLoaded() {
                    Intent intent = new Intent(context, TempAdActivity.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    } else {
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    }
                    context.startActivity(intent);
                }

            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showMyAd(final Context context, JSONObject jsonResponse) {
        try {
            JSONObject data = jsonResponse.getJSONObject("data");

            final String pka = data.getString("package_name");
            final String imageAd = data.getString("image_ad");

            if (MethodUtils.isAppInstall(context, pka)) {
                LogUtils.d("App đã cài");
                return;
            }

            Glide.with(context).load(imageAd).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    LogUtils.d("onResourceReady");
                    Intent intent = new Intent(context, MyAdActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Common.PACKAGE_NAME, pka);
                    intent.putExtra(Common.IMAGE_AD, imageAd);
                    context.startActivity(intent);
                    return false;
                }
            }).submit();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
