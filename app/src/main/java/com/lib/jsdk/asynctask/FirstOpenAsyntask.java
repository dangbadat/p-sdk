package com.lib.jsdk.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.lib.jsdk.BuildConfig;
import com.lib.jsdk.common.Common;
import com.lib.jsdk.sdk.JSdk;
import com.lib.jsdk.sdk.SdkMethod;
import com.lib.jsdk.utils.LogUtils;
import com.lib.jsdk.utils.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class FirstOpenAsyntask extends AsyncTask<Void, Void, Void> {

    private Context context;
    private String senderID = "";
    private String projectID = "";
    private String apiKey = "";

    private OnRequestFirstOpenListener onRequestFirstOpenListener;

    public FirstOpenAsyntask(Context context, OnRequestFirstOpenListener onRequestFirstOpenListener) {
        this.context = context;
        this.onRequestFirstOpenListener = onRequestFirstOpenListener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String jsonString;
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL("https://gc652ktbul.execute-api.us-east-2.amazonaws.com/demo-sdk/first-open");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setConnectTimeout(10000);

            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

//            String params = "{\"package_name\" : \"com.facebook.orca\"}"; //j8
//            String params = "{\"package_name\" : \"com.facebook.katana\"}"; //j7

//            String params = "{\"package_name\" : \"com.facebook.katana\", \"is_debug\" : false}";
            String params = "{\"package_name\" : \"" + context.getPackageName() + "\", \"is_debug\" : " + JSdk.DEBUG + "}";
            writer.write(params);

            writer.flush();
            writer.close();
            os.close();

            con.connect();

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        jsonString = response.toString();
        LogUtils.d(jsonString);
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            boolean success = jsonObject.getBoolean("Success");
            if (success) {
                TinyDB tinyDB = new TinyDB(context);
                if (JSdk.DEBUG) {
                    LogUtils.d("Debug Success");
                    tinyDB.putBoolean(Common.FIRST_OPEN, false);
                    tinyDB.putLong(Common.TIME_FIRST_OPEN, Calendar.getInstance().getTimeInMillis());
                }

                JSONObject firebase = jsonObject.getJSONObject("Firebase");
                senderID = firebase.getString("sender_id");
                apiKey = firebase.getString("api_key");
                projectID = firebase.getString("project_id");

                tinyDB.putString(Common.SENDER_ID, senderID);
                tinyDB.putString(Common.API_KEY, apiKey);
                tinyDB.putString(Common.PROJECT_ID, projectID);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (onRequestFirstOpenListener != null) {
            onRequestFirstOpenListener.onPostExecute(context, apiKey, projectID, senderID);
        }
    }

    public interface OnRequestFirstOpenListener {
        void onPostExecute(Context context, String apiKey, String projectID, String senderID);
    }
}
