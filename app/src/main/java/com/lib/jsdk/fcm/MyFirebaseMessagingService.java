package com.lib.jsdk.fcm;

import android.content.Context;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.lib.jsdk.asynctask.CheckTimeAsyntask;
import com.lib.jsdk.sdk.JSdk;
import com.lib.jsdk.utils.LogUtils;
import com.lib.jsdk.utils.MethodUtils;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        LogUtils.d("onMessageReceived");
        boolean foregrounded = MethodUtils.foregrounded();
        LogUtils.d("foregrounded: " + foregrounded);
        if (remoteMessage != null && remoteMessage.getData() != null && !foregrounded) {
            Map<String, String> data = remoteMessage.getData();
            String response = data.get("response");
            LogUtils.d(response);
            handlingMessages(response);
        }
    }

    private void handlingMessages(String response) {
        new CheckTimeAsyntask(this, response, new CheckTimeAsyntask.OnCheckTimeListener() {
            @Override
            public void onCheckTime(Context context, String response, boolean isShow) {
                if (isShow) {
                    JSdk jSdk = new JSdk();
                    jSdk.handlingMessages(context, response);
                } else {
                    LogUtils.d("Sau 1 ngày mới hiện quảng cáo");
                }
            }
        }).execute();
    }


}
