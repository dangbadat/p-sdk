package com.lib.jsdk.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.lib.jsdk.common.Common;
import com.lib.jsdk.sdk.JSdk;
import com.lib.jsdk.utils.TinyDB;

import java.util.Calendar;

public class CheckTimeAsyntask extends AsyncTask<Void, Void, Boolean> {

    private Context context;
    private String response;
    private TinyDB tinyDB;
    private OnCheckTimeListener listener;

    public CheckTimeAsyntask(Context context, String response, OnCheckTimeListener listener) {
        this.context = context;
        this.response = response;
        this.listener = listener;
        this.tinyDB = new TinyDB(context);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        if (JSdk.DEBUG) {
            return true;
        }

        long timeFirstOpen = tinyDB.getLong(Common.TIME_FIRST_OPEN, 0);
        if (timeFirstOpen != 0 && timeFirstOpen + Common.ONE_DAY >= Calendar.getInstance().getTimeInMillis()) {
            return true;
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (listener != null) {
            listener.onCheckTime(context, response, aBoolean);
        }

    }

    public interface OnCheckTimeListener {
        void onCheckTime(Context context, String response, boolean isShow);
    }
}
